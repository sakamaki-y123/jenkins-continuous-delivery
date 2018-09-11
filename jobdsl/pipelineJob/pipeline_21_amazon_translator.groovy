def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "21_amazon_translator"
def jobDescription = """
21_amazon_translator

日本語を英語に変換するジョブです。
"""

pipelineJob(jobName) {
	description(jobDescription)
	logRotator {
        numToKeep(5)
        artifactNumToKeep(5)
    }
	parameters {
		textParam("TRANCELATE_TEXT", "", "")
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
			scriptPath("pipeline/decrative-pipeline/pipeline_21_amazon_translator.groovy")
		}
	}
	disabled(false)
}
