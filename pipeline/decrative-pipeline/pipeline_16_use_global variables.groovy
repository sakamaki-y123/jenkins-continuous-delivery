/**
 * TOPICS
 * - defining-global-variables
 *     - https://jenkins.io/doc/book/pipeline/shared-libraries/#defining-global-variables
 */

@Library('jenkins-continuous-delivery')_

pipeline {
    agent any
    stages{
        stage ('Example') {
            steps {
                script { 
                    log.info 'Starting'
                    log.warning 'Nothing to do!'
                }
            }
        }
    }
}