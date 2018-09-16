def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "20_seach_youtube_video"
def jobDescription = "seach youtube video"

pipelineJob(jobName) {
	description(jobDescription)
	keepDependencies(false)
    logRotator {
        numToKeep(5)
        artifactNumToKeep(5)
    }
	parameters {
		stringParam("question", "", "youtubeの検索条件を指定する")
		stringParam("maxResults", "5", "検索結果の取得数 1~50")
		choiceParam("videoDuration", ["any","long","medium","short"], "videoDuration パラメータは、動画の検索結果を期間に基づいてフィルタリングします。")
		stringParam("publishedAfter", "2018-09-01T00:00:00Z", "いつ以降のものを取得するかを指定する")
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
			scriptPath("pipeline/decrative-pipeline/pipeline_20_seach_youtube_video.groovy")
		}
	}
	disabled(false)
}
