pipeline {

    agent any

    environment {
        kittenImage = "kitten-image.jpg"
    }

    stages {
        stage('get random kitten image') {
            steps {
                sh "curl --fail -o ${kittenImage} http://www.randomkittengenerator.com/cats/rotator.php"
                archiveArtifacts "${kittenImage}"
            }
        }
        
        stage ('send slack message'){
            steps {
                script{
                    def slackMessage ="""
                    |@here
                    |今日のにゃんこです。
                    |${BUILD_URL}artifact/${kittenImage}
                    """
                    withCredentials(
                        [usernamePassword(
                            credentialsId: 'slack',
                            usernameVariable: 'slackTeam',
                            passwordVariable: 'slackToken'
                        )]
                    ) {
                        slackSend(
                            channel: '#jenkins_kitten_image',
                            color: 'good',
                            message: slackMessage.stripMargin(),
                            teamDomain: "${slackTeam}",
                            token: "${slackToken}"
                        )
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
