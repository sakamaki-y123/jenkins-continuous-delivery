def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "14_run_ansible"
def jobDescription = "14_run_ansible"

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
			scriptPath("pipeline/decrative-pipeline/pipeline_14_run_ansible.groovy")
		}
	}
	disabled(false)
}