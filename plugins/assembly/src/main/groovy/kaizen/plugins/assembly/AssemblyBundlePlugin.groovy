package kaizen.plugins.assembly

import org.gradle.api.Plugin
import org.gradle.api.Project

class AssemblyBundlePlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {

		project.group = project.name

		// a bundle needs the deployment capabilities given by the base plugin
		project.plugins.apply 'base'

		// all projects in a bundle are considered assemblies by default
		project.subprojects.each { subProject ->
			subProject.plugins.apply AssemblyPlugin
		}
	}
}
