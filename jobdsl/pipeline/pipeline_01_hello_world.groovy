folder("decrative-pipeline")

pipelineJob("decrative-pipeline/01_hello_world") {
	description("01_hello_world job")
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
			scriptPath("decrative-pipeline/Jenkinsfile/pipeline_01_hello_world.groovy")
		}
	}
	disabled(false)
}