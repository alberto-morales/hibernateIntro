package eu.albertomorales.hibernateIntro.persistency.dao.core.hibernate;

import org.hibernate.Criteria;

public interface HibernateExpression {

	/**
	 * Put inside the criteria everything it is necessary to evaluate the expression
	 *
	 * @param criteria
	 *
	 * @return criteria
	 */
	public Criteria applyExpression(Criteria criteria);

}
