/**
 * TOPICS
 * - defining-global-variables
 *     - https://jenkins.io/doc/book/pipeline/shared-libraries/#defining-global-variables
 */

@Library('jenkins-continuous-delivery')_
import main.groovy.pipeline.library.Utils
def utils = new Utils()

pipeline {
    agent any
    stage ('Example') {
        steps {
             script { 
                 log.info 'Starting'
                 log.warning 'Nothing to do!'
             }
        }
    }
}