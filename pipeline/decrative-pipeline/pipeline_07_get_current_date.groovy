/**
* TOPICS
* - readJSON
*     - https://jenkins.io/doc/pipeline/steps/pipeline-utility-steps/#readjson-read-json-from-files-in-the-workspace
*/

pipeline {

    agent any

    stages {
        stage('get current date') {
            steps {
                script{
                    sh 'curl -f -o date.json "http://date.jsontest.com"'
                    json = readJSON file: 'date.json'
                    echo "TODAY is ${json.date}"
                }
            }
        }
    }
}
