/**
 * TOPICS
 * - parallel
 *     - https://jenkins.io/doc/book/pipeline/syntax/#parallel
 * - build
 *     - https://jenkins.io/doc/pipeline/steps/pipeline-build-step/#code-build-code-build-a-job
 */

pipeline {

    agent any

    stages {
        stage('parallel build') {
            steps {
                parallel(
                    "01_hello_world": {
                        build '01_hello_world'
                    },
                    "02_use_sh_step": {
                        build '02_use_sh_step'
                    },
                    "03_write_file": {
                        build (
                            job: '03_write_file', 
                            parameters: [
                                text(name: 'OUTPUT_TEXT', value: 'hoge hoge')
                            ]
                        )
                    }
                )
            }
        }
    }
}