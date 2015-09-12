package com.easycms.common.util.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.easycms.common.util.CommonUtility;

public class MailProcessor implements Runnable {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MailProcessor.class);
	
	private Email email;
	private String mimeType;
	private static final String host;
	private static final String port;
	private static final String username;
	private static final String password;
	private static Properties prop = new Properties();;
	private static boolean auth = false;
	private static boolean starttls = false;
	public static String MIMETYPE_TXT = "txt";
	public static String MIMETYPE_HTML = "html";

	static {
		try {
			InputStream in = MailSender.class.getClassLoader().getResourceAsStream("mail.properties");
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取邮件参数
		host = prop.getProperty("mail.smtp.host");
		port = prop.getProperty("mail.smtp.port");
		auth = Boolean.valueOf(prop.getProperty("mail.smtp.auth"));
		starttls = Boolean.valueOf(prop.getProperty("mail.smtp.starttls.enable"));
		username = prop.getProperty("mail.username");
		password = prop.getProperty("mail.password");
	}
	
	public MailProcessor(Email email,String mimeType) {
		this.email = email;
		this.mimeType = mimeType;
	}
	
	/**
	 * 得到发送Session
	 * @return
	 */
	protected static Session getSession() {
		PwdAuthenticator pa = new PwdAuthenticator();
		Properties pro = new Properties();
		pro.setProperty("mail.smtp.host", host);
		pro.setProperty("mail.smtp.port", port);
		if(auth) {
			pro.setProperty("mail.smtp.auth", String.valueOf(auth));
			pa = new PwdAuthenticator(username, password);
		}
		if(starttls) {
			pro.setProperty("mail.smtp.starttls.enable", String.valueOf(starttls));
			pa = new PwdAuthenticator(username, password);
		}
		return Session.getDefaultInstance(pro, pa);
	}
	
	/**
	 * 发送简单邮件(不含附件)
	 * @param email
	 * @param mimeType	发送文本类型，txt或者html可选
	 * @return
	 */
	public static boolean sendMail(Email email,String mimeType) {
		try {
			
			//创建发送者地址
			Message mailMsg = new MimeMessage(getSession());
			String addressFrom = email.getAddressFrom();
			if(!CommonUtility.isNonEmpty(addressFrom)) {
				addressFrom = username;
			}
			Address from = new InternetAddress(addressFrom);
			
			//设置邮件发送者
			mailMsg.setFrom(from);
			
			//创建邮件收件者,可能有多个接收者
			//设置邮件收件者
			List<String> toAddressList = email.getAddressTo();
			/*Address[] toAddress = {};
			for(String to : toAddressList) {
				toAddress = (Address[]) ArrayUtils.add(toAddress, new InternetAddress(to));
			}*/
			mailMsg.setRecipients(Message.RecipientType.TO, conver2Array(toAddressList));
			
			//创建邮件明抄送,可能有多个接收者
			//设置邮件明抄送
			List<String> ccAddressList = email.getAddressCc();
			if(ccAddressList != null) {
				/*Address[] ccAddress = {};
				for(String cc : ccAddressList) {
					ccAddress = (Address[]) ArrayUtils.add(ccAddress, new InternetAddress(cc));
				}*/
				mailMsg.setRecipients(Message.RecipientType.CC, conver2Array(ccAddressList));
			}
			
			//创建邮件暗抄送,可能有多个接收者
			//设置邮件暗抄送
			List<String> bccAddressList = email.getAddressBcc();
			if(bccAddressList != null) {
				/*Address[] bccAddress = {};
				for(String bcc : bccAddressList) {
					bccAddress = (Address[]) ArrayUtils.add(bccAddress, new InternetAddress(bcc));
				}*/
				mailMsg.setRecipients(Message.RecipientType.BCC, conver2Array(bccAddressList));
				
			}
			
			//设置邮件主题
			mailMsg.setSubject(email.getSubject());
			
			//设置发送时间
			mailMsg.setSentDate(email.getSendDate());
			
			//设置邮件内容，根据mineType来选择html或者txt
			if(MIMETYPE_HTML.equalsIgnoreCase(mimeType)) {
				MimeMultipart mimeMultipart = new MimeMultipart();
				BodyPart htmlPart = new MimeBodyPart();
				htmlPart.setContent(email.getContent(),"text/html; charset=utf-8");
				mimeMultipart.addBodyPart(htmlPart);
				mailMsg.setContent(mimeMultipart);
			}else if(MIMETYPE_TXT.equalsIgnoreCase(mimeType)) {
				mailMsg.setText(email.getContent());
			}else{
				mailMsg.setText(email.getContent());
			}
//			System.out.println("till here");
//			System.out.println(WebUtility.toJson(mailMsg));
			Transport.send(mailMsg);
			return true;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			System.out.println("Need address!");
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Override
	public void run() {
		try {
			email.setSendDate(new Date());
			logger.info("----- Send Email ------ ");
			logger.info("==> AddressFrom : " + email.getAddressFrom());
			logger.info("==> AddressTo : " + email.getAddressTo());
			logger.info("==> AddressCC : " + email.getAddressCc());
			logger.info("==> AddressBCC : " + email.getAddressBcc());
			logger.info("==> Subject : " + email.getSubject());
			logger.info("==> SendDate : " + email.getSendDate());
			
			boolean result = sendMail(email,mimeType);
			logger.info("==> Result : Send <" + (result?"OK":"FAILED") + ">");
			logger.info("----------------------- ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static Address[] conver2Array(List<String> toList) {
		try {
		List<Address> addressList = new ArrayList<Address>();
		if(toList != null) {
			for(String to : toList) {
					addressList.add(new InternetAddress(to));
			}
		}
		return addressList.toArray(new Address[addressList.size()]);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
