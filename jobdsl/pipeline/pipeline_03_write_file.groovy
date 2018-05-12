def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "03_write_file"
def jobDescription = "03_write_file"

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
			scriptPath("pipeline/decrative-pipeline/pipeline_03_write_file.groovy")
		}
	}
	disabled(false)
}