package com.unic.micsay.services;

import android.net.Uri;

interface MessageBinder {
	String currentAccount();

	void startChat(String email);

	void send(String message);

	void closeChat(String email);

}
