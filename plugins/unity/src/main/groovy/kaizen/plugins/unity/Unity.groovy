package kaizen.plugins.unity

import kaizen.commons.Paths
import kaizen.plugins.mono.DefaultMonoProvider
import kaizen.plugins.mono.MonoPrefixProvider
import org.gradle.api.Project
import org.gradle.internal.os.OperatingSystem
import kaizen.plugins.mono.MonoProvider
import kaizen.plugins.mono.ExecHandler
import kaizen.plugins.mono.Mono
import kaizen.plugins.mono.frameworks.LegacyMono

class Unity implements MonoProvider, MonoPrefixProvider {

	static Unity forProject(Project project) {
		project.extensions.findByType(Unity)
	}

	def location

	final UnityLocator locator

	final OperatingSystem operatingSystem

	final ExecHandler execHandler

	private final DefaultMonoProvider monoProvider

	Unity(UnityLocator locator, OperatingSystem operatingSystem, ExecHandler execHandler) {
		this.locator = locator
		this.operatingSystem = operatingSystem
		this.execHandler = execHandler
		this.monoProvider = new DefaultMonoProvider(this, operatingSystem, execHandler)
	}

	def getExecutable() {
		Paths.combine getLocation(), relativeExecutablePath()
	}

	String relativeExecutablePath() {
		if (operatingSystem.windows)
			return 'Unity.exe'
		if (operatingSystem.macOsX)
			return 'Contents/MacOS/Unity'
		return 'Unity'
	}

	String getLocation() {
		location ?: locator.unityLocation
	}

	/**
	 *
	 * @param frameworkVersion one of 'v3.5', 'v4.0', 'unity'
	 * @return
	 */
	@Override
	Mono runtimeForFrameworkVersion(String frameworkVersion) {
		if (frameworkVersion == 'unity')
			return monoUnity
		return monoProvider.runtimeForFrameworkVersion(frameworkVersion)
	}

	String getMonoPrefix() {
		getFrameworkPath('MonoBleedingEdge')
	}

	Mono getMonoUnity() {
		new LegacyMono(operatingSystem, getFrameworkPath('Mono'), execHandler)
	}

	String getFrameworkPath(String frameworkName) {
		def frameworksPath = operatingSystem.macOsX ? 'Contents/Frameworks' : 'Data'
		Paths.combine getLocation(), frameworksPath, frameworkName
	}
}