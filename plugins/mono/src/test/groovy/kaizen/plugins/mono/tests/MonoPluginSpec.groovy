package kaizen.plugins.mono.tests

import kaizen.plugins.clr.ClrExtension
import kaizen.testing.PluginSpecification

class MonoPluginSpec extends PluginSpecification {
	def 'installs clr'() {
		given:
		def project = projectWithName('foo')

		when:
		project.plugins.apply 'mono'

		then:
		def clr = ClrExtension.forProject(project)
		clr != null
		//clr.runtimeForFrameworkVersion(frameworkVersion) != null

		where:
		frameworkVersion << ['v3.5', 'v4.0']
	}
}
