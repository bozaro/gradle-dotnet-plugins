package kaizen.plugins.mono

import kaizen.plugins.clr.Clr

public interface Mono extends Clr {

	/**
	 * @return path of the mono command line interface
	 */
	String getCli()

	String lib(String profile, String fileName)
}