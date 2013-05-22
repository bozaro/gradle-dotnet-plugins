package kaizen.plugins.mono.tests

import kaizen.commons.Paths
import kaizen.testing.OperatingSystemSensitiveSpecification
import org.gradle.process.ExecResult
import org.gradle.process.ExecSpec
import org.gradle.util.ConfigureUtil
import spock.lang.Unroll
import kaizen.plugins.mono.frameworks.ModernMono
import kaizen.plugins.mono.ExecHandler

class MonoFrameworkSpec extends OperatingSystemSensitiveSpecification {

	@Unroll
	def 'exec runs #monoExecutable in debug mode by default on #operatingSystem'() {
		given:
		def execHandler = Mock(ExecHandler)
		def mono = new ModernMono(operatingSystem, 'Mono', execHandler, 'v4.0')

		def execSpec = Mock(ExecSpec)
		def execResult = Mock(ExecResult)

		def expectedMono = Paths.combine 'Mono', 'bin', monoExecutable
		def expectedExecutable = new File('foo.exe').canonicalFile

		when:
		def result = mono.exec {
			executable 'foo.exe'
			args 'a', 'b', 'c'
		}

		then:
		1 * execHandler.exec({ ConfigureUtil.configure(it, execSpec) }) >> execResult
		1 * execSpec.executable(expectedMono)
		1 * execSpec.args('--runtime=v4.0')
		1 * execSpec.args('--debug')
		1 * execSpec.args(expectedExecutable)
		1 * execSpec.args(['a', 'b', 'c'])
		result == execResult
		0 * _

		where:
		operatingSystem | monoExecutable
		windows()       | 'mono.exe'
		osx()           | 'mono'
	}
}
