# jenkins-continuous-delivery

## set up jenkins seed job
1. Jenkins にログイン
2. 「新規ジョブ作成」
3. 「Pipeline」を選択し、任意の名前でジョブを作成する * 以降 seedjob と表現する
4.  seedjobに次の設定をする
    -  Pipeline - Definition: "Pipeline script from SCM"
        -  SCM: Git
            - リポジトリ
                - リポジトリURL: https://github.com/sakamaki-y123/jenkins-continuous-delivery
                - Script Path: pipeline/decrative-pipeline/pipeline_00_seed_pipeline.groovy

## add plugins
1. job DSL
2. Pipeline Utility Steps
3. Blue Ocean


## disable Enable script security for Job DSL scripts
1. グローバルセキュリティの設定を開く
   http://{YOUR_JENKINS_HOST}/configureSecurity/
2. Enable script security for Job DSL scripts のチェックを外す。
    - "ERROR: script not yet approved for use" が出てしまうため。

## enable docker in ubuntu 16.04
### docker install
```sh
sudo apt-get update
sudo apt-get install apt-transport-https ca-certificates
sudo apt-key adv \
    --keyserver hkp://ha.pool.sks-keyservers.net:80 \
    --recv-keys 58118E89F3A912897C070ADBF76221572C52609D
echo "deb https://apt.dockerproject.org/repo ubuntu-xenial main" | sudo tee /etc/apt/sources.list.d/docker.list
sudo apt-get update
sudo apt-get install linux-image-extra-$(uname -r) linux-image-extra-virtual
sudo apt-get install docker-engine
sudo service docker start
sudo usermod -aG docker jenkins
sudo reboot
```
