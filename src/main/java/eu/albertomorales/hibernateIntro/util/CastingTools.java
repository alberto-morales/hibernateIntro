package eu.albertomorales.hibernateIntro.util;

/**
 * Utility class for casting.
 *
 */
public class CastingTools {

	/**
	 * Cast a variable to everything
	 *
	 */
	@SuppressWarnings("unchecked") // get rid of the undesired warning
	public static <T> T cast(Object list){

		return (T) list;

	}

}
