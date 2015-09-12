package com.easycms.common.email;

import java.io.File;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;

public class TempLateMailSender {

	private Logger logger = Logger.getLogger(TempLateMailSender.class);
	private JavaMailSender mailSender;

	private FreeMarkerConfigurer freeMarkerConfigurer;// FreeMarker的技术类

	private static final String ENCODING = "utf-8";

	public void setSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setFreeMarkerConfigurer(
			FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	public boolean sendEmailWithoutAttachment(Map<String, Object> variables,
			String fromEmail, String toEmail, String subject,
			String templateName) {
		return sendEmail(variables, fromEmail, toEmail, subject, templateName,
				null);
	}

	public boolean sendEmailWithAttachment(Map<String, Object> variables,
			String fromEmail, String toEmail, String subject,
			String templateName) {
		return sendEmail(variables, fromEmail, toEmail, subject, templateName,
				templateName);
	}

	private boolean sendEmail(Map<String, Object> variables, String fromEmail,
			String toEmail, String subject, String templateName,
			String attachmentPath) {
		MimeMessage msg = null;
		try {
			msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true,
					ENCODING);
			helper.setFrom(fromEmail);
			helper.setTo(toEmail);
			helper.setSubject(MimeUtility.encodeText(subject, ENCODING, "B"));
			helper.setText(getMailText(variables, templateName), true); // true表示text的内容为html
			// 添加内嵌文件，第1个参数为cid标识这个文件,第2个参数为资源
			// helper.addInline("welcomePic", new File("d:/welcome.gif")); //
			// 附件内容
			// 这里的方法调用和插入图片是不同的，解决附件名称的中文问题
			if (attachmentPath != null) {
				File file = new File(attachmentPath);
				helper.addAttachment(MimeUtility.encodeWord(file.getName()),
						file);
			}
		} catch (Exception e) {
			throw new RuntimeException("error happens", e);
		}
		mailSender.send(msg);
		return true;
	}

	private String getMailText(Map<String, Object> variables,
			String templateName) {
		String htmlText = "";
		try {
			// 通过指定模板名获取FreeMarker模板实例
			Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(
					templateName);
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,
					variables);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmlText;
	}

}
