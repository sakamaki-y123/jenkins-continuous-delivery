

pipeline {

    agent any

    environment {
        SEARCH_RESULT_JSON = "search-result.json"
        VIDEO_CATEGORIES_JSON = "video-category.json"
        YOUTUBE_API_ROOT_URL = "https://www.googleapis.com/youtube/v3/"        
    }

    stages {
        stage('get youtube api result') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'YOUTUBE_API_KEY', variable: 'YOUTUBE_API_KEY')
                    ]) {
                        parallel(
                            "search": {
                                def searchParams = []
                                searchParams.add("part=snippet")
                                searchParams.add("type=video")
                                def apiQuestion = "${question}".replaceAll(' ','%20').replaceAll('　','%20')
                                searchParams.add("q=${apiQuestion}")
                                searchParams.add("maxResults=${maxResults}")
                                searchParams.add("videoDuration=${videoDuration}")
                                searchParams.add("order=viewCount")
                                searchParams.add("publishedAfter=${publishedAfter}")
                                searchParams.add("key=${YOUTUBE_API_KEY}")
                                apiSeachParams = searchParams.join('\\&')
                                def apiSearchUrl = "${YOUTUBE_API_ROOT_URL}search?${apiSeachParams}"
                                sh "curl --fail -o ${SEARCH_RESULT_JSON} ${apiSearchUrl}"
                                archiveArtifacts "${SEARCH_RESULT_JSON}"
                            },
                            "video category": {
                                def videoCategoryParams = []
                                videoCategoryParams.add("part=snippet")
                                videoCategoryParams.add("regionCode=jp")
                                videoCategoryParams.add("key=${YOUTUBE_API_KEY}")
                                apiVideoCategoriesGParams = videoCategoryParams.join('\\&')
                                def videoCategoriesUrl = "${YOUTUBE_API_ROOT_URL}videoCategories?${apiVideoCategoriesGParams}"
                                sh "curl --fail -o ${VIDEO_CATEGORIES_JSON} ${videoCategoriesUrl}"
                                archiveArtifacts "${VIDEO_CATEGORIES_JSON}"
                            }
                        )
                    }
                }
            }
        }

        stage ('send slack message'){
            steps {
                script{
                    seachResult = readJSON file: "${SEARCH_RESULT_JSON}"
                    videoCategoriesResult = readJSON file: "${VIDEO_CATEGORIES_JSON}"
                    for(result in seachResult.items){
                        withCredentials([
                            usernamePassword(credentialsId: 'slack', usernameVariable: 'slackTeam',passwordVariable: 'slackToken'),
                            string(credentialsId: 'YOUTUBE_API_KEY', variable: 'YOUTUBE_API_KEY')
                        ]) {
                            def videoParams = []
                            videoParams.add("id=${result.id.videoId}")
                            videoParams.add("key=${YOUTUBE_API_KEY}")
                            apiVideoParams = videoParams.join('\\&')
                            
                            def videoSnippetUrl = "${YOUTUBE_API_ROOT_URL}videos?part=snippet\\&${apiVideoParams}"
                            def videoStatisticsUrl = "${YOUTUBE_API_ROOT_URL}videos?part=statistics\\&${apiVideoParams}"

                            def videoSnippetJson = "video-snippet-${result.id.videoId}.json"
                            sh "curl --fail -o ${videoSnippetJson} ${videoSnippetUrl}"
                            archiveArtifacts "${videoSnippetJson}"
                            videoSnippetResult = readJSON file: "${videoSnippetJson}"

                            def videoStatisticsJson = "video-statistics-${result.id.videoId}.json"
                            sh "curl --fail -o ${videoStatisticsJson} ${videoStatisticsUrl}"
                            archiveArtifacts "${videoStatisticsJson}"
                            videoStatisticsResult = readJSON file: "${videoStatisticsJson}"
                            def videoCategory = ""
                            for ( category in videoCategoriesResult.items){
                                if ("${category.id}" == "${videoSnippetResult.items[0].snippet.categoryId}") {
                                    videoCategory = category.snippet.title
                                    break;
                                }
                            }
                            
                            def message = """
                            |https://www.youtube.com/watch?v=${result.id.videoId}
                            |
                            |● Title
                            |${result.snippet.title}
                            |● Description
                            |${result.snippet.description}
                            |● category
                            |${videoCategory}
                            |● viewCount
                            |${videoStatisticsResult.items[0].statistics.viewCount}
                            |● channelTitle 
                            |${result.snippet.channelTitle}
                            """
                            echo message.stripMargin()
                            slackSend(
                                channel: '#youtube_search',
                                color: 'good',
                                message: message.stripMargin(),
                                teamDomain: "${slackTeam}",
                                token: "${slackToken}"
                            )
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
