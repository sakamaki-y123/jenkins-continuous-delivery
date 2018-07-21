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
                        'jobdsl/pipelineJob/pipeline_01_hello_world.groovy',
                        'jobdsl/pipelineJob/pipeline_02_use_sh_step.groovy',
                        'jobdsl/pipelineJob/pipeline_03_write_file.groovy',
                        'jobdsl/pipelineJob/pipeline_04_archive_artifacts.groovy',
                        'jobdsl/pipelineJob/pipeline_05_clean_workspace.groovy',
                        'jobdsl/pipelineJob/pipeline_06_copy_artifacts.groovy',
                        'jobdsl/pipelineJob/pipeline_07_read_json.groovy',
                        'jobdsl/pipelineJob/pipeline_08_parallel_step.groovy',
                        'jobdsl/pipelineJob/pipeline_09_agent_docker.groovy',
                        'jobdsl/pipelineJob/pipeline_10_use_multiple_docker.groovy',
                        'jobdsl/pipelineJob/pipeline_11_read_and_write_yaml.groovy',
                        'jobdsl/pipelineJob/pipeline_12_input_request.groovy',
                        'jobdsl/pipelineJob/pipeline_13_conditional_run_stage.groovy',
                        'jobdsl/pipelineJob/pipeline_14_run_ansible.groovy',
                        'jobdsl/pipelineJob/pipeline_15_use_shared_library.groovy',
                        'jobdsl/pipelineJob/pipeline_16_use_global_variables.groovy',
                        'jobdsl/pipelineJob/pipeline_17_nested_stage.groovy',
                        'jobdsl/pipelineJob/pipeline_18_matrix_build'
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
