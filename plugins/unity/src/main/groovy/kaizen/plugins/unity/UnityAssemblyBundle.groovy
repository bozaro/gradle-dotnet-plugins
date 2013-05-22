package kaizen.plugins.unity

import org.gradle.api.Project
import org.gradle.api.Plugin
import kaizen.plugins.LibClientPlugin
import kaizen.plugins.LocalRepositoryPlugin
import kaizen.plugins.assembly.AssemblyBundlePlugin

class UnityAssemblyBundle implements Plugin<Project> {
	@Override
	void apply(Project project) {

		// a unity assembly bundle uses unity clr and compilers
		project.plugins.apply UnityPlugin

		// it's a bundle
		project.plugins.apply AssemblyBundlePlugin

		// a bundle needs the ability to depend on external libs
		project.plugins.apply LibClientPlugin

		// a bundle needs a local repository to be published to
		project.plugins.apply LocalRepositoryPlugin
	}
}
