package kaizen.plugins.mono

import org.gradle.process.ExecResult
import org.gradle.process.ExecSpec

public interface ExecHandler {
	ExecResult exec(Closure execSpecConfig)
}