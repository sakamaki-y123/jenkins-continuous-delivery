def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "16_use_global_variables"
def jobDescription = "16_use_global_variables"

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
			scriptPath("pipeline/decrative-pipeline/pipeline_16_use_global_variables.groovy")
		}
	}
	disabled(false)
}
