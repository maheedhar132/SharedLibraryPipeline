def call(body)
{
	def config = [:]
	body.resolveStrategy = Closure.DELEGATE_FIRST
	body.delegate = config
	body()
	def buildCommands = config.buildCommands
	def branchName = config.branchName
	def buildFile = config.buildFile
	sh """
	ant ${buildCommands} -f ${buildFile}
	"""
}