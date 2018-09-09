pipeline {

    agent any

    stages {
        stage('download hot bokete images') {
            steps {
                script{                    
                    int i = 1   
                    for (int page = 1; page < 4; page++) {
                        withEnv([
                            "URL=https://bokete.jp/boke/hot?page=${page}"
                        ]) {
                            def hotPageHtml = sh( returnStdout: true, script: 'curl --fail ${URL}')
                            for( line in hotPageHtml.split("\n")){
                                if(line.trim().startsWith('<a href="https://stamp.bokete.jp/')){
                                    imageUrl = line.trim().replaceFirst('<a href="','').replaceFirst('" target="_blank">','')
                                    sh "curl --fail -o bokete_${i}.png ${imageUrl}"
                                    i ++
                                }
                            }
                        }
                    }
                    archiveArtifacts '*.png'
                }
            }
        }
    }
}
