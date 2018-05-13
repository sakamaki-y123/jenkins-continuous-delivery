/**
* TOPICS
* - pipeline
*     - https://jenkins.io/doc/book/pipeline/syntax/#declarative-pipeline
* - agent
*     - https://jenkins.io/doc/book/pipeline/syntax/#agent 
* - stages
*     - https://jenkins.io/doc/book/pipeline/syntax/#stages 
* - stage
*     - https://jenkins.io/doc/book/pipeline/syntax/#steps
* - steps
*     - https://jenkins.io/doc/book/pipeline/syntax/#steps
* - echo
*     - https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/#echo-print-message
*/

pipeline {

    agent any

    stages {
        stage('hello') {
            steps {
                echo 'hello world'
            }
        }
    }
}
