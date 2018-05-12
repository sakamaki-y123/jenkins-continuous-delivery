def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "07_get_current_date"
def jobDescription = "07_get_current_date"

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
			scriptPath("pipeline/decrative-pipeline/pipeline_07_get_current_date.groovy")
		}
	}
	disabled(false)
}