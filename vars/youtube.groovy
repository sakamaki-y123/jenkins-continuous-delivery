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
    def videoInfos =[]
    for(videoId in videoIds){
        videoInfos << getVideoInfo(videoId)       
    }
    return videoInfos
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
    def searchVideoInfos =[]
    for(searchWord in searchWords){
        searchVideoInfos << getSearchVideoInfo(searchWord)
    }
    return searchVideoInfos
}
