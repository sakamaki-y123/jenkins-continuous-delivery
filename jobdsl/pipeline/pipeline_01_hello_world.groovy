def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "01_hello_world"
def jobDescription = "01_hello_world job"

def githubUrl = "sakamaki-y123/jenkins-continuous-delivery"
def githubBranch = "*/master"

pipelineJob(jobName) {
	description(jobDescription)
	keepDependencies(false)
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
			scriptPath("pipeline/decrative-pipeline/pipeline_01_hello_world.groovy")
		}
	}
	disabled(false)
}