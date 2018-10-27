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
        sh "python tweet.py -t '${tweet}'"
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
        sh "python tweet_media.py -t '${tweet}' -f '${media}'"
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
        sh "python tweet_video.py -t '${tweet}' -f '${media}'"
    }
}

def searchVideo( keyword ,result_file,credentialFileId = 'twitter-config.py'){
    withCredentials([
        file(credentialsId: credentialFileId, variable: 'CONFIG_PY')
    ]) {
        sh "cp ${CONFIG_PY} ${WORKSPACE}/config.py"
    }
    withDockerContainer(args: '-u 0', image: 'python:3.6.7-alpine3.6') {
        sh "pip install requests requests_oauthlib TwitterAPI tweepy"
        writeFile file: 'search_videos.py', text: libraryResource('twitter/search_videos.py')
        sh "python search_videos.py -k '${keyword}' -f ${result_file} -r popular"
    }
}