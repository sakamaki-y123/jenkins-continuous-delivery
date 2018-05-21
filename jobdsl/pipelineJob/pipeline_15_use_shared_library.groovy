def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "15_use_shared_library"
def jobDescription = "15_use_shared_library"

def COPY_ARTIFACTS_PROJECTS = """\
04_archive_artifacts
14_run_ansible
"""

pipelineJob(jobName) {
	description(jobDescription)
	keepDependencies(false)
	logRotator {
        numToKeep(5)
        artifactNumToKeep(5)
    }
	parameters {
		choiceParam("START_STAGE_NO", ["1","2","3"], "select start stage no")
		textParam("COPY_ARTIFACTS_PROJECTS", "${COPY_ARTIFACTS_PROJECTS}", "input copy artifacts project separated by newlines")
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
			scriptPath("pipeline/decrative-pipeline/pipeline_15_use_shared_library.groovy")
		}
	}
	disabled(false)
}