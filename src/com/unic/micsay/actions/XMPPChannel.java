package com.unic.micsay.actions;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;
import com.unic.micsay.contentprovider.Tables;
import com.unic.micsay.datasource.ContactDataSource;
import com.unic.micsay.models.User;
import com.unic.micsay.services.MessageBinder;
import com.unic.micsay.services.MessageListener;
import com.unic.rainbow.workstation.Action;
import com.unic.rainbow.workstation.ToolSet;

public class XMPPChannel extends MessageBinder.Stub implements Action {

	public XMPPChannel(Context context, XMPPConnection connection, Uri account) {
		this.context = context;
		this.connection = connection;
		this.account = account;
	}

	private Context context = null;
	private XMPPConnection connection = null;
	private Uri account = null;
	private Map<String, ChatAction> chats = new HashMap<String, ChatAction>();
	private ChatAction currentChat = null;

	@Override
	public void execute(ToolSet toolSet) {
		ContactDataSource cantacts = new ContactDataSource(context);
		final int count = cantacts.getCount();
		Set<String> emails = new HashSet<String>();
		for (int i = 0; i < count; i++) {
			User user = (User) cantacts.getItem(i);
			emails.add(user.getEmail());
		}
		cantacts.close();
		ContentResolver cr = context.getContentResolver();
		final String id = account.getLastPathSegment();
		Roster roster = this.connection.getRoster();
		final String serviceName = this.connection.getServiceName();
		Collection<RosterEntry> entries = roster.getEntries();
		for (RosterEntry en : entries) {
			if (emails.contains(en.getName() + "@" + serviceName)) {
				continue;
			}
			ContentValues values = new ContentValues();
			values.put(Tables.Contact.Columns.ALIAS, en.getUser());
			values.put(Tables.Contact.Columns.EMAIL, en.getName() + "@"
					+ serviceName);
			values.put(Tables.Contact.Columns.OWNER, id);
			cr.insert(Tables.Contact.CONTENT_URL, values);
		}
	}

	@Override
	public String currentAccount() throws RemoteException {
		return account.toString();
	}

	@Override
	public void startChat(String email, MessageListener listener)
			throws RemoteException {
		if (null != currentChat) {
			currentChat.onPause();
		}
		if (chats.containsKey(email)) {
			this.currentChat = chats.get(email);
			currentChat.onResume();
			return;
		}
		ChatAction chatAction = new ChatAction(listener);
		Chat chat = connection.getChatManager().createChat(email,
				chatAction.listener);
		chatAction.setChat(chat);
		chats.put(email, chatAction);
		currentChat = chatAction;
	}

	@Override
	public void send(String message) throws RemoteException {
		currentChat.send(message);
	}

	@Override
	public void closeChat(String email) throws RemoteException {
		if (chats.containsKey(email)) {
			ChatAction chat = chats.get(email);
			chat.onClose();
			chats.remove(email);
			if (currentChat == chat) {
				currentChat = null;
			}
		}
	}

}
