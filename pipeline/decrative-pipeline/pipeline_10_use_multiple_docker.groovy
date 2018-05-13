/**
 * TOPICS
 * - agent none
 *     - https://jenkins.io/doc/book/pipeline/syntax/#agent
 * - using multiple docker containers
 *     - https://jenkins.io/doc/book/pipeline/docker/#using-multiple-containers
 */

pipeline {
    agent none

    stages {
        stage('Back-end') {
            agent {
                docker {
                    image 'maven:3-alpine'
                }
            }
            steps {
                sh 'mvn --version'
            }
        }

        stage('Front-end') {
            agent {
                docker {
                    image 'node:7-alpine'
                }
            }
            steps {
                sh 'node --version'
            }
        }
    }
}