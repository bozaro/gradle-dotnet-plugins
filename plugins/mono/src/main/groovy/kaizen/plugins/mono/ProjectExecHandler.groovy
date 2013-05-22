package kaizen.plugins.mono

import org.gradle.api.Project
import org.gradle.process.ExecResult

class ProjectExecHandler implements ExecHandler {

	final Project project

	ProjectExecHandler(Project project) {
		this.project = project
	}

	@Override
	ExecResult exec(Closure execSpecConfig) {
		project.exec execSpecConfig
	}
}
