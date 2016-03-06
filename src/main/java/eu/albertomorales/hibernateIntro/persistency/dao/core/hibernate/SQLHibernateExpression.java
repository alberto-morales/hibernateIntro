package eu.albertomorales.hibernateIntro.persistency.dao.core.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import eu.albertomorales.hibernateIntro.persistency.dao.core.Expression;

public class SQLHibernateExpression implements Expression, HibernateExpression {

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.hibernate.HibernateExpression#applyExpression(org.hibernate.Criteria)
	 */
	@Override
	public Criteria applyExpression(Criteria criteria) {

		criteria.add(Restrictions.sqlRestriction(SQL));

		return criteria;
	}

	public SQLHibernateExpression(String SQL) {
		this.SQL = SQL;
	}

	private String SQL;

}
