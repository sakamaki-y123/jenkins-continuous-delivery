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
