package main.groovy.pipeline.library;

import groovy.time.*

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

/**
* @param targetDateTime yyyy-MM-dd HH:mm:ss
* @return String get seconds from current to target.
*/
def getSecondsFromNow(targetDateTime){
    def current = new Date()
    def target = Date.parse("yyyy-MM-dd HH:mm:ss", "${targetDateTime}", TimeZone.getTimeZone("JST"))
    TimeDuration duration = TimeCategory.minus(target, current)
    return (duration.days * 24 * 60 * 60 + duration.hours * 60 * 60 + duration.minutes * 60 + duration.seconds).toString()
}

return this
