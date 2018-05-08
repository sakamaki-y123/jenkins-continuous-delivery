# jenkins-continuous-delivery

## set up jenkins seed job

1. Jenkins にログイン
2. 「新規ジョブ作成」
3. 「Pipeline」を選択し、任意の名前でジョブを作成する * 以降 seedjob と表現する
4.  seedjobに次の設定をする
    -  Pipeline - Definition: "Pipeline script from SCM"
        -  SCM: Git
            - リポジトリ
                - リポジトリURL: "https://github.com/sakamaki-y123/jenkins-continuous-delivery"
                - Script Path: "pipeline/decrative-pipeline/pipeline_00_seed_pipeline.groovy"
5. グローバルセキュリティの設定を開く
   http://{YOUR_JENKINS_HOST}/configureSecurity/
6. Enable script security for Job DSL scripts のチェックを外す。
   - "ERROR: script not yet approved for use" が出てしまうため。
