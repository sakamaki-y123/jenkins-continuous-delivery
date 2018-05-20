package src.main.groovy.pipeline.library;

def boolean skipStage( String startStageNo, String stageNo ){
    if( startStageNo.toInteger() <= stageNo.toInteger() ){
        return false
    } else {
        return true
    }
}

return this
