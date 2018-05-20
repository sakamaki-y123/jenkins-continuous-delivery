package main.groovy.pipeline.library;

def boolean skipStage( String startStageNo, String stageNo ){
    if( startStageNo.toInteger() <= stageNo.toInteger() ){
        return false
    } else {
        return true
    }
}

def void copyMultipleArtifacts( projectNameList ){
    for( projectName in projectNameList ){
        echo "copy artifacts from ${projectName}"
        copyArtifacts(projectName:"${projectName}")
    }    
}

def findWorkSpaceFiles(pattern = '*.*' ){
    files = findFiles(glob: '*.*')
    for (file in files) {
        echo file.name
    }
    return files
}

return this
