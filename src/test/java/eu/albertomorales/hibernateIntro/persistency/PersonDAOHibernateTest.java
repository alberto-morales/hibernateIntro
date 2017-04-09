package eu.albertomorales.hibernateIntro.persistency;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.albertomorales.hibernateIntro.model.Person;
import eu.albertomorales.hibernateIntro.persistency.dao.core.GenericDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
public class PersonDAOHibernateTest {

	    @Autowired()
	    private GenericDAO<Person, Integer> personDAO;

	    @Transactional
	    @Rollback(true)
	    @Test
	    public void personDAO_getAll() {
	    	List<Person> resultado = personDAO.getAll();
	    	assertEquals("personDAO.getAll().size()", 9, resultado.size());
	    }

}
