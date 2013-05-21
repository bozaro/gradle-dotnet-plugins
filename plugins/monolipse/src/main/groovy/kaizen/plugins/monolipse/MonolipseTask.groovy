package kaizen.plugins.monolipse

import kaizen.plugins.assembly.model.Assembly
import kaizen.plugins.conventions.ProjectDependenciesClassifier
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.internal.AbstractTask
import org.gradle.api.tasks.TaskAction

class MonolipseTask extends AbstractTask {
	@TaskAction
	def generateFiles() {
		def assembly = Assembly.forProject(project)
		def classifier = new ProjectDependenciesClassifier(project, { true })
		def file = project.file('.monolipse')
		def outputFolder = workspacePathFor(new File(project.buildDir, "Default"))
		file.withWriter { writer ->
			def xml = new groovy.xml.MarkupBuilder(writer)
			xml.settings {
				xml.language(assembly.sourceFileExtension)
				xml.outputType(assembly.target)
				xml.references {
					classifier.dependencies.each {
						def depName = it.name
						if (classifier.isProject(it)) {
							def referencedProject = (it as ProjectDependency).dependencyProject
							xml.'monolipse.core.internal.AssemblySourceReference-Remembrance' {
								xml.path(workspacePathFor(referencedProject.projectDir))
							}
						} else {
							if (depName.startsWith("Boo.Lang"))
								xml.'monolipse.core.internal.BooAssemblyReference' {
									xml.assemblyName(depName)
								}
							else
								xml.'monolipse.core.internal.LocalAssemblyReference-Remembrance' {
									xml.path("$outputFolder/${depName}.dll")
								}
						}
					}
				}
				xml.outputFolder(outputFolder)
			}
		}
	}

	def workspacePathFor(File f) {
		def rootDir = project.rootProject.projectDir
		def absoluteRoot = rootDir.absolutePath
		def absolutePath = f.absolutePath
		"/$rootDir.name/" + absolutePath.substring(absoluteRoot.length() + 1).replace('\\', '/')
	}
}
