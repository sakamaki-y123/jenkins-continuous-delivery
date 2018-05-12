pipeline {

    agent any

    stages {
        stage('write file') {

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
