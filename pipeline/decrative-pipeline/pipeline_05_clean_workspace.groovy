pipeline {

    agent any

    stages {
        
        environment {
            fileName = "output.txt"
        }

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

    post{
        success{
                cleanWs()
        }
    }
}
