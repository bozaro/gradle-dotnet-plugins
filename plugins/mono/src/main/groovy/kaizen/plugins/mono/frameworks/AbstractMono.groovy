package kaizen.plugins.mono.frameworks

import kaizen.commons.Paths
import kaizen.plugins.clr.Clr
import kaizen.plugins.clr.internal.DefaultClrExecSpec

import org.gradle.internal.os.OperatingSystem
import org.gradle.process.ExecResult
import org.gradle.process.ExecSpec
import org.gradle.util.ConfigureUtil
import kaizen.plugins.mono.Mono
import kaizen.plugins.mono.ExecHandler

abstract class AbstractMono implements Clr, Mono {

	String prefix
	final OperatingSystem operatingSystem
	final ExecHandler execHandler

	AbstractMono(OperatingSystem operatingSystem, String prefix, ExecHandler execHandler) {
		this.operatingSystem = operatingSystem
		this.prefix = prefix
		this.execHandler = execHandler
	}

	@Override
	String lib(String profile, String fileName) {
		Paths.combine prefix, 'lib', 'mono', profile, fileName
	}

	@Override
	ExecResult exec(Closure execSpecClosure) {
		def clrExecSpec = new DefaultClrExecSpec()
		ConfigureUtil.configure(execSpecClosure, clrExecSpec)
		assert clrExecSpec.executable

		def executable = canonicalFileFor(clrExecSpec.executable)
		def arguments = clrExecSpec.allArguments
		execHandler.exec { ExecSpec execSpec ->
			execSpec.executable cli
			setUpCliArguments execSpec
			execSpec.args '--debug'
			execSpec.args executable
			execSpec.args arguments
			execSpec.setIgnoreExitValue(clrExecSpec.isIgnoreExitValue())
		}
	}

	void setUpCliArguments(ExecSpec execSpec) {
	}

	abstract String getCli()

	String bin(String path) {
		Paths.combine prefix, 'bin', path
	}

	def canonicalFileFor(String file) {
		new File(file).canonicalFile
	}

}
