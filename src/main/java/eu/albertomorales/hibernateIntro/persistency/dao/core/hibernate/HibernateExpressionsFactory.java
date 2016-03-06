package eu.albertomorales.hibernateIntro.persistency.dao.core.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import eu.albertomorales.hibernateIntro.persistency.dao.core.Expression;
import eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory;

public class HibernateExpressionsFactory implements ExpressionsFactory {

	private String STRING_TRUE_VALUE = "S";
	private String STRING_FALSE_VALUE = "N";

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#isNull(java.lang.String)
	 */
	public Expression isNull(String propertyName) {
		SimpleHibernateExpression expresion = new SimpleHibernateExpression();
		expresion.setCriterion(Restrictions.isNull(propertyName));
		return expresion;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#sql(java.lang.String)
	 */
	public Expression sql(String expression) {
		if (StringUtils.isEmpty(expression)) return null;

		return new SQLHibernateExpression(expression);
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#equalBooleanIgnoresNullSN(java.lang.String, java.lang.Boolean)
	 */
	public Expression equalBooleanIgnoresNullSN(String propertyName, Boolean value) {
		Expression result = null;
		if (value != null) {
			if (Boolean.TRUE.equals(value)) {
				result = equalsTo(propertyName, STRING_TRUE_VALUE);
			}
			if (Boolean.FALSE.equals(value)) {
				result = equalsTo(propertyName, STRING_FALSE_VALUE);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#isDifferent(java.lang.String, java.lang.Object)
	 */
	public Expression isDifferent(String propertyName, Object value) {
		SimpleHibernateExpression expresion = new SimpleHibernateExpression();
		expresion.setCriterion(Restrictions.ne(propertyName, value));
		return expresion;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#equalsTo(java.lang.String, java.lang.Object)
	 */
	public Expression equalsTo(String propertyName, Object value){
		SimpleHibernateExpression expresion = new SimpleHibernateExpression();
		expresion.setCriterion(Restrictions.eq(propertyName, value));

		return expresion;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#isEmpty(java.lang.String)
	 */
	public Expression isEmpty(String propertyName){
		SimpleHibernateExpression expresion = new SimpleHibernateExpression();
		expresion.setCriterion(Restrictions.isNull(propertyName));
		return expresion;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#containedIn(java.lang.String, java.lang.Object[])
	 */
	public Expression containedIn(String propertyName, Object[] values) {
		if (values==null){
			return null;
		}
		List<Object> filteredValues = new ArrayList<Object>();
		for (Object loopValue : values) {
			if (loopValue!=null){
				filteredValues.add(loopValue);
			}
		}
		return containedIn(propertyName, filteredValues);
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#containedIn(java.lang.String, java.util.List)
	 */
	public Expression containedIn(String propertyName, List<?> values) {
		if (values==null || values.size()==0){
			return null;
		}
		SimpleHibernateExpression expresion = new SimpleHibernateExpression();
		expresion.setCriterion(Restrictions.in(propertyName, values));
		return expresion;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#nested(java.lang.String, eu.albertomorales.hibernateIntro.persistency.dao.core.Expression[])
	 */
	public Expression nested(String propertyName, Expression... arrayOfExpressions) {
		List<Expression> listOfExpressions = Arrays.asList(arrayOfExpressions);
		return nested(propertyName, listOfExpressions);
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#nested(java.lang.String, java.util.List)
	 */
	public Expression nested(String propertyName, List<Expression> listOfExpressions) {
		CollectionHibernateExpression rootExpression = new CollectionHibernateExpression();
		List<HibernateExpression> listOfHibernateExpressions = new ArrayList<HibernateExpression>();
		for (Expression expressions : listOfExpressions) {
			if (expressions != null) {
				listOfHibernateExpressions.add((HibernateExpression) expressions);
			}
		}

		if (listOfHibernateExpressions.size()==0) return null;

		rootExpression.setNestedExpressions(listOfHibernateExpressions);
		rootExpression.setPropertyName(propertyName);
		return rootExpression;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#nested(java.lang.String, java.lang.String, eu.albertomorales.hibernateIntro.persistency.dao.core.Expression[])
	 */
	public Expression nested(String propertyName, String joinExpression, Expression...arrayOfExpressions ) {
		CollectionHibernateExpression rootExpression = new CollectionHibernateExpression();
		List<HibernateExpression> listOfExpressions = new ArrayList<HibernateExpression>();
		for (Expression expression : arrayOfExpressions) {
			if (expression != null) {
				listOfExpressions.add((HibernateExpression) expression);
			}
		}

		if (listOfExpressions.size()==0) return null;

		rootExpression.setNestedExpressions(listOfExpressions);
		rootExpression.setJoinExpressions(joinExpression);
		rootExpression.setPropertyName(propertyName);
		return rootExpression;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#equalIgnoresNull(java.lang.String, java.lang.Object)
	 */
	public Expression equalIgnoresNull(String propertyName, Object value) {
		if (value == null) return null;
		return this.equalsTo(propertyName, value);
	}


	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#greaterEqualIgnoresNull(java.lang.String, java.lang.Object)
	 */
	public Expression greaterEqualIgnoresNull( String propertyName, Object value ){
		if (value == null ) return null;
		SimpleHibernateExpression expresion = new SimpleHibernateExpression();
		expresion.setCriterion(Restrictions.ge(propertyName, value));
		return expresion;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#lowerEqualIgnoresNull(java.lang.String, java.lang.Object)
	 */
	public Expression lowerEqualIgnoresNull( String propertyName, Object value ){
		if (value == null ) return null;
		SimpleHibernateExpression expresion = new SimpleHibernateExpression();
		expresion.setCriterion(Restrictions.le( propertyName, value));
		return expresion;

	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#lowerIgnoresNull(java.lang.String, java.lang.Object)
	 */
	public Expression lowerIgnoresNull(String propertyName, Object value) {
		if (value == null ) return null;
		SimpleHibernateExpression expresion = new SimpleHibernateExpression();
		expresion.setCriterion(Restrictions.lt(propertyName, value));
		return expresion;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#greaterIgnoresNull(java.lang.String, java.lang.Object)
	 */
	public Expression greaterIgnoresNull(String propertyName, Object value) {
		if (value == null ) return null;
		SimpleHibernateExpression expresion = new SimpleHibernateExpression();
		expresion.setCriterion(Restrictions.gt(propertyName, value));
		return expresion;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#orderBy(java.lang.String)
	 */
	public Expression orderBy(String propertyName) {
		SortHibernateExpression expresion = new SortHibernateExpression();
		expresion.setPropertyName(propertyName);
		return expresion;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#orderBy(java.lang.String, boolean)
	 */
	public Expression orderBy(String propertyName, boolean descending) {
		SortHibernateExpression expresion = new SortHibernateExpression();
		expresion.setPropertyName(propertyName);
		expresion.setDescending(descending);
		return expresion;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#containsString(java.lang.String, java.lang.String)
	 */
	public Expression containsString(String propertyName, String value) {
		if (value == null ) return null;
		SimpleHibernateExpression expresion = new SimpleHibernateExpression();
		expresion.setCriterion( Restrictions.like(propertyName, "%"+value+"%"));
		return expresion;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#startsWith(java.lang.String, java.lang.String)
	 */
	public Expression startsWith(String propertyName, String value) {
		if (value == null ) return null;
		SimpleHibernateExpression expresion = new SimpleHibernateExpression();
		expresion.setCriterion(Restrictions.like(propertyName, value+"%"));
		return expresion;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory#or(eu.albertomorales.hibernateIntro.persistency.dao.core.Expression[])
	 */
	public Expression or(Expression... arrayOfExpressions) {
		Disjunction dis = Restrictions.disjunction();
		for (Expression expresion : arrayOfExpressions) {
			dis.add(((SimpleHibernateExpression)expresion).getCriterion());
		}
		SimpleHibernateExpression result = new SimpleHibernateExpression();
		result.setCriterion(dis);
		return result;
	}

}
