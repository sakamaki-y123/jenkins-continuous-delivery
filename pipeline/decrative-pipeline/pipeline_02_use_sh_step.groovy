pipeline {

    agent any

    stages {
        stage('show os information') {
            steps {
                sh 'cat /etc/os-release'
            }
        }
    }
}
