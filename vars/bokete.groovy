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
                sh "curl --fail -o bokete_${i}.png ${bokete.downloadUrl}"
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
