pipeline {

    agent any

    libraries {
       lib("jenkins-continuous-delivery")
    }

    stages {

        stage ('get timeline'){
            steps {
                script{
                    withCredentials([
                        file(credentialsId: 'twitter-config.py', variable: 'CONFIG_PY')
                    ]) {
                        sh "cp ${CONFIG_PY} ${WORKSPACE}/config.py"
                    }
		            withDockerContainer(args: '-u 0', image: 'python:3.7-alpine3.7') {
		                sh "pip install requests requests_oauthlib"
                        writeFile file: 'timeline.py', text: libraryResource('twitter/timeline.py')
                        sh "python timeline.py -c 5"
		            }
                }
            }
            post{
                always{
                    cleanWs()
                }
            }
        }
        stage ('tweet'){
            steps {
                script{
                    git 'https://github.com/sakamaki-y123/jenkins-continuous-delivery'
		            withDockerContainer(args: '-u 0', image: 'python:3.7-alpine3.7') {
		                sh "pip install requests requests_oauthlib"
                        withCredentials([
                            file(credentialsId: 'twitter-config.py', variable: 'CONFIG_PY')
                        ]) {
                            sh "cp ${CONFIG_PY} resources/twitter/config.py"
                            sh "cd resources/twitter && python tweet.py -t '${TWEET}'"
                        }   
		            }
                }
            }
            post{
                always{
                    cleanWs()
                }
            }
        }
        stage ('tweet media'){
            steps {
                script{
                    git 'https://github.com/sakamaki-y123/jenkins-continuous-delivery'
                    sh "cp kitten-image.jpg resources/twitter/kitten-image.jpg"
                    withDockerContainer(args: '-u 0', image: 'python:3.7-alpine3.7') {
		                sh "pip install requests requests_oauthlib"
                        withCredentials([
                            file(credentialsId: 'twitter-config.py', variable: 'CONFIG_PY')
                        ]) {
                            sh "cp ${CONFIG_PY} resources/twitter/config.py"
                            sh "cd resources/twitter && python tweet_media.py -f kitten-image.jpg -t 'かわいいにゃんこ'"
                        }   
		            }
                }
            }
            post{
                always{
                    cleanWs()
                }
            }
        }
    }
}
