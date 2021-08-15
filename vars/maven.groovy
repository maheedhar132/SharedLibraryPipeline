def call(body)
{
	def config = [:]
	body.resolveStrategy = Closure.DELEGATE_FIRST
	body.delegate = config
	body()
	def mavenGoals = config.mavenGoals
	def branchName = config.branchName
	def pomLocation = config.pomFileLocation
	sh """
	mvn ${WORKSPACE}/settings.xml ${mavenGoals} -f ${WORKSPACE}/${pomLocation}
	"""
}