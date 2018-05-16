def createFolder(String folderPath) {
    folderPathString = "";
    folderPath.split("/").each { path ->
        if (!folderPathString.isEmpty()) {
            folderPathString = "${folderPathString}/"
        }
        folderPathString = "${folderPathString}${path}"
        folder("${folderPathString}"){
            description(
                'repository: https://github.com/sakamaki-y123/jenkins-continuous-delivery' 
			)
        }
    }
}

createFolder("${FOLDER_PATH}")