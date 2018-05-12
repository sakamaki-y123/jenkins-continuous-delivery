pipeline {

    agent any

    stages {

        stage('create decrative-pipeline jobs') {
            environment {
                FOLDER_PATH = "decrative-pipeline"
            }
            steps {
                jobDsl(
                    targets: [
                        'jobdsl/folder/folder.groovy',
                        'jobdsl/pipeline/pipeline_01_hello_world.groovy',
                        'jobdsl/pipeline/pipeline_02_use_sh_step.groovy',
                        'jobdsl/pipeline/pipeline_03_write_file.groovy',
                        'jobdsl/pipeline/pipeline_04_archive_artifacts.groovy'
                    ].join('\n'),
                    additionalParameters: [
                        FOLDER_PATH: "${FOLDER_PATH}"
                    ]
                )
            }
        }
    }

}
