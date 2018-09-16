
def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "24_download_beautiful_landscape-video"
def jobDescription = "download beautiful landscape video"

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
			scriptPath("pipeline/decrative-pipeline/pipeline_24_download_beautiful_landscape_video.groovy")
		}
	}
	disabled(false)
}
