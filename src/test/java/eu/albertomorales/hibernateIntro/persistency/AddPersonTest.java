package eu.albertomorales.hibernateIntro.persistency;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.albertomorales.hibernateIntro.model.Person;
import eu.albertomorales.hibernateIntro.model.impl.PersonImpl;
import eu.albertomorales.hibernateIntro.persistency.dao.core.ExpressionsFactory;
import eu.albertomorales.hibernateIntro.persistency.dao.core.GenericDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
public class AddPersonTest {

	    @Autowired()
	    private GenericDAO<Person, Integer> personDAO;

	    @Autowired()
	    private ExpressionsFactory expressionFactory;

	    @Transactional
	    @Rollback(true)
	    @Test
	    @Before
	    public void prepare() {
	    	List<Person> people = personDAO.getAll();
	    	for (Person person : people) {
	    		personDAO.makeTransient(person);
	    	}
	    }

	    @Transactional
	    @Rollback(true)
	    @Test
	    public void addPerson() {
	    	String testPersonName = "NewPerson";
			Person newPerson = new PersonImpl();
			newPerson.setName("NewPerson");
			personDAO.makePersistent(newPerson);
			List<Person> result = personDAO.findByExpression(expressionFactory.equalsTo("name", testPersonName));
			assertEquals("queryResultAfterInsert", 1, result.size());
			Person recentlyAddedPerson = result.get(0);
			assertEquals("recentlyAddedPersonName", testPersonName, recentlyAddedPerson.getName());
	    }

}
