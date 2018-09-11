def translate(text){
    if(text.isEmpty()){
        return text
    }

    withCredentials(
        [usernamePassword(
            credentialsId: 'amazon-translator-access-key',
            usernameVariable: 'accessKey',
            passwordVariable: 'secretkey'
        )]
    ) {
        withEnv([
            "AWS_ACCESS_KEY_ID=${accessKey}", 
            "AWS_SECRET_ACCESS_KEY=${secretkey}",
            'AWS_DEFAULT_REGION=us-east-1',
            'AWS_DEFAULT_OUTPUT=json'
        ]) {
            def jsonmap = [:]
            jsonmap.Text = "${text}"
            jsonmap.SourceLanguageCode = "ja"
            jsonmap.TargetLanguageCode = "en"
            def translateData = readJSON text: groovy.json.JsonOutput.toJson(jsonmap)
            writeJSON file: 'translate.json', json: translateData
            sh 'aws translate translate-text --cli-input-json file://${WORKSPACE}/translate.json > translated.json'
            def translatedData = readJSON file: 'translated.json'
            return translatedData.TranslatedText
        }
    }
}
