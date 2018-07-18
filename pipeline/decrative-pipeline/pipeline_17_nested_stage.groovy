/**
 * TOPICS
 * - sequential-stages
 *     - https://jenkins.io/blog/2018/07/02/whats-new-declarative-piepline-13x-sequential-stages/
 *   sequential-stages supported after Declarative Pipeline 1.3
 */

def SAMPLE_YAML = """\
name:
  first: ""
  last: ""
dates:
  birth: ""
"""

def yamlData


pipeline {
    agent any

    environment {
        fileName = "sample.yml"
    }
    
    stages {
        stage('1 prepare') {
            stages {
                stage("1-1 read yaml") {
                    steps {
                        script{
                            yamlData = readYaml text: "${SAMPLE_YAML}"
                            echo "Name is ${yamlData.name.first} ${yamlData.name.last}"
                            echo "Birthday is ${yamlData.dates.birth}"
                        }
                    }
                }
                stage("1-2 write yaml") {
                    steps {
                        script {
                            yamlData.name.first = "Ichiro"
                            yamlData.name.last = "Sato"
                            yamlData.dates.birth = "1980-01-01"
                            echo "Name is ${yamlData.name.first} ${yamlData.name.last}"
                            echo "Birthday is ${yamlData.dates.birth}"
                            writeYaml file: fileName, data: yamlData
                        }
                    }
                }
            }
        }

        stage('2 archive ') {
            steps {
                archiveArtifacts fileName
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}