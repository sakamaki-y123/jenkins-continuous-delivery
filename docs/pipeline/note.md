# use jobdsl in pipeline

* 以下のように jobDsl のステップに 複数のtargetsを指定できる。

```groovy
jobDsl(
    targets: [
        'jobdsl/folder/folder.groovy',
        'jobdsl/pipeline/pipeline_01_hello_world.groovy',
        'jobdsl/pipeline/pipeline_02_use_sh_step.groovy'
    ].join('\n'),
    additionalParameters: [
        FOLDER_PATH: "${FOLDER_PATH}"
    ]
)
```

https://github.com/jenkinsci/job-dsl-plugin/wiki/User-Power-Moves#use-job-dsl-in-pipeline-scripts

# shared library

様々なパイプラインで共通に利用できるロジックをshared library に定義することで冗長性を減らしコードをドライにすることができる。

参考ページ

https://jenkins.io/doc/book/pipeline/shared-libraries/#directory-structure
https://automatingguy.com/2017/12/29/jenkins-pipelines-shared-libraries/

## package階層

<pre>
(root)
+- src                     # Groovy source files
|   +- main
|       +- groovy
|           +- Utils.groovy  # for main.groovy.Utils class
+- vars
|   +- foo.groovy          # for global 'foo' variable
|   +- foo.txt             # help for 'foo' variable
+- resources               # resource files (external libraries only)
|   +- org
|       +- foo
|           +- bar.json    # static helper data for org.foo.Bar
</pre>

* src

    標準のJavaソースディレクトリ構造。
    このディレクトリは、パイプラインを実行するときにクラスパスに追加されます。
    import を使うことで呼び出せるようになる。

* vars

    パイプラインからアクセス可能なグローバル変数/関数を定義する。
    スクリプトから常時参照可能なcustome stepを用意する際に使う。

* resources

    関連する非Groovyのファイルをロードするために使用します。
    主な用途 yaml,json,shなどを置いておき適宜呼び出す.
## library の作成

サンプル

return this がポイントらしい。

```groovy
package main.groovy.pipeline.library;

def boolean skipStage( String startStageNo, String stageNo ){
    if( startStageNo.toInteger() <= stageNo.toInteger() ){
        return false
    } else {
        return true
    }
}

return this

```

## shared library の設定
Jenkinsの管理 > システムの設定 > Global Pipeline Libraries
からshared library を使うリポジトリを指定する。

* name ：任意の名前 

     例: jenkins-continuous-delivery

* default version : ブランチ、タグ、コミットのハッシュ値等を入れる。

    例: master

* Load implicitly: @library が必要なくなるオプション。今まで通り使うならfalse
* Allow default version to be overridden: オンの場合ライブラリのカスタムバージョンを選択することができるらしい。 default true 
* Include @Library changes in job recent changes : default true
* Retrieval method : gitを使いたいので Modern SCM を選ぶ
* Git : https://github.com/sakamaki-y123/jenkins-continuous-delivery


## library の呼び出し
### 1. src ディレクトリにコミットしたもの。

scripted pipeline から呼び出しが可能

- point
    - import
    - new

```groovy
@Library('jenkins-continuous-delivery')
import main.groovy.pipeline.library.Utils

pipeline {
    agent any

    stages {
        stage('stage1') {
            steps {
                script{
                    def utils = new Utils()
                    def final String START_STAGE_NO = "1"
                    def final String STAGE_NO = "2"
                    def shouldSkip = utils.skipStage( START_STAGE_NO, STAGE_NO )
                }
            }
        }
    }
}
```

### 2. vars ディレクトリにコミットしたもの。

- point
    - `_` が必要。

```groovy
@Library('jenkins-continuous-delivery')_

pipeline {
    agent any
    stages{
        stage ('Example') {
            steps {
                script { 
                    log.info "executed by " + user.getFullName()
                }
            }
        }
    }
}
```

### 既存モジュールと同じ名前のファンクションは作れない。

findFiles はすでにあるモジュールなので同じ名前では作れない。


### pipleine のファンクション

@NonCPS について
http://arasio.hatenablog.com/entry/2016/10/08/220843