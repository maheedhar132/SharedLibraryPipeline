def call(body)
{
	def config = [:]
	body.resolveStrategy = Closure.DELEGATE_FIRST
	body.delegate = config
	body()
	def branchName = config.branchName
	def buildFile = config.buildFile
	def tagName = config.tagName
	sh """
	docker build -f ${buildFile} ${tagName}:${BUILD_NUMBER} .
	"""
}