package com.unic.micsay.actions;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class ChatAction {

	public ChatAction() {
	}

	private Chat chat = null;

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public void onPause() {

	}

	protected MessageListener listener = new MessageListener() {

		public void processMessage(org.jivesoftware.smack.Chat chat, Message msg) {

		}
	};

	public void onResume() {

	}

	public void send(String message) {
		try {
			chat.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	public void onClose() {

	}

}
