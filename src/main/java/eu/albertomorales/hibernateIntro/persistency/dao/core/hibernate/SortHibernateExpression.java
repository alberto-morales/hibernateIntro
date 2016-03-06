package eu.albertomorales.hibernateIntro.persistency.dao.core.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import eu.albertomorales.hibernateIntro.persistency.dao.core.Expression;

public class SortHibernateExpression implements Expression, HibernateExpression {

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.hibernate.HibernateExpression#applyExpression(org.hibernate.Criteria)
	 */
	@Override
	public Criteria applyExpression(Criteria criteria) {

		if (descending){
			criteria.addOrder(Order.desc(propertyName));
		}
		else{
			criteria.addOrder(Order.asc(propertyName));
		}

		return criteria;
	}

	public String getPropertyName() {
		return propertyName;
	}
	public boolean isDescending() {
		return descending;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public void setDescending(boolean descending) {
		this.descending = descending;
	}

	private String propertyName;
	private boolean descending;
}
