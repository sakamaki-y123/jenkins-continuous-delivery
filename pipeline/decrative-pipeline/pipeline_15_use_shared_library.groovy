/**
 * TOPICS
 * - Pipeline Shared Libraries
 *     - https://jenkins.io/doc/book/pipeline/shared-libraries/
 */

@Library('jenkins-continuous-delivery')
import main.groovy.pipeline.library.Utils
def utils = new Utils()

pipeline {

    agent any

    stages {

        stage('stage0: crean workspace') {
            steps {
                deleteDir()
            }
        }

        stage('stage1: copy artifacts') {
            when {
                not {
                    expression {
                        return utils.skipStage(START_STAGE_NO, "1")
                    }
                }
            }

            steps {
                script {
                    def projectNameList = "${COPY_ARTIFACTS_PROJECTS}".split()
                    utils.copyMultipleArtifacts(projectNameList)
                }
            }
        }

        stage('stage2: find files') {
            when {
                not {
                    expression {
                        return utils.skipStage(START_STAGE_NO, "2")
                    }
                }
            }
            steps {
                script {
                    try {
                        utils.findWorkSpaceFiles()
                    } catch (Exception e) {
                        error e.getMessage()
                    }
                }
            }
        }
    }
}