package kaizen.plugins.monolipse

import kaizen.plugins.assembly.model.Assembly
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.plugins.ide.eclipse.model.EclipseModel

class MonolipsePlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		project.plugins.apply 'eclipse'

		def eclipse = project.extensions.findByType(EclipseModel)
		eclipse.project.natures('monolipse.core.booNature')
		eclipse.project.buildCommand('monolipse.core.booBuilder')

		def assemblies = project.subprojects.findAll { Assembly.forProject(it) != null }
		def tasks = assemblies.collect { it.tasks.create('monolipse', MonolipseTask) }

		project.tasks.eclipse.dependsOn tasks.toArray()
	}
}


