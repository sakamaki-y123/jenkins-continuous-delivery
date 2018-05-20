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
                    projectNameList = COPY_ARTIFACTS_PROJECTS.sprit("\r\n")
                    utils.copyArtifacts(projectNameList)
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