package kaizen.plugins.nunit

import kaizen.plugins.assembly.AssemblyPlugin
import kaizen.plugins.assembly.tasks.AssemblyCompile
import kaizen.plugins.conventions.Configurations
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.util.ConfigureUtil

class NUnitAssemblyPlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		// NUnitAssembly implies assembly
		project.plugins.apply AssemblyPlugin

		// NUnitAssembly implies NUnit at the root
		def bundle = project.rootProject
		bundle.plugins.apply NUnitPlugin

		def masterTestTask = project.task('test') {
			description 'Runs all nunit tests.'
		}
		project.afterEvaluate {
			configureNUnitTasksOn project, masterTestTask
		}
	}

	void configureNUnitTasksOn(Project project, Task masterTestTask) {

		def rootProject = project.rootProject
		def nunit = NUnitExtension.forProject(rootProject)
		def nunitVersion = nunit.version

		project.logger.info "Configuring nunit $nunitVersion on $project"

		def configureTaskForConfig = { Configuration config ->
			def configLabel = Configurations.labelFor(config)
			def compileTask = project.tasks.withType(AssemblyCompile).findByName("compile${configLabel}")
			if (compileTask) {
				project.dependencies.add(config.name, "nunit:nunit.framework:${nunitVersion}")
				configure(project) {
					def outputAssembly = project.file(compileTask.outputAssembly)
					def testConfigTask = task("test$configLabel", type: NUnitTask, dependsOn: [compileTask, rootProject.tasks.updateNUnit]) {
						inputs.file outputAssembly
						outputs.file new File(outputAssembly.parentFile, 'TestResult.xml')
						targetFrameworkVersion compileTask.targetFrameworkVersion
					}
					masterTestTask.dependsOn(testConfigTask)
				}
			}
		}
		project.configurations.each configureTaskForConfig
		project.configurations.whenObjectAdded configureTaskForConfig
	}

	def configure(Project project, Closure closure) {
		ConfigureUtil.configure(closure, project)
	}
}


