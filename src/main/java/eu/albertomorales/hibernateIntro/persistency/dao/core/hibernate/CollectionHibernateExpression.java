package eu.albertomorales.hibernateIntro.persistency.dao.core.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import eu.albertomorales.hibernateIntro.persistency.dao.core.hibernate.HibernateExpression;
import eu.albertomorales.hibernateIntro.persistency.dao.core.Expression;

public class CollectionHibernateExpression implements Expression, HibernateExpression {

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.hibernate.HibernateExpression#applyExpression(org.hibernate.Criteria)
	 */
	@Override
	public Criteria applyExpression(Criteria criteria) {
		Criteria subcritria;
		if (joinExpressions!=null){
			subcritria = criteria.createCriteria(propertyName,"alias",CriteriaSpecification.INNER_JOIN,  Restrictions.sqlRestriction(joinExpressions) );
		}else{
				subcritria = criteria.createCriteria(propertyName);
		}

		for (HibernateExpression expresion : nestedExpressions) {
			expresion.applyExpression(subcritria);
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria;
	}

	public void setNestedExpressions(List<HibernateExpression> nestedExpressions) {
		this.nestedExpressions = nestedExpressions;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public void setJoinExpressions(String expresionesJoin) {
		this.joinExpressions = expresionesJoin;
	}

	private List<HibernateExpression> nestedExpressions;
	private String joinExpressions;
	private String propertyName;

}
