def call(body)
{
	def config = [:]
	body.resolveStrategy = Closure.DELEGATE_FIRST
	body.delegate = config
	body()
	def BuildFileLocation = config.BuildFileLocation
	def gradleCommands = config.buildCommands
	sh """
	cd ${WORKSPACE}/${BuildFileLocation}
	gradle ${gradleCommands}
}
	