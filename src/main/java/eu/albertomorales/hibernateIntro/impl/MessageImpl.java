package eu.albertomorales.hibernateIntro.impl;

import eu.albertomorales.hibernateIntro.Message;

public class MessageImpl implements Message {

	/* (non-Javadoc)
	 * @see eu.albertomorales.hibernateIntro.impl.Messagee#getText()
	 */
	@Override
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private String text;

}
