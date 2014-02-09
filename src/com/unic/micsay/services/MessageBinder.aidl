package com.unic.micsay.services;

import android.net.Uri;
import com.unic.micsay.services.MessageListener;

interface MessageBinder {
	String currentAccount();

	void startChat(String email,MessageListener listener);

	void send(String message);

	void closeChat(String email);

}
