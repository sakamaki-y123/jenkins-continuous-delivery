package main.groovy.pipeline.library;

/**
 * Send notifications based on build status string
 */

def call( sendChannel, buildStatus = 'STARTED') {
    teamDomain: "${env.DEFAULT_SLACK_TEAM_DOMAIN}"
    token: "${env.DEFAULT_SLACK_TOKEN}"

    // build status of null means successful
    buildStatus = buildStatus ?: 'SUCCESS'

    wrap([$class: 'BuildUser']) {
        // Default values
        def colorName = 'good'
        def summary ="""
        |@${env.BUILD_USER_ID}
        |${env.JOB_NAME}/${env.BUILD_NUMBER} is `${buildStatus}`
        |For details, Please check below
        |${env.BUILD_URL}
        """

        // Override default values based on build status
        if (buildStatus == 'STARTED') {
            colorName = 'good'
        } else if (buildStatus == 'SUCCESS') {
            colorName = '#006AB6'
        } else {
            colorName = 'danger'
        }

        slackSend (
            channel: "${sendChannel}",
            color: colorName, 
            message: summary.stripMargin(), 
            teamDomain: "${teamDomain}", 
            token: "${token}"
        )
    }
}
