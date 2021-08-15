def call(body)
{
	def config = [:]
	body.resolveStrategy = Closure.DELEGATE_FIRST
	body.delegate = config
	body()
	def mavenGoals = config.mavenGoals
	def branchName = config.branchName
	def pomLocation = config.pomLocation
	sh """
	mvn ${mavenGoals} -f ${WORKSPACE}/${pomLocation}
	"""
}