def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "01_hello_world"
def jobDescription = "01_hello_world job"

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
			scriptPath("pipeline/decrative-pipeline/pipeline_01_hello_world.groovy")
		}
	}
	disabled(false)
}