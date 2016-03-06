package eu.albertomorales.hibernateIntro.persistency.dao.core;

import java.util.List;

public interface ExpressionsFactory {

	/**
	 *  Alows us to create a criterion of a given sql expression
	 *
	 *  The source table must be refered as "{alias}"
	 *
	 *  Example:
	 *  	{alias}.code in (select code form atalog where type = 'L')
	 */
	Expression sql(String expression);

	/**
	 * Creates a NON EQUAL expression
	 *
	 * @param propertyName
	 * @param value
	 *
	 * @return
	 */
	Expression isDifferent(String propertyName, Object value);

	/**
	 * Creates an like %stringValue% expression
	 *
	 * @param propertyName
	 * @param value
	 * @return
	 */
	Expression containsString(String propertyName, String value);

	/**
	 * Creates an like stringValue% expression
	 *
	 * @param propiedad
	 * @param valor
	 * @return
	 */
	Expression startsWith(String propertyName, String value);


	/**
	 * Creates an EQUAL expression
	 * @param propertyName
	 * @param value
	 *
	 * @return
	 */
	Expression equalsTo(String propertyName, Object value);


	/**
	 * Creates an IS NULL expression
	 *
	 * @param propertyName
	 *
	 * @return
	 */
	Expression isEmpty(String propertyName);

	/**
	 * Creates an IN (list of values) expression
	 *
	 * @param propiedad
	 * @param valores
	 *
	 * @return
	 */
	Expression containedIn(String propertyName, Object[] arrayOfValues);
	Expression containedIn(String propertyName, List<?> listOfValues);

	/**
	 * Creates a nested expression
	 *
	 * @param propertyName
	 * @param expression
	 * @return
	 */
	Expression nested(String propertyName, Expression... expression);

	/**
	 * @param propertyName
	 * @param expression
	 * @return
	 */
	Expression nested(String propertyName, List<Expression> expression);

	/**
	 * Creates an OR expression with the given ones
	 *
	 * @param array of expressions
	 *
	 * @return
	 */
	Expression or(Expression... expression);

	/**
	 *  Allows us to created a nested criterion, adding a join expression
	 *
	 *  The join expression allows to relate extra properties from the source table with the related table.
	 *  We must refer the source table as "this_" and the nested one as "{alias}"
	 *
	 *  Example:
	 *  	"{alias}.initialDate <= this_.someDate and {alias}.finalDate >= this_.someDate"
	 *
	 */
	public Expression nested(String propiedad, String joinExpresion, Expression...expresionAnidada );

	/**
	 * Creates an expression of equality
	 *
	 * @param propertyName
	 * @param value
	 *
	 * @return Expression created; If the value is null, the expression will be null too
	 */
	Expression equalIgnoresNull(String propertyName, Object value);

	/**
	 * Creates an expression of equality
	 *
	 * @param propertyName
	 * @param booleanValue trye / false / null
	 *
	 * @return Expression created, using S/N; If the value is null, the expression will be null too
	 */
	Expression equalBooleanIgnoresNullSN(String propertyName, Boolean booleanValue);

	Expression greaterIgnoresNull(String propertyName, Object value);

	Expression greaterEqualIgnoresNull(String propertyName, Object value);

	Expression lowerEqualIgnoresNull(String propertyName, Object value);

	Expression lowerIgnoresNull(String propertyName, Object value);

	Expression orderBy(String propertyName);

	Expression orderBy(String propertyName,boolean descending);

	/**
	 * Creates an expression indicating that the given property must have null value
	 * @param propertyName
	 * @return
	 */
	Expression isNull(String propertyName);

}
