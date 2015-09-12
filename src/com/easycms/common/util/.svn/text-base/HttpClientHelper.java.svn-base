package com.easycms.common.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.easycms.common.domain.RespEntity;


/**
 * @author dengjiepeng:
 * @areate Date:2012-3-15
 * 
 */
public class HttpClientHelper {
	private static Logger log = Logger.getLogger(HttpClientHelper.class);
	private static Properties prop = null;
//	public static final String DEFAULTPORT = "80";
	public static final String HTTPS = "https";
	public static final String HTTP = "http";
	public static final String POST_METHOD = "POST";
	public static final String GET_METHOD = "GET";
	public static final String PUT_METHOD = "PUT";
	public static final String DELETE_METHOD = "DELETE";
	
	private static final Integer QUERY_PARAMS = 0;
	private static final Integer JSON_PARAMS = 1;
	private static String ip;
	private static String port;
	
	static {
		try {
			prop = new Properties();
			log.debug("Start Load HttpClientPath File..");
			InputStream in = HttpClientHelper.class.getClassLoader()
					.getResourceAsStream("httpClientPath.properties");
			prop.load(in);
			log.debug("End Load HttpClientPath File..");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getIp() {
		if (ip == null) {
			ip = prop.getProperty("ip");
		}
		return ip;
	}

	public static String getParam(String name) {
		if (prop != null) {
			return prop.getProperty(name);
		} else {
			return null;
		}
	}
	
	public static String getPort() {
		if (port == null) {
			port = prop.getProperty("port");
		}
		return port;
	}

	public static String getPath(String pathSuffix) {
		if (prop != null) {
			String path = prop.getProperty(pathSuffix);
			if (path.startsWith("/")) {
				return path;
			} else {
				return "/" + path;
			}
		} else {
			return null;
		}
	}

	/**
	 * 发起HTTP请求
	 * @param parramMap	查询字符串数组
	 * @param path	请求的URI
	 * @param ip	域名或者IP地址
	 * @param port	端口,默认80可不填
	 * @param scheme	协议，http/https
	 * @param method	查询方式,支持Restful方式，GET/PUT/POST/DELETE
	 */
	public static String callRemoteService(Map<String, String> parramMap, 
			String path,String ip,String port,String scheme,String method)
					throws Exception {
		return process(parramMap,null,null,path,ip,port,scheme,method,QUERY_PARAMS).getData();
	}
	
	/**
	 * 发起HTTP请求
	 * @param parramMap	查询字符串数组
	 * @param path	请求的URI
	 * @param ip	域名或者IP地址
	 * @param port	端口,默认80可不填
	 * @param scheme	协议，http/https
	 * @param method	查询方式,支持Restful方式，GET/PUT/POST/DELETE
	 * @return com.easycms.common.domain.RespEntity
	 * @throws Exception
	 */
	public static RespEntity callRemoteServiceEntity(Map<String, String> parramMap, 
			String path,String ip,String port,String scheme,String method)
					throws Exception {
		return process(parramMap,null,null,path,ip,port,scheme,method,QUERY_PARAMS);
	}
	
	/**
	 * 发起HTTP请求
	 * @param parramMap	查询字符串数组
	 * @param headerMap	请求头字符串数组
	 * @param path	请求的URI
	 * @param ip	域名或者IP地址
	 * @param port	端口,默认80可不填
	 * @param scheme	协议，http/https
	 * @param method	查询方式,支持Restful方式，GET/PUT/POST/DELETE
	 */
	public static String callRemoteService(Map<String, String> parramMap, 
			Map<String, String> headerMap,String path,String ip,String port,String scheme,String method)
					throws Exception {
		return process(parramMap,headerMap,null,path,ip,port,scheme,method,QUERY_PARAMS).getData();
	}
	
	/**
	 * 发起HTTP请求
	 * @param parramMap	查询字符串数组
	 * @param headerMap	请求头字符串数组
	 * @param path	请求的URI
	 * @param ip	域名或者IP地址
	 * @param port	端口,默认80可不填
	 * @param scheme	协议，http/https
	 * @param method	查询方式,支持Restful方式，GET/PUT/POST/DELETE
	 * @return com.easycms.common.domain.RespEntity
	 * @throws Exception
	 */
	public static RespEntity callRemoteServiceEntity(Map<String, String> parramMap, 
			Map<String, String> headerMap,String path,String ip,String port,String scheme,String method)
					throws Exception {
		return process(parramMap,headerMap,null,path,ip,port,scheme,method,QUERY_PARAMS);
	}
	
	/**
	 * 支持PUT 和 POST方式
	 * @param jsonParams JSON字符串
	 * @param path	请求的URI 
	 * @param ip	域名或者IP地址
	 * @param port	端口,默认80可不填
	 * @param scheme	协议，http/https
	 * @param method	请求方式，只支持PUT和POST
	 * @return
	 * @throws Exception
	 */
	public static String callRemoteServiceByJson(String jsonParams, 
			String path,String ip,String port,String scheme,String method)
	throws Exception {
		return process(null,null,jsonParams,path,ip,port,scheme,method,JSON_PARAMS).getData();
	}
	
	/**
	 * 支持PUT 和 POST方式
	 * @param jsonParams JSON字符串
	 * @param path	请求的URI 
	 * @param ip	域名或者IP地址
	 * @param port	端口,默认80可不填
	 * @param scheme	协议，http/https
	 * @param method	请求方式，只支持PUT和POST
	 * @return com.easycms.common.domain.RespEntity
	 * @throws Exception
	 */
	public static RespEntity callRemoteServiceEntityByJson(String jsonParams, 
			String path,String ip,String port,String scheme,String method)
					throws Exception {
		return process(null,null,jsonParams,path,ip,port,scheme,method,JSON_PARAMS);
	}
	
	/**
	 * 
	 * @param jsonParams JSON字符串
	 * @param headerMap	请求头字符串数组
	 * @param path	请求的URI
	 * @param ip	域名或者IP地址
	 * @param port	端口,默认80可不填
	 * @param scheme	协议，http/https
	 * @param method	查询方式,支持Restful方式，PUT/POST
	 * @return 
	 * @throws Exception
	 */
	public static String callRemoteServiceByJson(String jsonParams, Map<String, String> headerMap,
			String path,String ip,String port,String scheme,String method)
					throws Exception {
		return process(null,headerMap,jsonParams,path,ip,port,scheme,method,JSON_PARAMS).getData();
	}
	
	/**
	 * 
	 * @param jsonParams JSON字符串
	 * @param headerMap	请求头字符串数组
	 * @param path	请求的URI
	 * @param ip	域名或者IP地址
	 * @param port	端口,默认80可不填
	 * @param scheme	协议，http/https
	 * @param method	查询方式,支持Restful方式，PUT/POST
	 * @return com.easycms.common.domain.RespEntity
	 * @throws Exception
	 */
	public static RespEntity callRemoteServiceEntityByJson(String jsonParams, Map<String, String> headerMap,
			String path,String ip,String port,String scheme,String method)
					throws Exception {
		return process(null,headerMap,jsonParams,path,ip,port,scheme,method,JSON_PARAMS);
	}
	
	/**
	 * @param parramMap
	 * @param jsonParams
	 * @param path
	 * @param ip
	 * @param port
	 * @param scheme
	 * @param method
	 * @param paramType
	 * @return
	 */
	private static RespEntity process(Map<String, String> parramMap,Map<String, String> headerMap, String jsonParams,
			String path,String ip,String port,String scheme,String method,Integer paramType) {
		log.info("------------------ HTTP REQUEST ----------------------");
		
		RespEntity respEntity = new RespEntity();
		HttpClient httpClient = createClient(scheme,port);
		String param = null;
		URI uri;
		HttpRequestBase submitMethod = null;
		String url = scheme;
		if(!CommonUtility.isNonEmpty(path)) path = "";
		if(CommonUtility.isNonEmpty(ip)) url += "://" + ip;
		if(CommonUtility.isNonEmpty(port)) {
			if(!"80".equals(port)) url += ":" + port;
		}
		if(!path.startsWith("/")) {
			url += "/";
		}
		url+= path;
		try {
			
			// #########################################################
			// 若是查询字符串
			// #########################################################
			if(QUERY_PARAMS == paramType) {
				//准备参数
				List<BasicNameValuePair> params = getParrams(parramMap);
				param = createParams(params, "utf-8");
				log.info("[method] ==> " + method);
				log.info("[URL] ==> " + url.toString());
				log.info("[param] ==> " + param);
				
				// 若是HTTP GET请求,直接拼装查询字符串
				if(GET_METHOD.equals(method)) {
					uri = new URI(url + "?" + param);
					
					HttpGet getMethod = new HttpGet(uri);
					submitMethod = getMethod;
				}
				
				// 若是HTTP DELETE请求,直接拼装查询字符串
				if(DELETE_METHOD.equals(method)) {
					uri = new URI(url + "?" + param);
					
					HttpDelete deleteMethod = new HttpDelete(uri);
					submitMethod = deleteMethod;
				}
				
				// 若是HTTP POST请求,在REQUEST BODY中封装参数
				if(POST_METHOD.equals(method)) {
					uri = new URI(url);
					HttpPost postMethod = new HttpPost(uri.toString());
					
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
					postMethod.setEntity(entity);
					submitMethod = postMethod;
				}
				
				// 若是HTTP PUT请求,在REQUEST BODY中封装参数
				if(PUT_METHOD.equals(method)) {
					uri = new URI(url);
					HttpPut putMethod = new HttpPut(uri.toString());
					
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
					putMethod.setEntity(entity);
					submitMethod = putMethod;
				}
			}
			
			// #########################################################
			// 若是JSON参数
			// #########################################################
			if(JSON_PARAMS == paramType) {
				//准备参数
				param = jsonParams;
				log.info("[method] ==> " + method);
				log.info("[URL] ==> " + url.toString());
				log.info("[param] ==> " + param);
				
				StringEntity entity = new StringEntity(param);
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json; charset=UTF-8");
				
				// 若是HTTP GET请求,直接拼装查询字符串
				if(GET_METHOD.equals(method)) {
					uri = new URI(url);
					
					HttpGet getMethod = new HttpGet(uri);
					submitMethod = getMethod;
				}
				
				// 若是HTTP POST请求,在REQUEST BODY中封装参数
				if(POST_METHOD.equals(method)) {
					uri = new URI(url);
					HttpPost postMethod = new HttpPost(uri.toString());
					
					postMethod.setEntity(entity);
					submitMethod = postMethod;
				}
				
				// 若是HTTP PUT请求,在REQUEST BODY中封装参数
				if(PUT_METHOD.equals(method)) {
					uri = new URI(url);
					HttpPut putMethod = new HttpPut(uri.toString());
					
					putMethod.setEntity(entity);
					submitMethod = putMethod;
				}
				
				// 若是HTTP DELETE请求
				if(DELETE_METHOD.equals(method)) {
					uri = new URI(url);
					HttpDelete deleteMethod = new HttpDelete(uri);
					
					submitMethod = deleteMethod;
				}
				
				submitMethod.addHeader("Content-Type", "application/json; charset=UTF-8");
				submitMethod.addHeader("Accept", "application/json, text/javascript");
			}
			
			
			// #########################################################
			// 设置请求头
			// #########################################################
			if(headerMap != null && headerMap.size() > 0) {
				 Iterator<Entry<String, String>> iter = headerMap.entrySet().iterator();
				 while(iter.hasNext()) {
					 Entry<String, String> entry = iter.next();
					 submitMethod.addHeader(entry.getKey(), entry.getValue());
					 log.info("[Header Parmas] ==> " + entry.getKey() + ":" + entry.getValue());
				 }
			}
			// #########################################################
			// 发起请求
			// #########################################################
//			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme)
			HttpResponse resp = httpClient.execute(submitMethod);
			StatusLine line = resp.getStatusLine();
			log.info("[status] ==> " + line.toString());
			log.info("[status code] ==> " + line.getStatusCode());
			
			// #########################################################
			// 返回状态码
			// #########################################################
//			if(returnStatusCode != null) {
//				returnStatusCode = line.getStatusCode();
//			}
			respEntity.setStatus(line.getStatusCode());
			respEntity.setData(IOUtils.toString(resp.getEntity().getContent()));
//			if (line.getStatusCode() == 200) {
//				xmlStr = EntityUtils.toString(resp.getEntity());
////				log.info(xmlStr);
//			}else{
//				xmlStr = IOUtils.toString(resp.getEntity().getContent());
//				log.warn(xmlStr);
//			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
//			submitMethod.abort();
			submitMethod.releaseConnection();
			httpClient.getConnectionManager().shutdown();
		}
		log.info("---------------------------------------------------");
		return respEntity;
	}
	
	private static HttpClient createClient(String scheme,String port) {
		HttpClient client = new DefaultHttpClient();
		if(HTTPS.equals(scheme)) {
//			SSLContext sslContext;
//		    try {
//		        sslContext = SSLContext.getInstance("SSL");
//		        // set up a TrustManager that trusts everything
//		        try {
//		            sslContext.init(null,
//		                    new TrustManager[] { new X509TrustManager() {
//								
//								@Override
//								public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//									// TODO Auto-generated method stub
//									return null;
//								}
//								
//								@Override
//								public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
//										String authType) throws CertificateException {
//									// TODO Auto-generated method stub
//									
//								}
//								
//								@Override
//								public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
//										String authType) throws CertificateException {
//									// TODO Auto-generated method stub
//									
//								}
//							}
//							}, new SecureRandom());
//		        } catch (KeyManagementException e) {
//		        }
//		        
//		         SSLSocketFactory ssf = new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//		         ClientConnectionManager ccm = client.getConnectionManager();
//		         SchemeRegistry sr = ccm.getSchemeRegistry();
//		         sr.register(new Scheme("https", Integer.parseInt(port), ssf));            
//		    } catch (Exception e) {
//		        log.error(e.getMessage(),e);
//		    }
			try {
				SSLContext ctx = SSLContext.getInstance("TLS");
				X509TrustManager tm = new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
					public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
				};
				ctx.init(null, new TrustManager[] { tm }, null);
				SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				SchemeRegistry registry = new SchemeRegistry();
				registry.register(new Scheme("https", 443, ssf));
				PoolingClientConnectionManager  mgr = new PoolingClientConnectionManager(registry);
				return new DefaultHttpClient(mgr, client.getParams());
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
		
		
		return client;
	}
	
	
	/**
	 * put key and value in url parrams.
	 * 
	 * @param parramMap
	 * @return
	 */
	private static List<BasicNameValuePair> getParrams(
			Map<String, String> parramMap) {
		List<BasicNameValuePair> parrams = new ArrayList<BasicNameValuePair>();
		if (parramMap != null && !parramMap.isEmpty()) {
			Set<Entry<String, String>> entrySet = parramMap.entrySet();
			Iterator<Entry<String, String>> iter = entrySet.iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				String key = entry.getKey();
				String value = entry.getValue();
				
				if(CommonUtility.isNonEmpty(value)) {
					//如果是","分割
					String[] values = {};
					//检测是否是json格式
					if(!value.startsWith("[{")){
						values = value.split(",");
					}
					if(values.length > 1) {
						for(String val : values) {
							parrams.add(new BasicNameValuePair(key, val));
						}
					}else{
						parrams.add(new BasicNameValuePair(key, value));
					}
				}
			}
		}
		return parrams;
	}
	public static void setRequestHeader(String key,String value){
	}
	private static String createParams(List<BasicNameValuePair> parrams,String encode) {
		StringBuffer sb = new StringBuffer();
		try {
			for (BasicNameValuePair vp : parrams) {
				String key = vp.getName();
				String value = vp.getValue();
				String entry;
				if(CommonUtility.isNonEmpty(value)) {
					entry = key + "="
					+ URLEncoder.encode(value, encode).toString();
				}else{
					entry = key + "=";
				}
				sb.append(entry + "&");
			}
			if(sb.length() > 0) {
				sb.deleteCharAt(sb.length() -1);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
}
