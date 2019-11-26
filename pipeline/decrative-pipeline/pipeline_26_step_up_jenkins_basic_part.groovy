pipeline{
    agent{
        label "slave1"
    }
    stages{
        stage("download kitten image"){
            steps{
                echo "=== start download kitten image ==="
                sh label: '', script: "curl --fail -o ${KITTEN_IMAGE_FILE_NAME} http://www.randomkittengenerator.com/cats/rotator.php"
                echo "=== start download kitten image ==="
            }
            post{
                success{
                    archiveArtifacts "${KITTEN_IMAGE_FILE_NAME}"
                }
            }
        }
    }
    post{
        always{
            cleanWs()
        }
        success{
            slackSend channel: '#topic_step_up_jenkins', color: 'good', message: "finished ${env.JOB_BASE_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
        }
        failure{
            slackSend channel: '#topic_step_up_jenkins', color: 'danger', message: "finished ${env.JOB_BASE_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
        }
    }
}
