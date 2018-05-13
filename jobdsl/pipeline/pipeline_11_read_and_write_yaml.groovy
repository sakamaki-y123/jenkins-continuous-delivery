def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "11_read_and_write_yaml"
def jobDescription = "11_read_and_write_yaml"

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
			scriptPath("pipeline/decrative-pipeline/pipeline_11_read_and_write_yaml.groovy")
		}
	}
	disabled(false)
}