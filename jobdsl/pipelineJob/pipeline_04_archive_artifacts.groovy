def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "04_archive_artifacts"
def jobDescription = "04_archive_artifacts"

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
			scriptPath("pipeline/decrative-pipeline/pipeline_04_archive_artifacts.groovy")
		}
	}
	disabled(false)
}