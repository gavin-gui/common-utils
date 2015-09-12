package com.easycms.common.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class PwdAuthenticator extends Authenticator {
	private String password = null;
	private String username = null;
	
	public PwdAuthenticator(){}
	public PwdAuthenticator(String username,String password){
		this.password = password;
		this.username = username;
	}
	
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
}
