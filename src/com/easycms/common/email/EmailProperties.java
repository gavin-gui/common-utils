package com.easycms.common.email;

import java.io.IOException;
import java.util.Properties;


public class EmailProperties {

	private static Properties prop;
	static{
		prop = new Properties();
		try {
			prop.load(EmailProperties.class.getClassLoader().getResourceAsStream("email.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValueByKey(String key){
		return prop.getProperty(key);
	}
}
