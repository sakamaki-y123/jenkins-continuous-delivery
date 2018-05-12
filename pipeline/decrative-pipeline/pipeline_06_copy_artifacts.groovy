/**
* TOPICS
* - parameters
*     - https://jenkins.io/doc/book/pipeline/syntax/#parameters
* - deleteDir
*     - https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/#code-deletedir-code-recursively-delete-the-current-directory-from-the-workspace
* - copyArtifacts
*     - https://jenkins.io/doc/pipeline/steps/copyartifact/#code-copyartifacts-code-copy-artifacts-from-another-project
* - script
*     - https://jenkins.io/doc/book/pipeline/syntax/#script
* - findFiles
*     - https://jenkins.io/doc/pipeline/steps/pipeline-utility-steps/#findfiles-find-files-in-the-workspace
*/

pipeline {

    agent any

    parameters {
        string(
            name: 'COPY_SOURCE_PROJECT', 
            defaultValue: "04_archive_artifacts", 
            description: 'Name of source project for copying of artifact(s).'
        )
    }

    stages {

        stage('delete workspace') {
            steps {
                deleteDir()
            }
        }

        stage('copy artifacts') {
            steps {
                copyArtifacts(projectName:"${params.COPY_SOURCE_PROJECT}")
            }
        }

        stage('find files') {
            steps {
                script{
                    files = findFiles(glob: '*.*')
                    for ( file in files ){
                        echo file.name
                    }
                }
            }
        }
    }
}
