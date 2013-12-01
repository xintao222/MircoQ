/**
 * 
 */
package com.unic.micoq.services;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;

/**
 * @author javafx
 * 
 */
public class MessageService {

	private final XMPPConnection connection;
	private final ChatManager chatmanager;

	public MessageService(XMPPConnection connection) {
		super();
		this.connection = connection;
		this.chatmanager = connection.getChatManager();
	}

	public Chat createChat(String address, MessageListener messageListener) {
		return chatmanager.createChat(
				address + "@" + connection.getServiceName(), messageListener);
	}

	public void shutdown() {
		this.connection.disconnect();
	}

}
