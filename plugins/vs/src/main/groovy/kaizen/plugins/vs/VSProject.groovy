package kaizen.plugins.vs

import kaizen.plugins.assembly.model.Assembly
import kaizen.plugins.assembly.model.AssemblyReference
import kaizen.plugins.clr.ClrLanguageNames
import kaizen.plugins.conventions.ProjectDependenciesClassifier
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency

class VSProject {
	final Project project
	@Lazy
	String guid = GuidString.from(project.name)
	final ProjectDependenciesClassifier dependenciesClassifier
	List<String> defines = []
	String keyFile

	VSProject(Project project) {
		this.project = project
		this.dependenciesClassifier = new ProjectDependenciesClassifier(project, { ProjectDependency d -> isSupportProject(d.dependencyProject) })
	}

	def isSupportProject(Project dependencyProject) {
		VSExtension.forProject(dependencyProject)?.project.isSupportedLanguage
	}

	String getTargetFrameworkVersion() {
		assembly.targetFrameworkVersion
	}

	File getFile() {
		project.file("${project.name}.csproj")
	}

	String getOutputType() {
		def target = assembly.target
		target == 'winexe' ? 'WinExe' : target.capitalize()
	}

	String getDefineConstants() {
		defines.empty ? '' : ';' + defines.join(';')
	}

	String getAssemblyName() {
		assembly.name
	}

	Iterable<String> getSourceFiles() {
		assembly.sourceFiles.collect {
			vsPathFor(it)
		}
	}

	// always use \ on vs project files
	def vsPathFor(File file) {
		project.relativePath(file).replace('/', '\\')
	}

	Map<String, String> getEmbeddedResources() {
		assembly.embeddedResources.inject([:]) { result, entry ->
			result[vsPathFor(entry)] = assembly.getEmbeddedResourceName(entry)
			return result
		}
	}

	Iterable<VSProjectReference> getProjectReferences() {
		projectDependencies.collect { projectReferenceFor(it) }
	}

	Iterable<Dependency> getExternalDependencies() {
		dependenciesClassifier.externalDependencies
	}

	Iterable<AssemblyReference> getAssemblyReferences() {
		assembly.references
	}

	boolean getIsSupportedLanguage() {
		assembly?.language == ClrLanguageNames.CSHARP
	}

	Assembly getAssembly() {
		project.extensions.findByName('assembly')
	}

	private getProjectDependencies() {
		dependenciesClassifier.projectDependencies
	}

	VSProjectReference projectReferenceFor(ProjectDependency projectDependency) {
		new VSProjectReference(project, projectDependency)
	}
}
