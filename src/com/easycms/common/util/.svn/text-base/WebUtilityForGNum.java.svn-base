package com.easycms.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author dengjiepeng:
 * @areate Date:2012-3-13
 * 
 */
public class WebUtilityForGNum {
	private static Logger log = Logger.getLogger(WebUtilityForGNum.class);
	private static GsonBuilder builder = new GsonBuilder();
	// private static HttpServletRequest request;
	private static final String EMAIL_PATTERN = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?$";
	private static final String USERNAME_PATTERN = "^[\\w\\u4e00-\\u9fa5]*$";
	private static final String NUMBER_PATTERN = "^([0-9]+)$";

	public static final String EMAIL = "EMAIL";
	public static final String THIRDPARTY = "THIRDPARTY";
	public static final String MOBILE = "MOBILE";
	public static final String BIND = "BIND";
	public static final String CHANGE = "CHANGE";
	public static final String ADD = "ADD";
	public static final String REGISTER = "REGISTER";
	public static final String SMS = "SMS";
	public static final String ID = "ID";
	public static final String VOICE = "VOICE";
	public static final String EN = "EN";
	public static final String CN = "CN";
	public static final String ZH = "zh";
	public static final String REG = "REG";
	public static final String REF = "REF";
	public static final String NUMBER = "NUMBER";
	public static final String NAME = "NAME";
	public static final String USERID = "USERID";
	public static final String GOLD = "GOLD";
	public static final String SILVER = "SILVER";
	public static final String BRONZE = "BRONZE";
	public static final String USER_IN_SESSION = "user";

	public static final String DISTURB = "gnum.com:";
	private static String TOKEN_FOR_REPEAT_SUBMIT = "TOKEN_FOR_REPEAT_SUBMIT";

	public static final TimeZone TIMEZONE_CHINA_SHANGHAI = TimeZone
			.getTimeZone("Asia/Shanghai");
	public static final TimeZone TIMEZONE_AMERICAN_NEWYORK = TimeZone
			.getTimeZone("America/New_York");
	public static final String TIMEZONE_GMT_TWO = "GMT+2";
	public static final String TIMEZONE_GMT_THREE = "GMT+3";
	public static final String TIMEZONE_GMT_FOUR = "GMT+4";
	public static final String TIMEZONE_GMT_FIVE = "GMT+5";
	public static final String TIMEZONE_GMT_SIX = "GMT+6";
	public static final String TIMEZONE_GMT_SEVEN = "GMT+7";
	public static final String TIMEZONE_GMT_ENGHT = "GMT+8";
	public static final String TIMEZONE_GMT_NINE = "GMT+9";

	public static Locale getI18nLocal(HttpServletRequest request) {
		Locale locale = (Locale) request.getSession().getAttribute(
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		return locale;
	}

	public static String getI18nLang(HttpServletRequest request) {
		Locale locale = (Locale) request.getSession().getAttribute(
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		String lang = locale == null ? WebUtilityForGNum.ZH : locale.getLanguage();
		return lang;
	}

	public static String getI18nIsoCode(HttpServletRequest request) {
		Locale locale = (Locale) request.getSession().getAttribute(
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		String isocode = null;
		if (locale != null)
			isocode = locale.getCountry();
		return isocode;
	}

	/**
	 * * 通配符比较
	 */
	public static boolean simpleWildcardMatch(String pattern, String str) {
		return wildcardMatch(pattern, str, "*");
	}

	/**
	 * 通配符比较
	 * 
	 * @param pattern
	 *            匹配规则
	 * @param str
	 *            待匹配字符串
	 * @param wildcard
	 *            通配符
	 * @return
	 */
	public static boolean wildcardMatch(String pattern, String str,
			String wildcard) {
		if (StringUtils.isEmpty(pattern) || StringUtils.isEmpty(str)) {
			return false;
		}
		/*
		 * final boolean startWith = pattern.startsWith(wildcard); final boolean
		 * endWith = pattern.endsWith(wildcard); String[] array =
		 * StringUtils.split(pattern, wildcard); int currentIndex = -1; int
		 * lastIndex = -1 ; switch (array.length) { case 0: return true ; case
		 * 1: currentIndex = str.indexOf(array[0]); if (startWith && endWith) {
		 * return currentIndex >= 0 ; } if (startWith) { return currentIndex +
		 * array[0].length() == str.length(); } if (endWith) { return
		 * currentIndex == 0 ; } return str.equals(pattern) ; default: for
		 * (String part : array) { currentIndex = str.indexOf(part); //
		 * System.out.println("part:"+part); //
		 * System.out.println("currentIndex:"+currentIndex); //
		 * System.out.println("lastIndex:"+lastIndex); if (currentIndex >=
		 * lastIndex) { lastIndex = currentIndex; continue; } return false; }
		 * return true; }
		 */

		pattern = pattern.replace("*", ".*");
		return Pattern.matches(pattern, str);

	}

	/**
	 * 获取带参数的请求URL
	 * 
	 * @param request
	 * @return URL字符串
	 */
	public static String getRequestURLWithQueryParrams(
			HttpServletRequest request) {
		StringBuffer sb = request.getRequestURL();
		Map<String, String[]> map = request.getParameterMap();
		if (map.size() > 0)
			sb.append("?");
		Iterator<Entry<String, String[]>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String[]> entry = it.next();
			String key = entry.getKey();
			String[] value = entry.getValue();
			String parram = key + "=" + value[0] + "&";
			sb.append(parram);
		}
		return sb.toString();
	}

	public static String getHostURL(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String tempContextUrl = url.delete(
				url.length() - request.getRequestURI().length(), url.length())
				.toString();
		return tempContextUrl;
	}

	public static String getProperty(String key) {
		try {
			Properties prop = new Properties();
			InputStream in = WebUtilityForGNum.class.getClassLoader()
					.getResourceAsStream("httpClientPath.properties");
			prop.load(in);
			return prop.getProperty(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Properties getPropertyFile(String fileName) {
		try {
			Properties prop = new Properties();
			InputStream in = WebUtilityForGNum.class.getClassLoader()
					.getResourceAsStream(fileName);
			prop.load(in);
			return prop;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isMobileNumber(String mobileNo) {
		boolean result = false;
		if (isVerify(mobileNo)) {
			String regex = "^(\\+)*([0-9])+$";
			result = Pattern.compile(regex).matcher(mobileNo).matches();
		}
		return result;
	}

	public static String toJson(Object obj) {
		Gson gson = builder.create();
		return gson.toJson(obj);
	}

	public static <T> T toObject(Class<T> clazz, String json) {
		Gson gson = builder.create();
		return gson.fromJson(json, clazz);
	}

	public static <T> T toObject(Type type, String json) {
		Gson gson = builder.create();
		return gson.fromJson(json, type);
	}

	public static void writeToClient(HttpServletResponse response, String msg,
			String code) {
		String defaultCode = "UTF-8";
		if (WebUtilityForGNum.isVerify(code))
			defaultCode = code;
		resp(response, msg, defaultCode);
	}

	public static void writeToClient(HttpServletResponse response, String msg) {
		String defaultCode = "UTF-8";
		resp(response, msg, defaultCode);
	}

	private static void resp(HttpServletResponse response, String msg,
			String code) {
		try {
			response.setCharacterEncoding(code);
			response.setContentType("text/html;charset=" + code);
			response.getWriter().write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * get browser language.
	 */
	public static String getBrowserLanuage(HttpServletRequest request) {
		Locale locale = (Locale) request.getSession().getAttribute(
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		String lanuage = null;
		if (locale != null) {
			log.debug("Get lanuage from session");
			lanuage = locale.getLanguage();
		} else {
			log.debug("Get lanuage from browser");
			lanuage = request.getLocale().getLanguage();
		}
		if (lanuage == null || lanuage == "") {
			log.debug("Get lanuage from browser is null,set the server lanuage");
			lanuage = Locale.getDefault().getLanguage();
		}
		log.debug("lanuage:" + lanuage);
		return lanuage;
	}

	/**
	 * set userId in session
	 * 
	 * @param userId
	 * @param request
	 */
	public static void setUserIdInSession(String userId,
			HttpServletRequest request) {
		if (isVerify(userId)) {
			request.getSession().setAttribute("userId", userId);
		}
	}

//	public static void setUserInSession(User user, HttpServletRequest request) {
//		if (user != null) {
//			request.getSession().setAttribute(USER_IN_SESSION, user);
//		}
//	}

	/**
	 * get userId from session
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserIdFromSession(HttpServletRequest request) {
		return (String) request.getSession().getAttribute("userId");
	}

//	public static User getUserFromSession(HttpServletRequest request) {
//		return (User) request.getSession().getAttribute(USER_IN_SESSION);
//	}

	/**
	 * to prevent repeat submit
	 */
	public static void makeToken(HttpServletRequest request) {
		String token = UUID.randomUUID().toString();
		log.info("makeToken:" + token);
		resetToken(request);
		request.getSession().setAttribute(TOKEN_FOR_REPEAT_SUBMIT, token);
	}

	public static String getToken(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(
				TOKEN_FOR_REPEAT_SUBMIT);
	}

	public static boolean isValidatedToken(HttpServletRequest request) {
		boolean result = false;
		String tokenFromRequest = request.getParameter("token");
		log.info("tokenFromRequest:" + tokenFromRequest);
		String tokenFromSession = (String) request.getSession().getAttribute(
				TOKEN_FOR_REPEAT_SUBMIT);
		log.info("tokenFromSession:" + tokenFromSession);
		if (isVerify(tokenFromSession) && isVerify(tokenFromRequest)) {
			if (tokenFromRequest.equals(tokenFromSession)) {
				result = true;
			}
		}
		return result;
	}

	public static void resetToken(HttpServletRequest request) {
		request.getSession().removeAttribute(TOKEN_FOR_REPEAT_SUBMIT);
	}

	public static String removePlus(String prefix) {
		if (isVerify(prefix)) {
			if (prefix.contains("+")) {
				prefix = prefix.substring(prefix.indexOf("+") + 1,
						prefix.length());
			}
		} else {
			prefix = "";
		}
		return prefix.trim();
	}

	/**
	 * md5
	 * 
	 * @param str
	 * @return
	 */
	public static String MD5Digest(String str) {
		// use md5
		String value = null;
		if (isVerify(str)) {
			value = DigestUtils.md5Hex(str);
		}
		return value;
	}

	/**
	 * make md5 with disturb string.
	 * 
	 * @param disturbStr
	 * @param str
	 * @return
	 */
	public static String MD5Digest(String disturbStr, String str) {
		// use md5
		if (!isVerify(disturbStr)) {
			disturbStr = "gnum.com:";
		}
		String value = null;
		if (isVerify(str)) {
			str = disturbStr + str;
			value = DigestUtils.md5Hex(str);
		}
		return value;
	}

	/**
	 * Verify Null
	 * 
	 * @param str
	 * @return if str is null or empty string return false ,otherwise return true
	 */
	public static boolean isVerify(String str) {
		if (str == null || "".equals(str)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * get identified code
	 * 
	 * @return
	 */
	public static String getVerifyCodeByCreated(HttpServletRequest request) {
		if (request != null) {
			return (String) request.getSession().getAttribute(
					com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		} else {
			return null;
		}
	}

	/**
	 * convert GMT
	 * 
	 * @param GMT
	 * @return
	 * @throws ParseException
	 */
	public static long convertCalendarToTimestamp(String calendar,
			TimeZone timezone) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(timezone);
		// log.info("timezone:"+timezone.getID());
		long time = format.parse(calendar).getTime();
		return time;
	}

	public static String convertCurrentTimestampToCalendar(TimeZone timezone) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			format.setTimeZone(timezone);
			Date date = new Date();
			String calendarStr = format.format(date);
			return calendarStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * public static void main(String[] args) throws ParseException {
	 * //1.receive the remote server's calendar. String remoteCalendar =
	 * "2012-05-24 01:23:02"; log.info("remoteCalendar:"+remoteCalendar);
	 * //2.convert remoteCalendar to timestamp long remoteTimestamp =
	 * convertCalendarToTimestamp(remoteCalendar,TIMEZONE_AMERICAN_NEWYORK);
	 * log.info("remoteTimestamp:"+remoteTimestamp); //3.make localCalendar
	 * String localCalendar =
	 * convertCurrentTimestampToCalendar(TIMEZONE_AMERICAN_NEWYORK);
	 * log.info("localCalendar:"+localCalendar); //4.convert localCalendar to
	 * timestamp long localTimestamp =
	 * convertCalendarToTimestamp(localCalendar,TIMEZONE_AMERICAN_NEWYORK);
	 * log.info("localTimestamp:"+localTimestamp); //5.compare with
	 * remoteTimestamp
	 * log.info("localTimestamp - remoteTimestamp = "+Math.abs(localTimestamp
	 * -remoteTimestamp)/1000+" s"); //print current system timestamp; String
	 * systemCalendar = new Date().toLocaleString(); long systemTimestamp =
	 * convertCalendarToTimestamp(systemCalendar,TIMEZONE_CHINA_SHANGHAI);
	 * log.info("system calendar:"+systemCalendar);
	 * log.info("system timestamp:"+systemTimestamp); //6.compare with
	 * currentSystem timestamp
	 * log.info("systemTimestamp - remoteTimestamp = "+Math
	 * .abs(systemTimestamp-remoteTimestamp)/1000+" s");
	 * 
	 * }
	 */

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

	public static String getIpFromHead(HttpServletRequest request) {

		Enumeration<String> headerNames = request.getHeaderNames();

		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			// log.info("<br>"+headerName+"="+request.getHeader(headerName));
		}

		String ip = null;
		ip = request.getHeader("X-Forwarded-For");
		log.info("Get header ip:" + ip);
		if (!isVerify(ip))
			ip = request.getRemoteAddr();
		return ip;
	}

	public static void disableBrowserCache(HttpServletResponse response) {
		response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", -1);
	}

	/**
	 * 得到request
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}

	/**
	 * 得到session
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request.getSession();
	}

	/**
	 * 得到context
	 * 
	 * @return
	 */
	public static ServletContext getServletContext() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request.getSession().getServletContext();
	}

	/**
	 * 去除字符串中的所有空格
	 * 
	 * @param str
	 * @return
	 */
	public static String removeBlank(String str) {
		StringBuffer sb = new StringBuffer();
		if (isVerify(str)) {
			int length = str.length();
			for (int i = 0; i < length; i++) {
				char s = str.charAt(i);
				if (0 == s || 32 == s) {
					continue;
				} else {
					sb.append(s);
				}
			}
			str = sb.toString();
		}
		return str;
	}

	public static String formatTime(String seconds) {
		int time = Integer.parseInt(seconds);
		DecimalFormat df = new DecimalFormat("00");
		int sec = time%60;
		int min = (time%3600)/60;
		int hour = time/3600;
		
		String sec_str = df.format(sec);
		String min_str = df.format(min);
		String hour_str = df.format(hour);
		
		String time_str = "";
		if(hour == 0) {
			if(min == 0) {
				time_str = sec_str +" secs";
			} else {
				time_str = min_str + " mins " + sec_str +" secs";
			}
		} else {
			time_str = hour_str + " hous " + min_str + " mins " + sec_str +" secs";
		}
		//01 hous 00 mins 00 secs
		return time_str;
	}
	
	/**
	 * 生成随机字符串码
	 * @param length 串码长度
	 * @return
	 */
	public static String getRandCNString(int length) {
		String charList = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String rev = " ";
		java.util.Random f = new Random();
		for (int i = 0; i < length; i++) {
			rev += charList.charAt(Math.abs(f.nextInt()) % charList.length());
		}
		return rev.trim();
	}
/**
 * 根据cookiename查找cookie的value
 * @param request
 * @param cookieName
 * @return
 */
	public static String findCookie(HttpServletRequest request,String cookieName){
		log.info("to find cookieName:>>>"+cookieName);
		if(null==cookieName)
			return null;
		//获取cookie判断用户是否之前已经登录
		Cookie[] cookies = request.getCookies();
		//遍历cookie是否存在用户登录信息
		if(cookies==null||cookies.length<1){
			return null;
		}
		for(Cookie cookie:cookies){
			if(cookieName.equals(cookie.getName())){
				return cookie.getValue();
			}
			//log.info("cookieName:>>> "+cookie.getName()+"   cookieValue: "+cookie.getValue());
		}
		return null;
	}
	
	public static void removeCookie(HttpServletRequest request,HttpServletResponse response,String cookieName){
	
		Cookie[] cookies = request.getCookies();
		if(cookies==null||cookies.length==0){
			
			return ;
		}
		for(Cookie cookie:cookies){
			if(cookieName.equals(cookie.getName())){
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
				System.out.println("删除cookie");
			}
		}
		
	}
	/**
	 * 计算百分比
	 * @param p1
	 * @param p2
	 * @param digit
	 * @return
	 */
	public static String percent(double p1, double p2,int digit) {
		  String str;
		  if(p2==0) return "0%";
		  double p3 = p1 / p2;
		  NumberFormat nf = NumberFormat.getPercentInstance();
		  nf.setMinimumFractionDigits(digit);
		  str = nf.format(p3);
		  return str;
	}
	
	public static String randomColor(){
		 String r,g,b;    
        Random random = new Random();    
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();    
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();    
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();    
            
        r = r.length()==1 ? "0" + r : r ;    
        g = g.length()==1 ? "0" + g : g ;    
         b = b.length()==1 ? "0" + b : b ;  
         
         return r+g+b;
		
	}
	public static void main(String[] args) {
		// System.out.println(MD5Digest("gnum.com:123456"));
		// System.out.println(ResourceUtil.class.getClassLoader().getResource(""));
		String intactName = "2009617235311.jpg";
		String[] names = intactName.split("\\.");
		log.info("[names size]:" + names.length);
		for (String n : names) {
			System.out.println("[name]:" + n);
		}
	}

}
