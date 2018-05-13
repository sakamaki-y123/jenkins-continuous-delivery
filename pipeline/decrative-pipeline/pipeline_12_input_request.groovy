/**
 * TOPICS
 * - timeout
 *     - https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/#code-timeout-code-enforce-time-limit
 * - input
 *     - https://jenkins.io/doc/book/pipeline/syntax/#input
 *     - https://jenkins.io/doc/pipeline/steps/pipeline-input-step/#code-input-code-wait-for-interactive-input
 */

def SAMPLE_YAML = """\
name:
  first: ""
  last: ""
dates:
  birth: ""
"""

def datas

pipeline {
    agent any

    environment {
        fileName = "sample.yml"
    }

    stages {
        stage('read sample yaml') {
            steps {
                script{
                    datas = readYaml text: "${SAMPLE_YAML}"
                }
            }
        }

        stage('input name') {
            steps {
                script{
                    timeout(time:3, unit:'MINUTES') {
                        inputName = input(
                            id: "inputName",
                            message: "Please input your name.",
                            parameters:[
                                string( name: 'first', defaultValue: '', description: 'Input your first name.'),
                                string( name: 'last', defaultValue: '', description: 'Input your last name.')
                            ]
                        )
                        datas.name.first = inputName.first
                        datas.name.last = inputName.last
                    }
                }
            }
        }

        stage('input birthday') {
            steps {
                script{
                    timeout(time:3, unit:'MINUTES') {
                        inputBirthday = input(
                            id: "inputBirthday",
                            message: "Please input your birth day.",
                            parameters:[
                                string( name: 'birth', defaultValue: 'yyyy-MM-dd', description: ''),
                            ]
                        )
                        datas.dates.birth = inputBirthday
                    }
                }
            }
        }

        stage('write yaml') {
            steps {
                script{
                    echo "Name is ${datas.name.first} ${datas.name.last}"
                    echo "Birthday is ${datas.dates.birth}"
                    writeYaml file: fileName, data: datas
                    archiveArtifacts fileName
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