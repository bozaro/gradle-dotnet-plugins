package kaizen.plugins.mono.compilers

import kaizen.plugins.clr.ClrCompiler
import kaizen.plugins.clr.ClrExecSpec
import kaizen.plugins.clr.ClrLanguageNames
import kaizen.plugins.mono.MonoProvider
import org.gradle.internal.os.OperatingSystem
import org.gradle.process.ExecResult
import org.gradle.util.ConfigureUtil

class Mcs implements ClrCompiler {

	final OperatingSystem operationSystem = OperatingSystem.current()

	final MonoProvider monoProvider

	@Lazy
	String mcs = getMcsPath()

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

	String getMcsPath() {
		if (operationSystem.linux) {
			return "/usr/lib/mono/4.5/mcs.exe"
		} else {
			return runtimeForFrameworkVersion('v3.5').lib('2.0', 'mcs.exe')
		}
	}

	private runtimeForFrameworkVersion(String framework) {
		if (operationSystem.linux) {
			monoProvider.runtimeForFrameworkVersion("v4.0")
		} else {
			monoProvider.runtimeForFrameworkVersion(framework)
		}
	}
}
