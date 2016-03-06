package eu.albertomorales.hibernateIntro.persistency.dao.core.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.PropertyProjection;
import org.springframework.transaction.annotation.Transactional;

import eu.albertomorales.hibernateIntro.persistency.dao.core.GenericDAO;
import eu.albertomorales.hibernateIntro.persistency.dao.core.Expression;
import eu.albertomorales.hibernateIntro.util.CastingTools;

/**
 * Implementa las operaciones de acceso a datos genéricas para ABM's usando
 * la API de Hibernate.
 * <p>
 * Implements the generic CRUD data access operations using Hibernate APIs.
 * <p>
 * To write a DAO, subclass and parameterize this class with your persistent class.
 * Of course, assuming that you have a traditional 1:1 appraoch for Entity:DAO design.
 * <p>
 * You have to inject the <tt>Class</tt> object of the persistent class and a current
 * Hibernate <tt>Session</tt> to construct a DAO (ñapeado).
 *
 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.GenericDAO
 *
 * @author <a href="mailto:mail@albertomorales.eu">Alberto Morales</a>
 */
@Transactional
public class HibernateGenericDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {

    protected HibernateGenericDAO(Class<? extends T> persistentClass) {
        this.persistentClass = (Class<? extends T>)persistentClass;
    }

    /* (non-Javadoc)
     * @see eu.albertomorales.hibernateIntro.persistency.dao.core.GenericDAO#findById(java.io.Serializable)
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public T findById(ID id) {
        if (id == null)
            return null;
        T entity = (T) getSession().get(persistentClass, id);
        return entity;
    }

    /* (non-Javadoc)
     * @see eu.albertomorales.hibernateIntro.persistency.dao.core.GenericDAO#getAll()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> getAll() {
    	Criteria criteria = createCriteria();
        return criteria.list();
    }

	private Criteria createCriteria() {
		Criteria criteria = getSession().createCriteria(persistentClass);
		return criteria;
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.GenericDAO#findByExpression(eu.albertomorales.hibernateIntro.persistency.dao.core.Expression[])
	 */
	@SuppressWarnings("unchecked")
    @Override
    public List<T> findByExpression(Expression... arrayOfExpressions) {
		Criteria criteria1 = createCriteria();
		for (Expression expression : arrayOfExpressions) {
			if (expression != null) {
				HibernateExpression hibernateExpression = (HibernateExpression) expression;
				criteria1 = hibernateExpression.applyExpression(criteria1);
			}
		}
		Criteria criteria = criteria1;
    	return criteria.list();
    }

    /* (non-Javadoc)
     * @see eu.albertomorales.hibernateIntro.persistency.dao.core.GenericDAO#getIDs(java.util.List)
     */
    @Override
	public List<ID> getIDs(List<Expression> expressionsList) {
		Expression[] arrayOfExpressions = expressionsList.toArray(new Expression[expressionsList.size()]);
		return getIDs(arrayOfExpressions);
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.GenericDAO#getIDs(eu.albertomorales.hibernateIntro.persistency.dao.core.Expression[])
	 */
    @Override
	public List<ID> getIDs(Expression... arrayOfExpressions) {

		String propertyName = "id";

		return CastingTools.cast(getPropertyValue(propertyName, arrayOfExpressions));
	}

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.GenericDAO#expressionsList(java.lang.String, eu.albertomorales.hibernateIntro.persistency.dao.core.Expression[])
	 */
    @Override
	public List<Object> getPropertyValue(String propertyName, Expression... arrayOfExpressions) {
		Projection projection = Projections.id();
		if (!"id".equals(propertyName)){
			projection = Projections.property(propertyName);
		}
		Criteria criteria = createCriteria();

		boolean conOrdenacionDiferenteAlRecuperado = false;

		for (Expression expresion : arrayOfExpressions) {
			if (expresion != null) {
				HibernateExpression expresionHibernate = (HibernateExpression) expresion;

				if (expresionHibernate instanceof  SortHibernateExpression){
					SortHibernateExpression expresionOrdenar = (SortHibernateExpression) expresionHibernate;

					String propiedad = expresionOrdenar.getPropertyName();
					if (!propiedad.equals(propertyName)){
						conOrdenacionDiferenteAlRecuperado = true;
						ProjectionList pl;
						if (projection instanceof ProjectionList){
							pl = (ProjectionList) projection;
						}else{
							pl = Projections.projectionList();
							pl.add(projection);
							projection = pl;
						}

						PropertyProjection propertyOrdenacion = Projections.property(propiedad);
						Projection p = Projections.alias(propertyOrdenacion,"_ord"+propiedad);
						pl.add(p);
						if (expresionOrdenar.isDescending()){
							criteria.addOrder(Order.desc("_ord"+propiedad));
						}else{
							criteria.addOrder(Order.asc("_ord"+propiedad));
						}
					}else{
						criteria = expresionHibernate.applyExpression(criteria);
					}
				}else{
					criteria = expresionHibernate.applyExpression(criteria);
				}
			}
		}

		criteria.setProjection(projection);
		if (conOrdenacionDiferenteAlRecuperado){
			// tengo que quedarme so con la propiedad que necesito
			List<Object[]> listaResultados = CastingTools.cast(criteria.list());
			List<Object> listaIds = new ArrayList<Object>();
			for (Object[] linea : listaResultados) {
				Object lineaID = (Object) linea[0];     // la propiedad que busco siempre tiene que estar en la primera posicion de los resultados
				listaIds.add(lineaID);
			}
			return listaIds;
		}else{
			return CastingTools.cast(criteria.list());
		}
	}


	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.persistency.dao.core.GenericDAO#findByExpression(java.util.List)
	 */
    @Override
	public List<T> findByExpression(List<Expression> expressionsList) {
		Expression[] expresiones = expressionsList.toArray(new Expression[expressionsList.size()]);
		return findByExpression(expresiones);
	}

    /* (non-Javadoc)
     * @see eu.albertomorales.hibernateIntro.persistency.dao.core.GenericDAO#makePersistent(java.lang.Object)
     */
    @Override
    public T makePersistent(T entity) {
    	getSession().saveOrUpdate(entity);
        return entity;
    }


    /* (non-Javadoc)
     * @see eu.albertomorales.hibernateIntro.persistency.dao.core.GenericDAO#makeTransient(java.lang.Object)
     */
    @Override
    public void makeTransient(T entity){
    	getSession().delete(entity);
    	getSession().flush(); //
    }

    /**
     * @return persistent class
     */
    public Class<? extends T> getPersistentClass() {
        return persistentClass;
    }

    /**
     * @param persistent class
     */
    public void setPersistentClass(Class<? extends T> persistentClass) {
        this.persistentClass = persistentClass;
    }


    /**
     * @return
     */
    protected Session getSession() {

    	org.hibernate.classic.Session currentSession = sessionFactory.getCurrentSession();

		return currentSession;
    }

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

    private Class<? extends T> persistentClass;

    private SessionFactory sessionFactory;

}

