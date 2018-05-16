def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "03_write_file"
def jobDescription = "03_write_file"

pipelineJob(jobName) {
	description(jobDescription)
	keepDependencies(false)
	logRotator {
        numToKeep(5)
        artifactNumToKeep(5)
    }
	parameters {
		textParam("OUTPUT_TEXT", "", "")
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
			scriptPath("pipeline/decrative-pipeline/pipeline_03_write_file.groovy")
		}
	}
	disabled(false)
}