/**
 * TOPICS
 * - agent docker
 *     - https://jenkins.io/doc/book/pipeline/docker/
 */

pipeline {

    agent {
        docker { 
            image 'node:7-alpine' 
        }
    }

    stages {
        stage('version') {
            steps {
                sh 'node --version'
            }
        }
    }
}