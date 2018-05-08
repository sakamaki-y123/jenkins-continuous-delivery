folder("decrative-pipeline")

pipelineJob("decrative-pipeline/01_hello_world") {
	description()
	keepDependencies(false)
	definition {
		cpsScm {
			scm {
				git {
					remote {
						github("sakamaki-y123/jenkins-continuous-delivery", "https")
					}
					branch("*/master")
				}
			}
			scriptPath("decrative-pipeline/Jenkinsfile/01_hello_world.groovy")
		}
	}
	disabled(false)
}