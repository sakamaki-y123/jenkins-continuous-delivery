/**
 * TOPICS
 * - agent docker
 *     - https://jenkins.io/doc/book/pipeline/syntax/#agent
 *     - https://jenkins.io/doc/book/pipeline/docker/#execution-environment
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