package eu.albertomorales.hibernateIntro.persistency;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.albertomorales.hibernateIntro.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
public class ExampleTest {

    @Test
    public void message_getText() {
    	String text = message.getText();
    	assertEquals("message.getText", "Spring is fun.", text);
    }

    private Message message;

	public Message getMessage() {
		return message;
	}

	@Resource(name="message")
	public void setMessage(Message message) {
		this.message = message;
	}

}
