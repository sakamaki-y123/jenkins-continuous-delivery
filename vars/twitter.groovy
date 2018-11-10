def getTimeline(count = 5, credentialFileId = 'twitter-config.py'){
    withCredentials([
        file(credentialsId: credentialFileId, variable: 'CONFIG_PY')
    ]) {
        sh "cp ${CONFIG_PY} ${WORKSPACE}/config.py"
    }
    withDockerContainer(args: '-u 0', image: 'python:3.7-alpine3.7') {
        sh "pip install requests requests_oauthlib"
        writeFile file: 'timeline.py', text: libraryResource('twitter/timeline.py')
        sh "python timeline.py -c ${count}"
    }
}

def tweet(tweet, credentialFileId = 'twitter-config.py'){
    withCredentials([
        file(credentialsId: credentialFileId, variable: 'CONFIG_PY')
    ]) {
        sh "cp ${CONFIG_PY} ${WORKSPACE}/config.py"
    }
    withDockerContainer(args: '-u 0', image: 'python:3.7-alpine3.7') {
        sh "pip install requests requests_oauthlib"
        writeFile file: 'tweet.py', text: libraryResource('twitter/tweet.py')
        withEnv(["TWEET=${tweet}"]) {
            sh "python tweet.py -t '$TWEET'"
        }
    }
}

def tweetMedia(tweet,media, credentialFileId = 'twitter-config.py'){
    withCredentials([
        file(credentialsId: credentialFileId, variable: 'CONFIG_PY')
    ]) {
        sh "cp ${CONFIG_PY} ${WORKSPACE}/config.py"
    }
    withDockerContainer(args: '-u 0', image: 'python:3.7-alpine3.7') {
        sh "pip install requests requests_oauthlib"
        writeFile file: 'tweet_media.py', text: libraryResource('twitter/tweet_media.py')
        withEnv(["TWEET=${tweet}"]) {
            sh "python tweet_media.py -t '$TWEET' -f '${media}'"
        }
    }
}

def tweetVideo(tweet,media, credentialFileId = 'twitter-config.py'){
    withCredentials([
        file(credentialsId: credentialFileId, variable: 'CONFIG_PY')
    ]) {
        sh "cp ${CONFIG_PY} ${WORKSPACE}/config.py"
    }
    withDockerContainer(args: '-u 0', image: 'python:3.7-alpine3.7') {
        sh "pip install requests requests_oauthlib TwitterAPI"
        writeFile file: 'tweet_video.py', text: libraryResource('twitter/tweet_video.py')
        withEnv(["TWEET=${tweet}"]) {
            sh "python tweet_video.py -t '$TWEET' -f '${media}'"
        }
    }
}

def searchVideo( keyword,targetCount,credentialFileId = 'twitter-config.py'){
    withCredentials([
        file(credentialsId: credentialFileId, variable: 'CONFIG_PY')
    ]) {
        sh "cp ${CONFIG_PY} ${WORKSPACE}/config.py"
    }
    withDockerContainer(args: '-u 0', image: 'python:3.6.7-alpine3.6') {
        sh "pip install requests requests_oauthlib TwitterAPI tweepy"
        writeFile file: 'search_videos.py', text: libraryResource('twitter/search_videos.py')
        int i = 1
        waitUntil {
            sh "python search_videos.py -k '${keyword}' -f ${outPutJsonPath} -m ${targetCount} -r recent -c 5000"   
        }
    }
}