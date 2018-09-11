def folderPath = "${FOLDER_PATH}"
def jobName = "${FOLDER_PATH}" + "/" + "20_search_youtube_video"
def jobDescription = """
20_search_youtube_video

parameterの説明は以下を参照してください。

https://developers.google.com/youtube/v3/docs/search/list?hl=ja
"""

pipelineJob(jobName) {
	description(jobDescription)
	logRotator {
        numToKeep(5)
        artifactNumToKeep(5)
    }
	parameters {
		stringParam("question", "", "youtubeの検索条件を指定する")
		stringParam("maxResults", 5, "検索結果の取得数 1~50")
		choiceParam("videoDuration", ["any", "long", "medium", "short"], "videoDuration パラメータは、動画の検索結果を期間に基づいてフィルタリングします。")
		stringParam("publishedAfter", "2018-08-25T00:00:00Z", "いつ以降のものを取得するか")
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
			scriptPath("pipeline/decrative-pipeline/pipeline_20_search_youtube_video.groovy")
		}
	}
	disabled(false)
}
