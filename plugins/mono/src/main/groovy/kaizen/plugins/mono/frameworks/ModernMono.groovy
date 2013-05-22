package kaizen.plugins.mono.frameworks

import org.gradle.internal.os.OperatingSystem
import org.gradle.process.ExecSpec
import kaizen.plugins.mono.ExecHandler

class ModernMono extends AbstractMono {
	final String runtimeVersion

	ModernMono(OperatingSystem operatingSystem, String prefix, ExecHandler execHandler, String runtimeVersion) {
		super(operatingSystem, prefix, execHandler)
		this.runtimeVersion = runtimeVersion
	}

	@Override
	void setUpCliArguments(ExecSpec execSpec) {
		execSpec.args "--runtime=$runtimeVersion"
	}

	String getCli() {
		operatingSystem.getExecutableName(bin('mono'))
	}
}


