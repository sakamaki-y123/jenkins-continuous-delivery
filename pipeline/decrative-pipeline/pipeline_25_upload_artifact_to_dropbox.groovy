pipeline {

    agent any

    environment {
        kittenImage = "kitten-image.jpg"
        DROPBOX_SHARED_URL = "https://www.dropbox.com/sh/tpkdnsdha16feh1/AADAz-iqJTRj6lGMoSnzt4Qta"
    }

    stages {
        stage('get random kitten image') {
            steps {
                sh "curl --fail -o ${kittenImage} http://www.randomkittengenerator.com/cats/rotator.php"
            }
            post{
                success{
                    archiveArtifacts "${kittenImage}"
                    dropbox (
                        configName: 'dropbox-test', 
                        remoteDirectory: 'kitten', 
                        sourceFiles: "${kittenImage}"
                    )
                    echo "Check here! ${DROPBOX_SHARED_URL}"
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