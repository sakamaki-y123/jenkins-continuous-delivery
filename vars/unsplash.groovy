import groovy.json.*

def searchPhotos(query,page = 1,per_page = 10){
    withCredentials([
        string(credentialsId: 'UNSPLASH_API_KEY', variable: 'UNSPLASH_API_KEY')
    ]) {
        def params = []
        params << "query=${query}"
        params << "page=${page}"
        params << "per_page=${per_page}"
        params << "client_id=${UNSPLASH_API_KEY}"
        urlParams = params.join("&")
        withEnv([
            "URL=https://api.unsplash.com/search/photos?${urlParams}",
        ]) {
            def urlJsonTxt = sh( returnStdout: true, script: 'curl --fail ${URL}')
            def urlJson = readJSON text: urlJsonTxt
            for( result in urlJson.results) {
                def summary = """
                |id:${result.id}
                |description:${result.description}
                |created_at:${result.created_at}
                |download:${result.links.download}
                """
                echo summary.stripMargin()
            }
            return urlJson
        }
    }
}

def getRandomPhoto(query, count =1){
    withCredentials([
        string(credentialsId: 'UNSPLASH_API_KEY', variable: 'UNSPLASH_API_KEY')
    ]) {
        def params = []
        params << "query=${query}"
        params << "count=${count}"
        params << "client_id=${UNSPLASH_API_KEY}"
        urlParams = params.join("&")
        withEnv([
            "URL=https://api.unsplash.com/photos/random?${urlParams}",
        ]) {
            def urlJsonTxt = sh( returnStdout: true, script: 'curl --fail ${URL}')
            def urlJson = readJSON text: urlJsonTxt
            for( result in urlJson) {
                def summary = """
                |id:${result.id}
                |description:${result.description}
                |created_at:${result.created_at}
                |download:${result.links.download}
                """
                echo summary.stripMargin()
            }
            return urlJson
        }
    }
}

def getListPhotos(page = 1,per_page = 10,order_by = "latest" ){
    withCredentials([
        string(credentialsId: 'UNSPLASH_API_KEY', variable: 'UNSPLASH_API_KEY')
    ]) {
        def params = []
        params << "page=${page}"
        params << "per_page=${per_page}"
        params << "order_by=${order_by}"
        params << "client_id=${UNSPLASH_API_KEY}"
        urlParams = params.join("&")
        withEnv([
            "URL=https://api.unsplash.com/photos?${urlParams}",
        ]) {
            def urlJsonTxt = sh( returnStdout: true, script: 'curl --fail ${URL}')
            def urlJson = readJSON text: urlJsonTxt
            return urlJson
        }
    }
}

def getAPhoto(id){
    withCredentials([
        string(credentialsId: 'UNSPLASH_API_KEY', variable: 'UNSPLASH_API_KEY')
    ]) {
    }
    def params = []
    params << "id=${id}"
    params << "client_id=${UNSPLASH_API_KEY}"
    urlParams = params.join("&")
    withEnv([
        "URL=https://api.unsplash.com/photos/${id}?${urlParams}"
    ]) {
        def urlJsonTxt = sh( returnStdout: true, script: 'curl --fail ${URL}')
        def urlJson = readJSON text: urlJsonTxt
        def summary = """
        |id:${urlJson.id}
        |title:${urlJson.location.title}
        |created_at:${urlJson.created_at}
        |download:${urlJson.links.download}
        """
        echo summary.stripMargin()
        return urlJson
    } 
}

def getDownloadUrl(id){
    withCredentials([
        string(credentialsId: 'UNSPLASH_API_KEY', variable: 'UNSPLASH_API_KEY')
    ]) {
        def params = []
        params << "client_id=${UNSPLASH_API_KEY}"
        urlParams = params.join("&")
        withEnv([
            "URL=https://api.unsplash.com/photos/${id}/download?${urlParams}",
        ]) {
            def urlJsonTxt = sh( returnStdout: true, script: 'curl --fail ${URL}')
            def urlJson = readJSON text: urlJsonTxt
            return urlJson.url
        }
    }
}
