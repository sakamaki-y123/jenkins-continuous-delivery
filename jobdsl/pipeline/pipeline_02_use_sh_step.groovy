def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "02_use_sh_step"
def jobDescription = "02_use_sh_step"

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
			scriptPath("pipeline/decrative-pipeline/pipeline_02_use_sh_step.groovy")
		}
	}
	disabled(false)
}