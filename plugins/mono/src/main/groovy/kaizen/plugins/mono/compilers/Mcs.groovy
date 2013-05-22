package kaizen.plugins.mono.compilers

import kaizen.plugins.clr.ClrCompiler
import kaizen.plugins.clr.ClrExecSpec
import kaizen.plugins.clr.ClrLanguageNames

import org.gradle.process.ExecResult
import org.gradle.util.ConfigureUtil
import kaizen.plugins.mono.MonoProvider

class Mcs implements ClrCompiler {

	final MonoProvider monoProvider

	@Lazy String mcs = runtimeForFrameworkVersion('v3.5').lib('2.0', 'mcs.exe')

	Mcs(MonoProvider monoProvider) {
		this.monoProvider = monoProvider
	}

	@Override
	String getLanguage() {
		ClrLanguageNames.CSHARP
	}

	@Override
	ExecResult exec(Closure compileSpec) {
		def builder = new McsCommandLineBuilder()
		ConfigureUtil.configure(compileSpec, builder)
		def runtime = runtimeForFrameworkVersion(builder.targetFramework)
		def mcs = this.mcs
		runtime.exec { ClrExecSpec execSpec ->
			execSpec.executable mcs
			execSpec.args builder.arguments
		}
	}

	private runtimeForFrameworkVersion(String framework) {
		monoProvider.runtimeForFrameworkVersion(framework)
	}
}
