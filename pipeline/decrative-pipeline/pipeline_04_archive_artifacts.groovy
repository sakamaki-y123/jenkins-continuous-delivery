/**
* TOPICS
* - environment
*     - https://jenkins.io/doc/book/pipeline/syntax/#environment
* - archiveArtifacts
*     - https://jenkins.io/doc/pipeline/steps/core/#archiveartifacts-archive-the-artifacts
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
}
