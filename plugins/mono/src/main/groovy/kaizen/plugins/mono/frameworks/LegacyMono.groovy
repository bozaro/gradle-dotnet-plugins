package kaizen.plugins.mono.frameworks

import org.gradle.internal.os.OperatingSystem
import kaizen.plugins.mono.ExecHandler

class LegacyMono extends AbstractMono {

	LegacyMono(OperatingSystem operatingSystem, String prefix, ExecHandler execHandler) {
		super(operatingSystem, prefix, execHandler)
	}

	@Override
	String getCli() {
		operatingSystem.getScriptName(bin('cli_unity'))
	}
}
