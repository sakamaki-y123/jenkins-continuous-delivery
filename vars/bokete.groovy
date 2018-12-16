def getBoketeUrl(category){
    switch(category) {
        case "all":
            return "https://bokete.jp/boke/hot"
            break
        case "baka":
            return "https://bokete.jp/boke/category/%E3%83%90%E3%82%AB/hot"
            break
        case "surrealism":
            return "https://bokete.jp/boke/category/%E3%82%B7%E3%83%A5%E3%83%BC%E3%83%AB/hot"
            break
        case "black":
            return "https://bokete.jp/boke/category/%E3%83%96%E3%83%A9%E3%83%83%E3%82%AF/hot"
            break
        case "miuchi":
            return "https://bokete.jp/boke/category/%E8%BA%AB%E5%86%85/hot"
            break
        case "tatoe":
            return "https://bokete.jp/boke/category/%E4%BE%8B%E3%81%88/hot"
            break
        case "sonota":
            return "https://bokete.jp/boke/category/%E3%81%9D%E3%81%AE%E4%BB%96/hot"
            break
        case "select":
            return "https://bokete.jp/boke/select"
            break
        case "pickup":
            return "https://bokete.jp/boke/pickup"
            break
        default:
            return "https://bokete.jp/boke/tag/${category}"
            break
    }
}

def getBoketeInfoList(boketeUrl,startPageNumber,endPageNumber,maxResult = "80"){
    def boketeInfoList = []
    // get info
    for (int page = startPageNumber; page < endPageNumber; page++) {
        withEnv([
            "URL=${boketeUrl}?page=${page}"
        ]) {
            def hotPageHtml = sh( returnStdout: true, script: 'curl --fail ${URL}')
            for( line in hotPageHtml.split("\n")){
                if(line.trim().startsWith('<input type="text" class="form-control" value=') && line.contains('<a href=')){
                    def boketeInfo = line.trim().replaceFirst('<input type="text" class="form-control" value=','').replaceFirst('\'<a href="','').replaceFirst('"><img src="',' ').replaceFirst('" title="',' ').replaceFirst('"></a>\'','').split(' ')
                    def bokete = [:]
                    bokete.url = boketeInfo[0]
                    bokete.downloadUrl = boketeInfo[1]
                    bokete.title = boketeInfo[2]
                    boketeInfoList << bokete
                    if( boketeInfoList.size() >= maxResult){
                        break;
                    }                                    
                }
            }
        }
        if( boketeInfoList.size() >= maxResult){
            break;
        }   
    }
    return boketeInfoList
}


def downloadBoketeImage(category,boketeInfoList,minresult,maxresult){
    def pickUpBoketeInfoList = []
    int i = 1
    dir(category){
        for (bokete in boketeInfoList) {
            if(minresult < i){
                try {
                    sh "curl --fail -o bokete_${i}.png ${bokete.downloadUrl}"
                } catch (Exception){
                    downloadUrl = "${bokete.downloadUrl}".replaceFirst('https',"http")
                    sh "curl --fail -o bokete_${i}.png ${downloadUrl}"
                }
                pickUpBoketeInfoList << bokete
            }

            if( maxresult <= i ){
                break;
            } 
            i ++
        } 
    }
    return pickUpBoketeInfoList
}

def downloadBoketeImage(boketeInfoList,minresult,maxresult){
    def pickUpBoketeInfoList = []
    int i = 1
    for (bokete in boketeInfoList) {
        if(minresult < i){
            bokete.image = "${bokete.downloadUrl}".split('/').last()
            try {
                sh "curl --fail -o ${bokete.image} ${bokete.downloadUrl}"
            } catch (Exception){
                downloadUrl = "${bokete.downloadUrl}".replaceFirst('https',"http")
                sh "curl --fail -o ${bokete.image} ${downloadUrl}"
            }
            pickUpBoketeInfoList << bokete
        }

        if( maxresult <= i ){
            break;
        } 
        i ++
    } 
    return pickUpBoketeInfoList
}

def createSourceDescription(boketeInfoList,minresult,maxresult){
    int i = 1
    // create source description
    def sourceInfoList = []
    for (bokete in boketeInfoList) {
        if(minresult < i){
            def sourceInfo = """
            |${bokete.title}
            |${bokete.url}
            """
            sourceInfoList << sourceInfo.stripMargin().trim()
        }

        if( maxresult <= i ){
            break;
        } 
         i ++
    }
    return sourceInfoList
}

def createSourceSummary(boketeInfoList,minresult,maxresult){
    int i = 1
    // create source description
    def sourceInfoList = []
    sourceInfoList << "■ ボケてタイトル一覧"
    for (bokete in boketeInfoList) {
        if(minresult < i){
            def sourceInfo = """
            |${i}. 「${bokete.title}」
            """
            sourceInfoList << sourceInfo.stripMargin().trim()
        }

        if( maxresult <= i ){
            break;
        } 
         i ++
    }
    return sourceInfoList
}

def getBoketeComments( boketeUrl,resultJson = 'search_bokete_comments.json'){
    withDockerContainer(args: '-u 0', image: 'python:3.6.7-alpine3.6') {
        sh "pip install requests"
        writeFile file: 'parse_bokete_comments.py', text: libraryResource('html-parser/parse_bokete_comments.py')
        sh "python parse_bokete_comments.py -u '${boketeUrl}' -r '${resultJson}'"
        def comments = readJSON file: "${resultJson}"
        return comments
    }
}

def addBoketeComments(boketeInfoList){
    withDockerContainer(args: '-u 0', image: 'python:3.6.7-alpine3.6') {
        sh "pip install requests"
        writeFile file: 'parse_bokete_comments.py', text: libraryResource('html-parser/parse_bokete_comments.py')
        for( boketeInfo in boketeInfoList){
            retry(3){
                sh "python parse_bokete_comments.py -u '${boketeInfo.url}' -r tmp_result.json"
                def comments = readJSON file: "tmp_result.json"
                boketeInfo.comments = comments
            }
        }
        return boketeInfoList
    }
}