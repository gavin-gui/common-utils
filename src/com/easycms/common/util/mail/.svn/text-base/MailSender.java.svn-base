package com.easycms.common.util.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.easycms.common.util.CommonUtility;


/**
 * 邮件发送工具类
 * @author jiepeng
 *
 */
public class MailSender {
//	private static String protocol = null;
	
	public static void sendMailByDefault() {
		Properties pro = CommonUtility.getPropertyFile("mail.properties");
		sendMailWithTxtByDefaultSetting(pro.getProperty("mail.simple.subject")
				,pro.getProperty("mail.simple.content"));
	}
	
	public static void sendMailWithTxtByDefaultSetting(String subject,String content) {
		 processMailByDefaultSetting(subject,content,MailProcessor.MIMETYPE_TXT);
	}
	
	public static void sendMailWithHtmlByDefaultSetting(String subject,String content) {
		 processMailByDefaultSetting(subject,content,MailProcessor.MIMETYPE_HTML);
	}
	
	public static void sendMailWithHtml(Email email) {
		new Thread(new MailProcessor(email,MailProcessor.MIMETYPE_HTML)).start();
	}
	
	protected static void processMailByDefaultSetting(String subject,String content,String mimeType) {
		
		Properties pro = CommonUtility.getPropertyFile("mail.properties");
		String addressTo = pro.getProperty("mail.address.to");
		String addressFrom = pro.getProperty("mail.address.from");
		List<String> addressToList = null;
		List<String> addressCcList = null;
		List<String> addressBccList = null;
		
		if(CommonUtility.isNonEmpty(addressTo)) {
			String[] addressToArray = addressTo.split(",");
			addressToList = new ArrayList<String>();
			for(String to : addressToArray) {
				addressToList.add(to);
			}
		}
		
		//准备抄送人
		String addressCc = pro.getProperty("mail.address.cc");
		if(CommonUtility.isNonEmpty(addressCc)) {
			String[] addressCcArray = addressCc.split(",");
			addressCcList = new ArrayList<String>();
			for(String cc : addressCcArray) {
				addressCcList.add(cc);
			}
		}
		
		//准备暗抄送人
		String addressBcc = pro.getProperty("mail.address.bcc");
		if(CommonUtility.isNonEmpty(addressBcc)) {
			String[] addressBccArray = addressBcc.split(",");
			addressBccList = new ArrayList<String>();
			for(String bcc : addressBccArray) {
				addressBccList.add(bcc);
			}
		}
		
		Email email = new Email();
		email.setAddressFrom(addressFrom);
		email.setSubject(subject);
		email.setContent(content);
		email.setAddressTo(addressToList);
		email.setAddressCc(addressCcList);
		email.setAddressBcc(addressBccList);
		
		//执行发送邮件
		new Thread(new MailProcessor(email, mimeType)).start();
	}
	
//	protected static boolean processMail(String subject,String content,List<String> toList
//			,List<String> ccList,List<String> bccList,String mimeType) {
//		Properties pro = WebUtility.getPropertyFile("mail.properties");
//		Email mail = new Email();
//		mail.setSubject(subject);
//		mail.setContent(content);
//		mail.setSendDate(new Date());
//		mail.setAddressFrom(pro.getProperty("mail.address.from"));
//		//设置收件人
//		if(toList!=null && !toList.isEmpty()) {
//			mail.setAddressTo(toList);
//		}else{
//			System.out.println("need address to!");
//			return false;
//		}
//		//设置明抄送人
//		if(ccList!=null && !ccList.isEmpty()) {
//			mail.setAddressCc(ccList);
//		}
//		//设置暗抄送人
//		if(bccList!=null && !bccList.isEmpty()) {
//			mail.setAddressBcc(bccList);
//		}
//		return MailSender.sendMail(mail,mimeType);
//	}
	
//	public static void main(String[] args) {
////		System.out.println("To send email");
////		MailSender.sendMailByDefault();
////		System.out.println("Send end");
//		
//		Map<String,Object> rootMap = new HashMap<String,Object>();
//		String content = TemplateUtility.getTemplateAsString("/email/email_template.html", rootMap);
//		String subject = " BudgetRoam Shop order:xxxx";
//		MailSender.sendMailWithHtmlByDefaultSetting(subject, content);
//	}
}
