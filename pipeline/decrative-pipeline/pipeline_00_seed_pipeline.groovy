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
                        'jobdsl/pipeline/pipeline_04_archive_artifacts.groovy',
                        'jobdsl/pipeline/pipeline_05_clean_workspace.groovy'
                    ].join('\n'),
                    additionalParameters: [
                        FOLDER_PATH: "${FOLDER_PATH}",
                        GIT_HUB_OWNER_AND_PROJECT: "sakamaki-y123/jenkins-continuous-delivery",
                        GIT_HUB_PROTOCOL:"https",
                        GIT_HUB_BRANCH: "*/master"
                    ]
                )
            }
        }
    }

}
