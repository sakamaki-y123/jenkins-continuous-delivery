import groovy.json.*

def searchVideos(query = "yellow+flower",videoType = "all",category = "",minWidth = 0, minHeight = 0,order = "popular",page = 1 , perPage = 20){
    withCredentials([
        string(credentialsId: 'PIXABAY_API_KEY', variable: 'PIXABAY_API_KEY')
    ]) {
        def params = []
        params << "q=${query}"
        params << "video_type=${videoType}"
        params << "category=${category}"
        params << "min_width=${minWidth}"
        params << "min_height=${minHeight}"
        params << "order=${order}"
        params << "page=${page}"
        params << "per_page=${perPage}"
        params << "key=${PIXABAY_API_KEY}"
        urlParams = params.join("&")
        withEnv([
            "URL=https://pixabay.com/api/videos/?${urlParams}",
        ]) {
            def urlJsonTxt = sh( returnStdout: true, script: 'curl --fail ${URL}')
            def urlJson = readJSON text: urlJsonTxt
            for( result in urlJson.hits) {
                def summary = """
                |id:${result.id}
                |pageURL:${result.pageURL}
                """
                echo summary.stripMargin()
            }
            return urlJson
        }
    }
}
