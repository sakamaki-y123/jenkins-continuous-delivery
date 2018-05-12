def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "04_archive_artifacts"
def jobDescription = "archive_artifacts"

def githubUrl = "sakamaki-y123/jenkins-continuous-delivery"
def githubBranch = "*/master"

pipelineJob(jobName) {
	description(jobDescription)
	keepDependencies(false)
	parameters {
		textParam("OUTPUT_TEXT", "", "")
	}
	definition {
		cpsScm {
			scm {
				git {
					remote {
						github(githubUrl, "https")
					}
					branch(githubBranch)
				}
			}
			scriptPath("pipeline/decrative-pipeline/pipeline_04_archive_artifacts.groovy")
		}
	}
	disabled(false)
}