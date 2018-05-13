def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "08_parallel_step"
def jobDescription = "08_parallel_step"

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
			scriptPath("pipeline/decrative-pipeline/pipeline_08_parallel_step.groovy")
		}
	}
	disabled(false)
}