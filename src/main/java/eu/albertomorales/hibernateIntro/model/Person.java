package eu.albertomorales.hibernateIntro.model;

public interface Person {

	/**
	 * @return Person name
	 */
	public abstract String getName();

	/**
	 * @return Person ID
	 */
	public abstract Integer getId();

	/**
	 * @param Person name
	 */
	public abstract void setName(String name);


}
