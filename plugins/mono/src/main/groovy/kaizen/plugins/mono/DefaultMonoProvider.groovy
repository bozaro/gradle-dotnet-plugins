package kaizen.plugins.mono

import kaizen.plugins.mono.frameworks.ModernMono
import org.gradle.internal.os.OperatingSystem

class DefaultMonoProvider implements MonoProvider {

	final OperatingSystem operatingSystem

	final ExecHandler execHandler

	final MonoPrefixProvider monoPrefixProvider

	DefaultMonoProvider(MonoPrefixProvider monoPrefixProvider, OperatingSystem operatingSystem, ExecHandler execHandler) {
		this.monoPrefixProvider = monoPrefixProvider
		this.operatingSystem = operatingSystem
		this.execHandler = execHandler
	}

	@Override
	Mono runtimeForFrameworkVersion(String frameworkVersion) {
		if (frameworkVersion == 'v3.5')
			return mono('v2.0.50727')
		if (frameworkVersion == 'v4.0')
			return mono('v4.0')
		throw new IllegalArgumentException("$frameworkVersion not supported")
	}

	Mono mono(String runtimeVersion) {
		new ModernMono(operatingSystem, monoPrefix, execHandler, runtimeVersion)
	}

	String getMonoPrefix() {
		monoPrefixProvider.monoPrefix
	}
}
