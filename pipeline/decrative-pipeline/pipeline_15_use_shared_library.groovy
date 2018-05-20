/**
 * TOPICS
 * - when
 *     - https://jenkins.io/doc/book/pipeline/syntax/#when
 */

@Library('jenkins-continuous-delivery')
import main.groovy.pipeline.library.Utils
import java.lang.String
def utils = new Utils()

pipeline {

    agent any
    
    stages {

        stage('stage0: delete workspace') {
            steps {
                deleteDir()
            }
        }

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
                    utils.findFiles()
                }
            }
        }
    }
}