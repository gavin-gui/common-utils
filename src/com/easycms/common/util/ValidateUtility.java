package com.easycms.common.util;

import java.util.regex.Pattern;

/**
 * @author dengjiepeng:
 * @areate Date:2012-3-13
 * 
 */
public class ValidateUtility {
	// private static HttpServletRequest request;
	private static final String EMAIL_PATTERN = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?$";
	private static final String USERNAME_PATTERN = "^[\\w\\u4e00-\\u9fa5]*$";
	private static final String NUMBER_PATTERN = "^([0-9]+)$";

	/**
	 * Verify Null
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isVerify(String str) {
		if (str == null || "".equals(str)) {
			return false;
		} else {
			return true;
		}
	}


	/**
	 * is email pattern
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
		if (isVerify(email) && emailPattern.matcher(email).matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isUsername(String username) {
		Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN);
		if (isVerify(username) && usernamePattern.matcher(username).matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNumber(String number) {
		Pattern numberPattern = Pattern.compile(NUMBER_PATTERN);
		if (isVerify(number) && numberPattern.matcher(number).matches()) {
			return true;
		} else {
			return false;
		}

	}
}
