@Library('jenkins-continuous-delivery')_

pipeline {

    agent any

    stages {
        stage('get random kitten image') {
            steps {
                script{
                    def count = 1
                    def infos =[]
                    for ( pohoto in unsplash.getRandomPhoto("kitten",30)){
                        def downloadUrl = unsplash.getDownloadUrl(pohoto.id)
                        withEnv([
                            "URL=${downloadUrl}",
                            "kittenImage=kitten-image-${count}.jpg"
                        ]) {
                            sh 'curl --fail -o ${kittenImage} ${URL}'
                            def info = """
                            |${count} ${pohoto.description}
                            |${pohoto.links.html}
                            """
                            infos << info.stripMargin().trim()
                        }
                        count++
                    }
                    archiveArtifacts "*.jpg"
                    writeFile file: "source.txt", text: infos.join("\n\n")
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
