pipelineJob("development/sakamaki_y/kitten_image_generator_pipeline") {
    description("猫の画像をダウンロードして保存するジョブ")
    keepDependencies(false)
    parameters {
        stringParam("KITTEN_IMAGE_FILE_NAME", "kitten-image.jpg", "保存する猫の画像のファイル名")
    }
    disabled(false)

    throttleConcurrentBuilds {
        maxPerNode(1)
        maxTotal(1)
        throttleDisabled(false)
    }

    logRotator {
        numToKeep(5)
        artifactNumToKeep(5)
    }

    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        github("sakamaki-y123/jenkins-continuous-delivery", "https")
                    }
                    branch("*/master")
                }
            }
            scriptPath("pipeline/decrative-pipeline/pipeline_26_step_up_jenkins_basic_part.groovy")
        }
    }
}
