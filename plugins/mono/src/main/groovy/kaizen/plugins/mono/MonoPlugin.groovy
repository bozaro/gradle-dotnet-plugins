package kaizen.plugins.mono

import kaizen.plugins.clr.ClrExtension
import kaizen.plugins.mono.compilers.Mcs
import org.gradle.api.Plugin
import org.gradle.api.Project
import kaizen.plugins.clr.ClrPlugin
import org.gradle.internal.os.OperatingSystem

class MonoPlugin implements Plugin<Project> {
	@Override
	void apply(Project project) {
		project.plugins.apply ClrPlugin

		def clr = ClrExtension.forProject(project)
		def monoProvider = new DefaultMonoProvider({ "/usr" } as MonoPrefixProvider, OperatingSystem.current(), new ProjectExecHandler(project))
		clr.providers.add monoProvider
		clr.compilers.add new Mcs(monoProvider)
	}
}
