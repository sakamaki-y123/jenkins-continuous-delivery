pipeline {

    agent any

    stages {
        stage('archive artifacts') {

            environment {
                fileName = "output.txt"
            }

            steps {
                 writeFile( file: fileName, text: "${OUTPUT_TEXT}")
                 archiveArtifacts fileName
            }
        }
    }
}
