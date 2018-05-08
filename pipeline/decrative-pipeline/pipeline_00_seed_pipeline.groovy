pipeline {

    agent any

    stages {
        
        stage('create folder') {
            steps {
                jobDsl targets: 'jobdsl/folder/folder.groovy'
            }
        }

        stage('create pipeline jobs') {
            environment {
                FOLDER_PATH = "decrative-pipeline"
            }
            steps {
                jobDsl (
                    targets: 'jobdsl/pipeline/pipeline_01_hello_world.groovy',
                    additionalParameters: [FOLDER_PATH: '${FOLDER_PATH}']
                )
            }
        }
    }

}
