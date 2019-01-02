def getGroupId(groupName){
    allGroups = readJSON file: 'groups.json'
    for(group in allGroups){
        if( group.value.name == groupName){
            return group.key
        }
    }
}

def writeSummary(posts){
    def summary = []
    for( post in posts){
        def tags = []
        for(tag in post.value.tags){
            tags << tag['key']
        }
        summary << "id   : ${post.key}"
        summary << "title: ${post.value.title}"
        summary << "tags : ${tags.join(',')}"
        summary << "url  : ${post.value.url}"
        summary << ""
    }
    writeFile file: 'summary.txt', text: summary.join("\n")
}

def getTag(post,groupName){
    def tags = []
    for(tag in post.value.tags){
        tags << tag['key']
    }
    if( tags.isEmpty()){
        tags << groupName
    }
    return tags.join(',')
}

def getTargetType(type){
    if (type == 'fresh'){
        return 'vote'
    } else {
        return type
    }
}

def getDownloadFileName(post){
    if("${post.value.media_url}".endsWith(".mp4")){
        return "${post.key}.mp4"
    } else if ("${post.value.media_url}".endsWith(".jpg")){
        return "${post.key}.jpg"
    }
}

def downloadMedia(posts){
    for( post in posts){
        if("${post.value.media_url}"){
            retry(3){
                def download_media_name = getDownloadFileName(post)
                sh "curl --fail -o ${download_media_name} ${post.value.media_url}"
                post.value.video_name = download_media_name.toString()
            }
        }
    }
}