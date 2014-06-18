package kaizen.plugins.mono.compilers

import kaizen.plugins.clr.ClrCompileSpec
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class AbstractCompilerCommandLineBuilder implements ClrCompileSpec {
	final Logger logger = LoggerFactory.getLogger(AbstractCompilerCommandLineBuilder.class)

	final List<Object> arguments = []

	String targetFramework

	boolean wrapArguments = true


	@Override
	void targetFrameworkVersion(String targetFramework) {
		this.targetFramework = targetFramework
	}

	@Override
	void outputAssembly(File outputAssembly) {
		args("-target:${targetFor(outputAssembly)}")
		args("-out:$outputAssembly.canonicalPath")
	}

	def targetFor(File file) {
		file.name.endsWith('.exe') ? 'exe' : 'library'
	}

	@Override
	void sourceFiles(Iterable<File> sourceFiles) {
		args(sourceFiles*.canonicalPath)
	}

	def sdkVersionForFrameworkVersion(String frameworkVersion) {
		if (frameworkVersion == 'v3.5')
			return 2
		if (frameworkVersion == 'v4.0')
			return 4
		throw new IllegalArgumentException("Unsupported framework version '$frameworkVersion'")
	}

	@Override
	void references(Iterable<String> references) {
		args(references.collect { "-r:$it" })
	}

	@Override
	void defines(Iterable<String> defines) {
		args(defines.collect { "-define:$it" })
	}

	@Override
	void keyFile(File keyFile) {
		args("-keyfile:$keyFile")
	}

	@Override
	void compilerOptions(Iterable<String> compilerOptions) {
		arguments.addAll(compilerOptions)
	}

	void args(Object... args) {
		arguments.addAll(args)
	}

	void args(Iterable args) {
		arguments.addAll(args)
	}

	@Override
	void wrapArguments(boolean wrapArguments) {
		this.wrapArguments = wrapArguments;
	}

	List<Object> getWrappedArguments() {
		if (wrapArguments) {
			File tmp = File.createTempFile("tmp", ".arg")
			tmp.deleteOnExit();
			String args = arguments.join("\n")
			logger.debug("Creating arguments file {} with content:\n{}", tmp.absolutePath, args)
			tmp.write(args)
			return ["@" + tmp.absolutePath];
		}
		return arguments
	}
}
