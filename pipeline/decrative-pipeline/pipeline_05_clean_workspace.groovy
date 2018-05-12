/**
* TOPICS
* - post
*     - https://jenkins.io/doc/book/pipeline/syntax/#post
* - cleanWs
*     - https://jenkins.io/doc/pipeline/steps/ws-cleanup/#cleanws-delete-workspace-when-build-is-done
*/

pipeline {

    agent any

    environment {
        fileName = "output.txt"
    }

    stages {

        stage('write file') {
            steps {
                 writeFile( file: fileName, text: "${OUTPUT_TEXT}")
            }
        }

        stage('archive artifacts') {
            steps {
                 archiveArtifacts fileName
            }
        }
    }

    post{
        success{
                cleanWs()
        }
    }
}
