def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "23_download_random_kitten_images"
def jobDescription = """
23_download_random_kitten_images
unsplash api を使って、猫の画像を取得します。
"""

pipelineJob(jobName) {
	description(jobDescription)
	logRotator {
        numToKeep(5)
        artifactNumToKeep(5)
    }
	keepDependencies(false)
	definition {
		cpsScm {
			scm {
				git {
					remote {
						github("${GIT_HUB_OWNER_AND_PROJECT}", "${GIT_HUB_PROTOCOL}")
					}
					branch("${GIT_HUB_BRANCH}")
				}
			}
			scriptPath("pipeline/decrative-pipeline/pipeline_23_download_random_kitten_images.groovy")
		}
	}
	disabled(false)
}
