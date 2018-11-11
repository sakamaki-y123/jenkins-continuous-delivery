def postHatenaBlog(title,postFile,categolies = [], isDraft = true, credentialFileId = 'hatena-blog-config.py'){
    withCredentials([
        file(credentialsId: credentialFileId, variable: 'CONFIG_PY')
    ]) {
        sh "cp ${CONFIG_PY} ${WORKSPACE}/config.py"
    }
    withDockerContainer(args: '-u 0', image: 'python:3.7-alpine3.7') {
        sh "pip install requests"
        writeFile file: 'post_hatena_blog.py', text: libraryResource('hatena/post_hatena_blog.py')
        withEnv(["TITLE=${title}"]) {
            postCategolies = categolies.join(" ")
            publishOption = isDraft ? "--draft" : "--publish"
            sh "python post_hatena_blog.py -t '$TITLE' -f '${postFile}' -c ${postCategolies} ${publishOption}"
        }
    }
}