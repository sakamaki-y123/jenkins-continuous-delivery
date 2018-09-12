def getVideoId(videoUrl){
    def videoId = ""
    if (videoUrl.startsWith("https://www.youtube.com/watch?v")){
        videoId = videoUrl.split("=")[1]
    } else if (videoUrl.startsWith("https://youtu.be/")) {
        videoId = videoUrl.split("/")[3]
    }
    return videoId
}

def getVideoIds(videoUrls){
    def videoIds = []
    for( url in videoUrls){
        videoIds << getVideoId(url)
    }
    return videoIds
}

def getVideoCategory(){
    withCredentials([
        string(credentialsId: 'YOUTUBE_API_KEY', variable: 'YOUTUBE_API_KEY')
    ]) {
        def params = []
        params.add("part=snippet")
        params.add("regionCode=jp")
        params.add("key=${YOUTUBE_API_KEY}")
        urlParams = params.join('&')
        withEnv([
            "URL=https://www.googleapis.com/youtube/v3/videoCategories?${urlParams}",
        ]) {
            def urlJsonTxt = sh( returnStdout: true, script: 'curl --fail ${URL}')
            def urlJson = readJSON text: urlJsonTxt
            return urlJson
        }            
    }
}

def getVideoInfo(videoId){
    withCredentials([
        string(credentialsId: 'YOUTUBE_API_KEY', variable: 'YOUTUBE_API_KEY')
    ]) {
        def params = []
        params.add("part=snippet")
        params.add("id=${videoId}")
        params.add("key=${YOUTUBE_API_KEY}")
        urlParams = params.join('&')
        withEnv([
            "URL=https://www.googleapis.com/youtube/v3/videos?${urlParams}",
        ]) {
            def urlJsonTxt = sh( returnStdout: true, script: 'curl --fail ${URL}')
            def urlJson = readJSON text: urlJsonTxt
            return urlJson
        }            
    }
}

def getVideoInfoList(videoIds){
    def videoInfoList =[]
    for(videoId in videoIds){
        videoInfoList << getVideoInfo(videoId)       
    }
    return videoInfoList
}

def getSearchVideoInfo(searchWord,part = "snippet", type = "video", maxResults = 2, order = "viewCount" ,publishedDaysAgo = 7){
    withCredentials([
        string(credentialsId: 'YOUTUBE_API_KEY', variable: 'YOUTUBE_API_KEY')
    ]) {
        def params = []
        params.add("part=${part}")
        params.add("type=${type}")
        def apiQuestion = "${searchWord}".replaceAll(' ','%20').replaceAll('　','%20')
        params.add("q=${apiQuestion}")
        params.add("maxResults=${maxResults}")
        params.add("order=${order}")
        def daysAgo = (new Date() - "${publishedDaysAgo}".toInteger()).format("yyyy-MM-dd'T'HH:mm:ss'Z'")
        params.add("publishedAfter=${daysAgo}")
        params.add("key=${YOUTUBE_API_KEY}")
        urlParams = params.join('&')
        withEnv([
            "URL=https://www.googleapis.com/youtube/v3/search?${urlParams}",
        ]) {
            def urlJsonTxt = sh( returnStdout: true, script: 'curl --fail ${URL}')
            def urlJson = readJSON text: urlJsonTxt
            return urlJson
        }            
    }
}

def getSearchVideoInfoList(searchWords){
    def searchVideoInfoList =[]
    for(searchWord in searchWords){
        searchVideoInfoList << getSearchVideoInfo(searchWord)
    }
    return searchVideoInfoList
}

def uploadVideo(title,videoPath){
    def videoId = ""
    withCredentials([
        string(credentialsId: 'YOUTUBE_API_KEY', variable: 'YOUTUBE_API_KEY')
    ]) {
        withCredentials([
            file(credentialsId: 'youtube_upload_credential', variable: 'CREDENTIAL_FILE'),
            file(credentialsId: 'youtube_upload_client_secret', variable: 'CLIENT_SECRET_FILE')
        ]) {
            sh "wget https://github.com/tokland/youtube-upload/archive/master.zip"
            unzip dir: '', glob: '', zipFile: 'master.zip'
            withDockerContainer(args: '-u 0', image: 'python') {
                sh "pip install --upgrade httplib2 oauth2client rsa uritemplate"
                sh "pip install --upgrade google-api-python-client progressbar2"
                sh "cd youtube-upload-master ; python setup.py install"
                def params = []
                params.add("--title=${title}")
                params.add("--default-language=ja")
                params.add("--default-audio-language=ja")                         
                params.add("--credentials-file=${CREDENTIAL_FILE}")
                params.add("--client-secrets=${CLIENT_SECRET_FILE}")
                def cmd = "youtube-upload "+ params.join(" ") + " ${videoPath}"
                videoId = sh( returnStdout: true, script: cmd).trim()
            }
        }        
    }
    return videoId
}

def updateVideo(videoId,title,discriptionFile,categoryId,tag){
    def result = ""
    withCredentials([
        string(credentialsId: 'YOUTUBE_API_KEY', variable: 'YOUTUBE_API_KEY')
    ]) {
        withCredentials([
            file(credentialsId: 'youtube_upload_credential', variable: 'CREDENTIAL_FILE'),
            file(credentialsId: 'youtube_upload_client_secret', variable: 'CLIENT_SECRET_FILE')
        ]) {
            script = libraryResource 'youtube/video_update.py'
            writeFile file: "video_update.py",text: script
            withDockerContainer(args: '-u 0', image: 'python') {
                sh "pip install --upgrade httplib2 oauth2client rsa uritemplate google-api-python-client progressbar2"
                def params = []
                params.add("--video-id=${videoId}")
                params.add("--title=${title}")
                params.add("--description-file=${discriptionFile}")
                params.add("--category-id=${categoryId}")
                params.add("--tag=${tag}")
                params.add("--credentials-file=${CREDENTIAL_FILE}")
                params.add("--client-secrets=${CLIENT_SECRET_FILE}")
                def cmd = "python video_update.py "+ params.join(" ")
                result = sh( returnStdout: true, script: cmd).trim()
                echo result
            }
        }        
    }
    return result
}