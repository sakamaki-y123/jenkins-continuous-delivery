def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "23_download_random_kitten_images"
def jobDescription = "download 30 randome kitten images"

pipelineJob(jobName) {
	description(jobDescription)
	keepDependencies(false)
    logRotator {
        numToKeep(5)
        artifactNumToKeep(5)
    }
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
