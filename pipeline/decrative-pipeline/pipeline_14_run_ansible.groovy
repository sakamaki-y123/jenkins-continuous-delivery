/**
 * TOPICS
 * - git
 *     - https://jenkins.io/doc/pipeline/steps/git/
 * - withDockerContainer
 *     - https://jenkins.io/doc/pipeline/steps/docker-workflow/#withdockercontainer-run-build-steps-inside-a-docker-container
 * - ansiblePlaybook
 *     - https://jenkins.io/doc/pipeline/steps/ansible/#ansibleplaybook-invoke-an-ansible-playbook
 */


// sample ansible.cfg
// https://raw.githubusercontent.com/ansible/ansible/devel/examples/ansible.cfg
def ANSIBLE_CONFIG = """\
[defaults]
log_path = ./ansible.log
"""

pipeline {
    agent any

    stages {
        stage('set up ansible files') {
            steps {
                deleteDir()
                git 'https://github.com/chusiang/helloworld.ansible.role'
                writeFile(file: "ansible.cfg", text: "${ANSIBLE_CONFIG}")
            }
        }

        stage('run helloworld.yml') {
            steps {
                withDockerContainer(args: '-u 0', image: 'williamyeh/ansible:alpine3') {
                    echo "show ansible version"
                    sh "ansible --version"

                    echo "run ansible-playbook"
                    ansiblePlaybook(
                        playbook: 'setup.yml',
                        extras: '-c local'
                    )
                }
            }
            post {
                always {
                    archiveArtifacts 'ansible.log'
                }
            }
        }
    }
    post {
        success {
            cleanWs()
        }
    }
}