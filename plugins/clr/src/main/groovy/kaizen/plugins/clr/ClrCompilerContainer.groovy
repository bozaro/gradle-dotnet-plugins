package kaizen.plugins.clr

public interface ClrCompilerContainer extends ClrContainer<ClrCompiler> {
	ClrCompiler forLanguage(String language)
}