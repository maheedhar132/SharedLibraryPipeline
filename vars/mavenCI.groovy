def call(body){
		def config = [:]
		body.resloveStrategy = Closure.DELEGATE_FIRST
		body.delegate = config
		body()
		def buildGoals = config.buildGoals
		def pomFileLocation = config.buildFileLocation
		def propertiesFileLocation = config.propertiesFileLocation
			node{
			stage("buildType build"){
			//Maven stage
			maven{
				mavenGoals = "${buildGoals}"
				pomLocation = "${pomFileLocation}"
				}
				}
			//Sonar Stage
			stage("Sonar Scan"){
				sonar{
				sonarPropertiesFile = "${propertiesFileLocation}"
				}	
			}
			stage("wait for Quality Gate"){
				timeout (time: 1, unit: 'HOURS'){
					def qualityGate = waitForQualityGate()
					if(qualityGate.status != 'OK'){
						error "Pipeline failed due to quality gate failure: ${qualityGate.status}"
					}
				}
			}
			}
}