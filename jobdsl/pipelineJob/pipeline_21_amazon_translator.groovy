def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "21_amazon_translator"
def jobDescription = "trancelate language by amazon translator"

pipelineJob(jobName) {
	description(jobDescription)
	keepDependencies(false)
    logRotator {
        numToKeep(5)
        artifactNumToKeep(5)
    }
	parameters {
		textParam("TRANCELATE_TEXT", "", "")
		stringParam("SOURCE_LANGUAGE_CODE", "ja", "")
		stringParam("TARGET_LANGUAGE_CODE", "en", "")
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
			scriptPath("pipeline/decrative-pipeline/pipeline_21_amazon_translator.groovy")
		}
	}
	disabled(false)
}
