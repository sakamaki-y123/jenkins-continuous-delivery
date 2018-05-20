package main.groovy.pipeline.library;

def boolean skipStage( String startStageNo, String stageNo ){
    if( startStageNo.toInteger() <= stageNo.toInteger() ){
        return false
    } else {
        return true
    }
}

def copyArtifacts( List projectNameList ){
    for( project in projectNameList ){
        copyArtifacts(projectName:"${params.COPY_SOURCE_PROJECT}")
    }    
}

def findFiles(pattern = '*.*' ){
    files = findFiles(glob: '*.*')
    for (file in files) {
        echo file.name
    }
    return files
}

return this
