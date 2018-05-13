/**
* TOPICS
* - writeFile
*     - https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/#writefile-write-file-to-workspace
*/


pipeline {

    agent any

    stages {
        stage('write file') {
            steps {
                 writeFile( file: "output.txt", text: "${OUTPUT_TEXT}")
            }
        }
    }
}
