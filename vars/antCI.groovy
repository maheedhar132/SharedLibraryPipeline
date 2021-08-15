def call(body){
		def config = [:]
		body.resolveStrategy = Closure.DELEGATE_FIRST
		body.delegate = config
		body()
		def buildCommands = config.buildCommands
		def buildFileLocation = config.buildFileLocation
		def propertiesFileLocation = config.propertiesFileLocation
		print(config.buildFileLocation)
		print(pomFileLocation)
			node{
			stage("scm checkout"){
			checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/maheedhar132/task.git']]])
			}
			stage("Ant build"){
			//Gradle stage
			ant{
				buildCommands = "${buildCommands}"
				buildFileLocation = "${buildFileLocation}"
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