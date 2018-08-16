pipeline {

    agent {
        label 'master'
    }

    stages {

        stage("setup workspace"){
            steps{
                git 'https://github.com/sakamaki-y123/jenkins-continuous-delivery'
            }
        }

        stage('flyway migration') {
            environment {
                DOCKER_ARGS = "-u 0 -v $JENKINS_HOME/userContent:/var/lib/jenkins/userContent"
            }
            steps {
                script {
                    withDockerContainer(image: "gradle", args: DOCKER_ARGS ) {
                        try {
                            sh "cd resources/db; gradle flywayMigrate -i"
                        } catch (err){ 
                            sh "cd resources/db; gradle flywayInfo"
                            sh "cd resources/db; gradle flywayRepair"
                            error("Migration failed. please check migration log.")
                        } finally {
                            sh "cd resources/db; gradle flywayInfo"
                            sh "rm -rf resources/db/.gradle"
                        }
                    }
                }
            }
        }
    }

    post{
        always {
            script{
                cleanWs()
            }
        }
    }
}