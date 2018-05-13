def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "10_use_multiple_docker"
def jobDescription = "10_use_multiple_docker"

pipelineJob(jobName) {
	description(jobDescription)
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
			scriptPath("pipeline/decrative-pipeline/pipeline_10_use_multiple_docker.groovy")
		}
	}
	disabled(false)
}