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
