package eu.albertomorales.hibernateIntro.persistency.dao.core.hibernate;

import java.io.Serializable;
import java.util.MissingResourceException;


public class HibernateGenericDAOExtended<T, ID extends Serializable> extends HibernateGenericDAO<T,ID> {

	public HibernateGenericDAOExtended() {
		super(null);
	}

	/**
	 * Method that assigns the persistent class for the DAO
	 * @param persistentClass
	 */
	@SuppressWarnings("unchecked")
	public void setPersistentClass(String persistentClass){
		String persistentClassName =  persistentClass.trim();
		try {
			super.setPersistentClass((Class<? extends T>)Class.forName(persistentClassName));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new MissingResourceException(null,null,null);
		}
	}

}
