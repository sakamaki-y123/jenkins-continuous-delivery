pipeline {

    agent any

    environment {
        fileName = "output.txt"
    }

    stages {

        stage('write file') {
            steps {
                 writeFile( file: "output.txt", text: "${OUTPUT_TEXT}")
            }
        }

        stage('archive artifacts') {
            steps {
                 archiveArtifacts fileName
            }
        }
    }
}
