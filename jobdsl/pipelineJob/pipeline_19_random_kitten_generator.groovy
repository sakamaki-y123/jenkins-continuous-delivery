def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "19_random_kitten_generator"
def jobDescription = "create randome kitten image and slack channel."
def cronFormat = '''\
TZ=Asia/Tokyo
0 8 * * *
'''

pipelineJob(jobName) {
	description(jobDescription)
	keepDependencies(false)
	logRotator {
		daysToKeep(30)
		artifactDaysToKeep(30)
    }
	triggers{
        cron( cronFormat )
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
			scriptPath("pipeline/decrative-pipeline/pipeline_19_random_kitten_generator.groovy")
		}
	}
	disabled(false)
}
