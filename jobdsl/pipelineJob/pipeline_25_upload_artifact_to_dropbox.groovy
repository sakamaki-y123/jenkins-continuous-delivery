
def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "25_upload_artifact_to_dropbox"
def jobDescription = "upload artifact to dropbox"

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
			scriptPath("pipeline/decrative-pipeline/pipeline_25_upload_artifact_to_dropbox.groovy")
		}
	}
	disabled(false)
}
