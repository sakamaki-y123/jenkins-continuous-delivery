/**
* TOPICS
* - sh
*     - https://jenkins.io/doc/pipeline/steps/workflow-durable-task-step/#sh-shell-script
*/


pipeline {

    agent any

    stages {
        stage('show os information') {
            steps {
                sh 'cat /etc/os-release'
            }
        }
    }
}
