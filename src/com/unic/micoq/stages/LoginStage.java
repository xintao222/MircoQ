/**
 * 
 */
package com.unic.micoq.stages;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.sasl.SASLMechanism.Success;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.unic.cicoco.plugins.WorkStationStage;
import com.unic.micoq.R;
import com.unic.micoq.services.LoginService;
import com.unic.micoq.services.MessageService;

/**
 * @author javafx
 * 
 */
public class LoginStage extends WorkStationStage {

	private EditText userNameView = null;
	private EditText passwordView = null;
	private EditText message = null;
	private View loginPanel = null;
	private View messagePanel = null;
	private TextView shower = null;

	private MessageService messageService = null;
	private Chat chat = null;

	private final int SUCCESS = 1001;

	@Override
	public void onCreate(Bundle savedInstanceState, Intent intent) {
		super.onCreate(savedInstanceState, intent);
		setContentView(R.layout.activity_login);
		findViewById(R.id.loginBtn).setOnClickListener(loginBtn);
		findViewById(R.id.sendBtn).setOnClickListener(sendBtn);
		findViewById(R.id.logoutBtn).setOnClickListener(logoutBtn);
		loginPanel = findViewById(R.id.loginPanel);
		messagePanel = findViewById(R.id.messagePanel);
		shower = (TextView) findViewById(R.id.shower);
		userNameView = (EditText) findViewById(R.id.userName);
		passwordView = (EditText) findViewById(R.id.passWord);
		message = (EditText) findViewById(R.id.messageEdit);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SUCCESS: {
				loginPanel.setVisibility(View.GONE);
				messagePanel.setVisibility(View.VISIBLE);
			}

				break;

			default:
				break;
			}
		}

	};

	private View.OnClickListener loginBtn = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			String userName = userNameView.getText().toString();
			String password = passwordView.getText().toString();
			LoginService service = new LoginService(userName, password);
			service.setMonitor(loginMonitor);
			workstation.dispatch(service);
		}
	};

	private LoginService.LoginMonitor loginMonitor = new LoginService.LoginMonitor() {

		@Override
		public void onSuccess(MessageService service) {
			handler.obtainMessage(SUCCESS).sendToTarget();
			messageService = service;
			chat = messageService.createChat("javafx", messageListener);
		}

		@Override
		public void onFailed() {

		}

		@Override
		public void onLoging(LoginService service) {

		}
	};

	private MessageListener messageListener = new MessageListener() {

		@Override
		public void processMessage(Chat chat, Message msg) {
			shower.append(msg.getBody());
		}
	};

	private View.OnClickListener logoutBtn = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			messageService.shutdown();
			loginPanel.setVisibility(View.VISIBLE);
			messagePanel.setVisibility(View.GONE);
		}
	};

	private View.OnClickListener sendBtn = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				chat.sendMessage(message.getText().toString());
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}
	};

}
