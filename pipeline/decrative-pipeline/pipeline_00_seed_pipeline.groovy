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
                        'jobdsl/pipeline/pipeline_05_clean_workspace.groovy',
                        'jobdsl/pipeline/pipeline_06_copy_artifacts.groovy',
                        'jobdsl/pipeline/pipeline_07_read_json.groovy',
                        'jobdsl/pipeline/pipeline_08_parallel_step.groovy',
                        'jobdsl/pipeline/pipeline_09_agent_docker.groovy',
                        'jobdsl/pipeline/pipeline_10_use_multiple_docker.groovy',
                        'jobdsl/pipeline/pipeline_11_read_and_write_yaml.groovy',
                        'jobdsl/pipeline/pipeline_12_input_request.groovy',
                        'jobdsl/pipeline/pipeline_13_conditional_run_stage.groovy"'
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
