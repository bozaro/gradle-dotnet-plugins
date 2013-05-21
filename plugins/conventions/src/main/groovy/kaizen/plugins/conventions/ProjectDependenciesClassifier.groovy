package kaizen.plugins.conventions

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.DependencySet
import org.gradle.api.artifacts.ProjectDependency

class ProjectDependenciesClassifier {

	final Project project
	final Closure isSupportProjectDependency

	ProjectDependenciesClassifier(Project project, Closure isSupportProjectDependency) {
		this.project = project
		this.isSupportProjectDependency = isSupportProjectDependency
	}

	Iterable<Dependency> getExternalDependencies() {
		dependencies.findAll { isExternal(it) }
	}

	def isExternal(Dependency d) {
		!isProject(d)
	}

	Iterable<Dependency> getProjectDependencies() {
		dependencies.findAll { isProject(it) }
	}

	def isProject(Dependency d) {
		d instanceof ProjectDependency && isSupportProjectDependency(d)
	}

	DependencySet getDependencies() {
		project.configurations.getByName('default').allDependencies
	}
}
