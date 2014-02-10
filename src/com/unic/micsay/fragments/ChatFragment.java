package com.unic.micsay.fragments;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.unic.micsay.R;
import com.unic.micsay.activitys.MainActivity;
import com.unic.micsay.contentprovider.Tables;
import com.unic.micsay.services.MessageBinder;
import com.unic.micsay.services.MessageCenter;
import com.unic.micsay.services.MessageListener;

public class ChatFragment extends Fragment {
	private MainActivity mainActivity = null;
	private TextView titleView = null;
	private TextView chatArea = null;
	private EditText msgBox = null;
	private Button sendBtn = null;
	private String peer = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mainActivity = (MainActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chat_fragment, null);
		this.titleView = (TextView) view.findViewById(R.id.title);
		chatArea = (TextView) view.findViewById(R.id.chatArea);
		msgBox = (EditText) view.findViewById(R.id.msgbox);
		sendBtn = (Button) view.findViewById(R.id.sendBtn);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		this.titleView.setText(bundle.getString(Tables.Contact.Columns.ALIAS));
		peer = bundle.getString(Tables.Contact.Columns.EMAIL);
		sendBtn.setOnClickListener(sendListner);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent service = new Intent(mainActivity, MessageCenter.class);
		this.mainActivity.bindService(service, serviceConnection,
				Context.BIND_AUTO_CREATE);
	}

	private MessageBinder messageBinder = null;

	private MessageListener.Stub listener = new MessageListener.Stub() {

		@Override
		public void processMessage(String msg) throws RemoteException {
			chatArea.append(msg + "\n");
		}
	};

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			messageBinder = null;

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			messageBinder = MessageBinder.Stub.asInterface(service);
			try {
				messageBinder.startChat(peer, listener);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};

	private View.OnClickListener sendListner = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (null != messageBinder) {
				final String msg = msgBox.getText().toString();
				try {
					messageBinder.send(msg);
					chatArea.append(msg + "\n");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				msgBox.setText("");
			}
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (null != messageBinder) {
			try {
				messageBinder.closeChat(peer);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		mainActivity.unbindService(serviceConnection);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mainActivity = null;
	}

}
