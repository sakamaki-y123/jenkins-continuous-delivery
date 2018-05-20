/**
 * TOPICS
 * - when
 *     - https://jenkins.io/doc/book/pipeline/syntax/#when
 */

@library('jenkins-continuous-delivery')
import main.groovy.pipeline.library.Utils
def utils = new Utils()

pipeline {
    agent any

    stages {
        stage('stage1') {
            
            when{
                not{
                    expression {
                        return utils.skipStage( START_STAGE_NO, "1" )
                    }
                }
            }

            steps {
                echo "run stage 1"
            }
        }

        stage('stage2') {

            when{
                not{
                    expression {
                        return utils.skipStage( START_STAGE_NO, "2" )
                    }
                }
            }
            
            steps {
                echo "run stage 2"
            }
        }
    }
}