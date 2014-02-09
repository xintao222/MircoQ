package com.unic.micsay.actions;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.os.RemoteException;

public class ChatAction {

	public ChatAction(com.unic.micsay.services.MessageListener msgListener) {
		this.msgListener = msgListener;
	}

	private com.unic.micsay.services.MessageListener msgListener = null;
	private Chat chat = null;
	private boolean chating = true;

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public void onPause() {
		chating = false;
	}

	protected MessageListener listener = new MessageListener() {

		public void processMessage(org.jivesoftware.smack.Chat chat, Message msg) {
			if (chating) {
				try {
					msgListener.processMessage(msg.getBody());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	};

	public void onResume() {
		chating = true;
	}

	public void send(String message) {
		try {
			chat.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	public void onClose() {
		chating = false;
	}

}
