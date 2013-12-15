/**
 * 
 */
package com.unic.micoq.services;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.util.Log;

import com.unic.rainbow.workstation.Action;
import com.unic.rainbow.workstation.ToolSet;

/**
 * @author javafx
 * 
 * 
 * 
 * 
 * 
 */
public class LoginService implements Action {

	public LoginService(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}

	private final String userName;
	private final String passWord;

	public interface LoginMonitor {

		public void onLoging(LoginService service);

		public void onSuccess(MessageService service);

		public void onFailed();

	}

	private boolean canceled = false;
	private LoginMonitor monitor = new LoginMonitor() {

		@Override
		public void onSuccess(MessageService service) {

		}

		@Override
		public void onLoging(LoginService service) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFailed() {
			// TODO Auto-generated method stub

		}
	};

	public void cancel() {
		this.canceled = true;
	}

	public void setMonitor(LoginMonitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public void execute(ToolSet toolset) {
		monitor.onLoging(this);
		ConnectionConfiguration config = new ConnectionConfiguration(
				"192.168.1.106", 6100);
		/** 是否启用安全验证 */
		config.setCompressionEnabled(false);
		config.setSASLAuthenticationEnabled(false);
		config.setVerifyChainEnabled(false);
		config.setSelfSignedCertificateEnabled(false);
		/** 创建connection链接 */
		XMPPConnection connection = null;
		if (canceled) {
			return;
		}
		try {
			connection = new XMPPConnection(config);
			/** 建立连接 */
			connection.connect();
		} catch (XMPPException e) {
			e.printStackTrace();
			monitor.onFailed();
		}

		if (!connection.isConnected() && !connection.isAuthenticated()) {
			return;
		}

		Log.d("bluesky", "contected...");
		try {
			Log.d("bluesky", userName + ":" + passWord);
			connection.login(userName, passWord);
			MessageService service = new MessageService(connection);
			monitor.onSuccess(service);
		} catch (XMPPException e) {
			e.printStackTrace();
			monitor.onFailed();
		}
	}
}
