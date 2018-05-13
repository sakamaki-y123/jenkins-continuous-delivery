/**
 * TOPICS
 * - agent none
 *     - https://jenkins.io/doc/book/pipeline/syntax/#agent
 * - using multiple docker containers
 *     - https://jenkins.io/doc/book/pipeline/docker/#using-multiple-containers
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
    agent none

    environment {
        fileName = "sample.yml"
    }

    stages {
        stage('read yaml') {
            steps {
                script{
                    def datas = readYaml text: "${SAMPLE_YAML}"
                    echo "Name is ${datas.name.first} ${datas.name.last}"
                    echo "Birthday is ${datas.dates.birth}"
                }
            }
        }

        stage('read and write yaml') {
            steps {
                script{
                    datas.name.first = "Ichiro"
                    datas.name.last = "Sato"
                    datas.dates.birth = "1980-01-01"
                    echo "Name is ${datas.name.first} ${datas.name.last}"
                    echo "Birthday is ${datas.dates.birth}"
                    writeYaml file: fileName, data: datas
                }
            }
        }

        stage('archive sample.yml') {
            steps {
                 archiveArtifacts fileName
            }
        }
    }
}