pipeline {

    agent any

    stages {
        
        stage('create decrative-pipeline jobs') {
            environment {
                FOLDER_PATH = "decrative-pipeline"
            }
            steps {
                jobDsl (
                    targets: 'jobdsl/folder/folder.groovy',
                    additionalParameters: [FOLDER_PATH: "${FOLDER_PATH}"]
                )
                jobDsl (
                    targets: 'jobdsl/pipeline/pipeline_01_hello_world.groovy',
                    additionalParameters: [FOLDER_PATH: "${FOLDER_PATH}"]
                )
                jobDsl (
                    targets: 'jobdsl/pipeline/pipeline_02_use_sh_step.groovy',
                    additionalParameters: [FOLDER_PATH: "${FOLDER_PATH}"]
                )
            }
        }
    }

}
