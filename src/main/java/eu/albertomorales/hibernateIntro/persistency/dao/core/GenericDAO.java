package eu.albertomorales.hibernateIntro.persistency.dao.core;

import java.io.Serializable;
import java.util.List;

/**
 * Interface compartida por todos los business DAO's .
 * <p>
 * An interface shared by all business data access objects.
 * <p>
 * All CRUD (create, read, update, delete) basic data access operations are
 * isolated in this interface and shared accross all DAO implementations.
 * The current design is for a state-management oriented persistence layer
 * (for example, there is no UDPATE statement function) that provides
 * automatic transactional dirty checking of business objects in persistent
 * state.
 *
 * @author <a href="mailto:mail@albertomorales.eu">Alberto Morales</a>
 */
public interface GenericDAO<T, ID extends Serializable> {

    /**
     * Find an entity by id.
     * @param  id entity ID.
     * @return Retrieved entity.
     * @throws DAOExcepcion if entity hasn't been found.
     */
    T findById(ID id);

    /**
     * Get all the (same type) entities.
     * @return list with the retrieved entities.
     */
    List<T> getAll();

    /**
     * Get all the (same type) entities, that match the given conditions
     * @param arrayOfExpressions conditions
     * @return list with the retrieved entities.
     */
     List<T> findByExpression(Expression... arrayOfExpressions);

     /**
      * Get all the (same type) entities, that match the given conditions
      * @param expressionsList conditions
      * @return list with the retrieved entities.
      */
     List<T> findByExpression(List<Expression> expressionsList);

     /**
      * Get all the (same type) entities ID's, that match the given conditions
      *
     * @param arrayOfExpressions conditions
     * @return list with the retrieved entities ID's.
     */
    List<ID> getIDs(Expression... arrayOfExpressions);

    /**
     * Get all the (same type) entities ID's, that match the given conditions
     *
     * @param expressionsList conditions
     * @return list with the retrieved entities ID's.
     */
    List<ID> getIDs(List<Expression> expressionsList);

    /**
     * Get all the (same type) entities given property value, for those entities that match the given conditions
     *
     * @param propertyName entity property to be evaluated
     * @param arrayOfExpressions conditions
     * @return list with the retrieved entities given property value
     */
    List<Object> getPropertyValue(String propertyName, Expression... arrayOfExpressions);

    /**
     * Method that persists (save to the DB) an entity in a transitory state
     * @param entity to be persisted
     * @return the same entity in a persistent state
     */
    T makePersistent(T entity);

    /**
     * Method that make transient (delete from the DB) a persistent entity
     * @param entity to be deleted
     */
    void makeTransient(T entity);

}
