pipeline {

    agent any

    stages {
        stage('create pipeline jobs') {
            steps {
                jobDsl targets: 'jobdsl/pipeline/pipeline_01_hello_world.groovy'
            }
        }
    }

}
