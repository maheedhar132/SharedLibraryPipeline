def call(body)
{
	def config = [:]
	body.resolveStrategy = Closure.DELEGATE_FIRST
	body.delegate = config
	body()
	def mavenGoals = config.buildCommands
	def branchName = config.branchName
	def buildFileLocation = config.buildFileLocation
	sh """
	mvn ${WORKSPACE}/settings.xml ${mavenGoals} -f ${WORKSPACE}/${pomLocation}
	"""
}