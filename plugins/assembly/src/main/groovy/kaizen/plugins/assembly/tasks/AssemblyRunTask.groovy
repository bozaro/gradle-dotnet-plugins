package kaizen.plugins.assembly.tasks

import kaizen.plugins.assembly.model.Assembly
import kaizen.plugins.clr.Clr
import kaizen.plugins.clr.ClrExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class AssemblyRunTask extends DefaultTask {

	def assembly

	def runAssemblyBuiltBy(AssemblyCompile compileTask) {
		this.assembly = { compileTask.outputAssembly }
		this.dependsOn compileTask
		this.onlyIf { assemblyModel.isExecutable() }
	}

	@TaskAction
	def run() {
		def task = this
		clr.exec {
			def assemblyFile = project.file(task.assembly)
			executable assemblyFile
			workingDir assemblyFile.parentFile
		}
	}

	Clr getClr() {
		def clr = ClrExtension.forProject(project)
		clr.runtimeForFrameworkVersion(assemblyModel.targetFrameworkVersion)
	}

	Assembly getAssemblyModel() {
		Assembly.forProject(project)
	}
}
