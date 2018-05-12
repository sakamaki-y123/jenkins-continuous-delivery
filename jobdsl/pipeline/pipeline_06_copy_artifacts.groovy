def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "06_copy_artifacts"
def jobDescription = "06_copy_artifacts"

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
			scriptPath("pipeline/decrative-pipeline/pipeline_06_copy_artifacts.groovy")
		}
	}
	disabled(false)
}