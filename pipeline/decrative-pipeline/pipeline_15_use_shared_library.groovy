/**
 * TOPICS
 * - when
 *     - https://jenkins.io/doc/book/pipeline/syntax/#when
 */

@Library('jenkins-continuous-delivery')
import main.groovy.pipeline.library.Utils
def utils = new Utils()

pipeline {

    agent any
    
    stages {
        
        stage('stage1: copy artifacts') {
            when{
                not{
                    expression {
                        return utils.skipStage( START_STAGE_NO, "1" )
                    }
                }
            }

            steps {
                script{
                    deleteDir()
                    def projectNameList = "${COPY_ARTIFACTS_PROJECTS}".split()
                    utils.copySomeArtifacts(projectNameList)
                }
            }
        }

        stage('stage2: find files') {
            when{
                not{
                    expression {
                        return utils.skipStage( START_STAGE_NO, "2" )
                    }
                }
            }
            steps {
                script{
                    try {
                        utils.findWorkSpaceFiles()
                    } catch ( Exception e){
                        error e.getMessage()
                    }
                }
            }
        }
    }
}