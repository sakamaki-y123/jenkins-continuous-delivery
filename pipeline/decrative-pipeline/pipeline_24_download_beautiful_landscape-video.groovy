@Library('jenkins-continuous-delivery')_

pipeline {

    agent any

    stages {
        stage('get beautiful landscape videos') {
            steps {
                script{
                    def count = 1
                    def infos =[]
                    def videoInfos = pixabay.searchVideos("beautiful+mountain","nature","",0,0,"latest",1,3)
                    echo videoInfos.toString()
                    for ( videoInfo in videoInfos.hits){
                        def downloadUrl = videoInfo.videos.large.url 
                        if (downloadUrl.isEmpty()){
                            downloadUrl = videoInfo.videos.medium.url
                        }
                        if (downloadUrl.isEmpty()){
                            continue;
                        }
                        downloadFullUrl = downloadUrl + "&download=1"
                        withEnv([
                            "URL=${downloadFullUrl}",
                            "videoName=beautiful-landscape-${count}.mp4"
                        ]) {
                            sh 'curl --fail -L -o ${videoName} ${URL}'
                            def info = """
                            |create: ${videoInfo.user}
                            |${videoInfo.pageURL}
                            """
                            infos << info.stripMargin().trim()
                        }
                        count++
                    }
                    archiveArtifacts "*.mp4"
                    writeFile file: "source.txt", text: infos.join("\n")
                    archiveArtifacts "source.txt"
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
