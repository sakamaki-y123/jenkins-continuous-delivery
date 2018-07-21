/**
 * TOPICS
 * - Matrix-like flow
 *   https://support.cloudbees.com/hc/en-us/articles/115000088431-Create-a-Matrix-like-flow-with-Pipeline
 */

def flatMatrix(){
    def axisNode = ["maven:3-jdk-8-alpine","maven:3-jdk-8-slim"]
    def axisTool = ["mvn -version","java -version"]
    tasks = [:]
    
    for(int i=0; i< axisNode.size(); i++) {
        def axisNodeValue = axisNode[i]
        for(int j=0; j< axisTool.size(); j++) {
            def axisToolValue = axisTool[j]
                tasks["${axisNodeValue}/${axisToolValue}"] = {
                withDockerContainer(args: '-u 0', image: axisNodeValue) {
                    sh "${axisToolValue}"
                }
            }
        }
    }
    return tasks
}

def nestedMatrix(){
    def axisNode = ["maven:3-jdk-8-alpine","maven:3-jdk-8-slim"]
    def axisTool = ["mvn -version","java -version"]
    tasks = [:]
    
    for(int i=0; i< axisNode.size(); i++) {
        def axisNodeValue = axisNode[i]
        def subTasks = [:]
        for(int j=0; j< axisTool.size(); j++) {
            def axisToolValue = axisTool[j]
            subTasks["${axisNodeValue}/${axisToolValue}"] = {
                sh "${axisToolValue}"
            }
        }

        tasks["${axisNodeValue}"] = {
            withDockerContainer(args: '-u 0', image: axisNodeValue) {
                parallel subTasks
            }
        }
    }
    return tasks
}

pipeline {
    agent any

    stages {
        stage ("flat matrix") {
            steps {
                script {
                    parallel flatMatrix()
                }
            }
        }

        stage ("nested matrix") {
            steps {
                script {
                    parallel nestedMatrix()
                }
            }
        }
    }
}