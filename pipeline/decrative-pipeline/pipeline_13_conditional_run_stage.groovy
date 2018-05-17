/**
 * TOPICS
 * - when
 *     - https://jenkins.io/doc/book/pipeline/syntax/#when
 */

def boolean skipStage( String startStageNo, String stageNo ){
    if( startStageNo.toInteger() <= stageNo.toInteger() ){
        return false
    } else {
        return true
    }
}

pipeline {
    agent any

    stages {
        stage('stage1') {

            when {
                environment name: 'START_STAGE_NO', value: '1'
            }

            steps {
                echo "run stage 1"
            }
        }

        stage('stage2') {

            when{
                not{
                    expression {
                        return skipStage( START_STAGE_NO, "2" )
                    }
                }
            }
            
            steps {
                echo "run stage 2"
            }
        }
    }
}