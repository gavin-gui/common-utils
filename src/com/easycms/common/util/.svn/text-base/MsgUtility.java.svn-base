package com.easycms.common.util;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Copyright (C), 2002-2012, www.globalroam.com.cn
 * 
 * @Author : jiepeng Description :
 * 
 * @Data : 2012-4-26
 * 
 */
public class MsgUtility {
	private static Logger log = Logger.getLogger(MsgUtility.class);
	private static Map<String, Map<String, String>> statusCodeMap = new HashMap<String, Map<String, String>>();
	private static String defaultErrorDescription = null;
	private static String defaultSuccessDescription = null;
	// password
	static {
		if (statusCodeMap.isEmpty()) {
			log.debug("[Start]:load message.");
			try {
				SAXReader reader = new SAXReader();
				URL  url = MsgUtility.class.getClassLoader().getResource("error-message.xml");
				if(url!=null) {
					//System.out.println("url:" + url.toString());
					Document doc = reader.read(url);
					getMessages(statusCodeMap, doc);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			log.info("[End]:load message.");
		}
	}

	private static void getMessages(
			Map<String, Map<String, String>> statusCodeMap, Document doc) {
		Element root = doc.getRootElement();
		Element error = root.element("default-error-description");
		if(error!=null) {
			defaultErrorDescription = error.getText();
		}
		Element success = root.element("default-correct-description");
		if(success!=null) {
			defaultSuccessDescription = success.getText();
		}
		List<Element> containers = root.elements("container");
		//System.out.println("containers.size:" + containers.size());
		for (Element e : containers) {
			Map<String, String> map = new HashMap<String, String>();
			String key  = e.attributeValue("methodName");
			log.debug("loading " + key + "'s messages.");
			List<Element> messages = e.elements("message");
			for (Element msg : messages) {
				String code = msg.element("code").getText();
				String description = msg.element("description").getText();
				map.put(code, description);
				//log.info("code:" + code);
				//log.info("description:" + description);
			}
			statusCodeMap.put(key, map);
		}
	}

	/**
	 * Create message by specify code and description from remote server.
	 * 
	 * @param type
	 * @param code
	 * @return
	 */
	public static CustomMessage combinationMessage(String desc, String code) {
		CustomMessage msg = new CustomMessage();
		if (WebUtilityForGNum.isVerify(desc) && WebUtilityForGNum.isVerify(code)) {
			boolean available = false;
			if ("1000".equals(code)) {
				available = true;
			}
			msg.setAvailable(available);
		}
		msg.setCodeStatus(code);
		msg.setCodeDescribe(desc);
		return msg;
	}

	public static CustomMessage makeMessage(String methodName,String code,String desc) {
		CustomMessage msg = new CustomMessage();
		if (WebUtilityForGNum.isVerify(code)) {
			boolean available = false;
			Map<String, String> resultMap = statusCodeMap.get(methodName);
			if (resultMap!=null && !resultMap.isEmpty()) {
				String detail = resultMap.get(code);
				//log.info("desc:"+desc);
				if(detail==null && desc == null) {
					desc = defaultErrorDescription;
				}
			}
			if(Integer.parseInt(code) == 1000) {
			//if ("1000".equals(code)) {
				available = true;
				if(desc==null) {
					desc = defaultSuccessDescription;
				}
			}
			msg.setCodeStatus(code);
			msg.setCodeDescribe(desc);
			msg.setAvailable(available);
		}
		return msg;
	}

	/**
	 * verify the msg
	 * 
	 * @param msg
	 * @return
	 */
	public static boolean isSuccess(CustomMessage msg) {
		boolean result = false;
		if (msg != null) {
			boolean isTimeout = msg.isTimeout();
			boolean available = msg.isAvailable();
			if (!isTimeout && available) {
				result = available;
			}
		}
		return result;
	}

	/**
	 * If timeout,set true
	 * 
	 * @return
	 */
	public static CustomMessage serverRefused() {
		CustomMessage msg = new CustomMessage();
		msg.setTimeout(true);
		msg.setCodeStatus("-1");
		msg.setCodeDescribe("Connect remote server timeout!");
		return msg;
	}

	/**
	 * put msg into request
	 * 
	 * @param msg
	 * @param request
	 */
	public static void pushMsg(CustomMessage msg, HttpServletRequest request) {
		request.setAttribute("customMessage",
				msg.getCodeStatus() + "," + msg.getCodeDescribe());
		request.setAttribute("customMsg", msg);
	}

	/**
	 * judge error
	 * 
	 * @return
	 */
	public static boolean hasError(CustomMessage msg) {
		boolean result = false;
		if (msg != null) {
			if (!isSuccess(msg)) {
				result = true;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		/*
		 * CustomMessage msg = new CustomMessage();
		 * msg.getInfoMap().put("userId", "1701@gr");
		 * System.out.println("userId:"+msg.getInfoMap().get("userId"));
		 */

	}

}
