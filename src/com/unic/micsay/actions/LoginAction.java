package com.unic.micsay.actions;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.unic.micsay.services.MessageCenter;
import com.unic.rainbow.workstation.Action;
import com.unic.rainbow.workstation.ToolSet;

public class LoginAction implements Action {

	public LoginAction(String userName, String password,
			MessageCenter messageCenter) {
		this.userName = userName;
		this.passWord = password;
		this.messageCenter = messageCenter;
	}

	private final MessageCenter messageCenter;
	private final String userName;
	private final String passWord;

	@Override
	public void execute(ToolSet toolset) {
		ConnectionConfiguration config = new ConnectionConfiguration(
				"192.168.1.105", 5222);
		config.setReconnectionAllowed(true);
		config.setSendPresence(true);
		/** 是否启用安全验证 */
		config.setCompressionEnabled(false);
		config.setSASLAuthenticationEnabled(false);
		config.setVerifyChainEnabled(false);
		config.setSelfSignedCertificateEnabled(false);
		/** 创建connection链接 */
		XMPPConnection connection = null;
		if (toolset.isCancel()) {
			return;
		}
		try {
			connection = new XMPPConnection(config);
			/** 建立连接 */
			connection.connect();
		} catch (XMPPException e) {
			messageCenter.onLoginFailed();
		}

		if (!connection.isConnected() && !connection.isAuthenticated()) {
			return;
		}

		try {
			connection.login(userName, passWord);
			messageCenter.onLoginSuccess(connection, userName, passWord);
		} catch (XMPPException e) {
			messageCenter.onLoginFailed();
		}
	}

}
