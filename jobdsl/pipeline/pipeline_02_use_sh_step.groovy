def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "02_use_sh_step"
def jobDescription = "02_use_sh_step"

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
			scriptPath("pipeline/decrative-pipeline/pipeline_02_use_sh_step.groovy")
		}
	}
	disabled(false)
}