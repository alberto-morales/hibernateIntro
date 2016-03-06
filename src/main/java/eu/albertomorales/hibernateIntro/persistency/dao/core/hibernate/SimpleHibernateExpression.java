package eu.albertomorales.hibernateIntro.persistency.dao.core.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

import eu.albertomorales.hibernateIntro.persistency.dao.core.Expression;

public class SimpleHibernateExpression implements Expression, HibernateExpression {

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.hibernate.HibernateExpression#applyExpression(org.hibernate.Criteria)
	 */
	@Override
	public Criteria applyExpression(Criteria criteria) {

		criteria.add(getCriterion());

		return criteria;
	}

	public Criterion getCriterion() {
		return criterion;
	}

	public void setCriterion(Criterion criterion) {
		this.criterion = criterion;
	}


	private Criterion criterion;

}
