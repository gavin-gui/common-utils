package com.easycms.common.upload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.easycms.common.util.CommonUtility;

public class UploadUtil {

	private static final Logger logger = Logger.getLogger(UploadUtil.class);
	private static final String defaultEncode = "utf-8";
//	public static final int RELATIVE_PATH = 0;
//	public static final int ABSOLUTE_PATH = 1;
	

	/**
	 * 上传文件
	 * 
	 * @param relativeDir	相对工程路径，若不存在会创建目录
	 * @param encode	编码
	 * @param rename	是否重命名
	 * @param request	HttpServletRequest对象
	 * @param pathType  上传文件路径是相对路径还是绝对路径,RELATIVE_PATH/ABSOLUTE_PATH
	 *            
	 * @return Map<String,String> 包含文件原始完整名称和路径的集合
	 */
	public static List<FileInfo> upload(String relativeDir, String encode,boolean rename,
			HttpServletRequest request) {
		List<FileInfo> fileInfoList = null;

		fileInfoList = executeUpload(relativeDir,true, encode,rename, request,fileInfoList);

		return fileInfoList;
	}
	
	/**
	 * 上传文件
	 * 不会重命名文件
	 * @param relativeDir	相对工程路径，若不存在会创建目录
	 * @param encode	编码
	 * @param request	HttpServletRequest对象
	 *            
	 * @return Map<String,String> 包含文件原始完整名称和路径的集合
	 */
	public static List<FileInfo> upload(String relativeDir, String encode,
			HttpServletRequest request) {
		List<FileInfo> fileInfoList = null;
		
		fileInfoList = executeUpload(relativeDir,true, encode,false, request,
				fileInfoList);
		
		return fileInfoList;
	}
	
	/**
	 * 上传文件
	 * 不会重命名文件
	 * @param absoluteDir	绝对路径，若不存在会创建目录
	 * @param encode	编码
	 * @param request	HttpServletRequest对象
	 *            
	 * @return Map<String,String> 包含文件原始完整名称和路径的集合
	 */
	public static List<FileInfo> uploadAbsolute(String absoluteDir, String encode,
			HttpServletRequest request) {
		List<FileInfo> fileInfoList = null;
		
		fileInfoList = executeUpload(absoluteDir,false, encode,false, request,
				fileInfoList);
		
		return fileInfoList;
	}
	
	/**
	 * 上传文件
	 * 
	 * @param absoluteDir	绝对路径，若不存在会创建目录
	 * @param encode	编码
	 * @param rename	是否重命名
	 * @param request	HttpServletRequest对象
	 * @param pathType  上传文件路径是相对路径还是绝对路径,RELATIVE_PATH/ABSOLUTE_PATH
	 *            
	 * @return Map<String,String> 包含文件原始完整名称和路径的集合
	 */
	public static List<FileInfo> uploadAbsolute(String absoluteDir, String encode,boolean rename,
			HttpServletRequest request) {
		List<FileInfo> fileInfoList = null;

		fileInfoList = executeUpload(absoluteDir,false, encode,rename, request,fileInfoList);

		return fileInfoList;
	}

	/**
	 * 上传文件并返回一个MAP包含文件名和流信息
	 * @param encode
	 * @param request
	 * @return
	 */
	public static Map<String, InputStream> upload(String encode,
			HttpServletRequest request) {
		// #########################################################
		// 执行上传
		// #########################################################
		Map<String,InputStream> map = new HashMap<String, InputStream>();
		try {
			CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());

			// 若不是一个包含上传文件的request，则返回错误
			if (!commonsMultipartResolver.isMultipart(request)) {
				logger.error("==> Not a multi request,upload file is not in this request");
				return null;
			}

			// 开始上传

			if (!CommonUtility.isNonEmpty(encode))
				encode = defaultEncode;
			commonsMultipartResolver.setDefaultEncoding(encode);

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multipartRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile multiFile = multipartRequest
						.getFile((String) iter.next());
				if (multiFile != null) {

					String orignFilename = multiFile.getOriginalFilename();
					map.put(orignFilename, multiFile.getInputStream());
				}
			}
			return map;
		} catch (UnsupportedEncodingException e) {
			logger.error("upload(HttpServletRequest, HttpServletResponse)", e);

			// e.printStackTrace();
		} catch (IllegalStateException e) {
			logger.error("upload(HttpServletRequest, HttpServletResponse)", e);

			// e.printStackTrace();
		} catch (IOException e) {
			logger.error("upload(HttpServletRequest, HttpServletResponse)", e);
			if ("Stream ended unexpectedly".equals(e.getCause())) {
				logger.error("File upload is canceled");
			} else {
				// e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * @param targetDir
	 * @param relative
	 * @param encode
	 * @param request
	 * @param pathType
	 * @param fileInfoList
	 * @return
	 */
	private static List<FileInfo> executeUpload(String targetDir,boolean relative, String encode,boolean rename,
			HttpServletRequest request,
			List<FileInfo> fileInfoList) {
		// #########################################################
		// 检查目标目录是否为空
		// #########################################################
		if (!CommonUtility.isNonEmpty(targetDir)) {
			logger.error("==> Target dir can not null,targetDir =" + targetDir);
			return fileInfoList;
		}
		
		File dir = null;
		if(relative) {
			dir = new File(request.getServletContext().getRealPath(""),targetDir);
		}else{
			dir = new File(targetDir);
		}
//		if(RELATIVE_PATH == pathType) {
//		}else if(ABSOLUTE_PATH == pathType) {
//			dir = new File(targetDir);
//		}else{
//			dir = new File(targetDir);
//		}
		// #########################################################
		// 检查目标目录是否存在，如不存在则建立
		// #########################################################
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		// #########################################################
		// 检查目标目录是否是一个目录
		// #########################################################
		if (!dir.isDirectory()) {
			logger.error("==> Target dir must be a direct,targetDir =" + targetDir);
			return fileInfoList;
		}

		// #########################################################
		// 执行上传
		// #########################################################
		try {
			CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());

			// 若不是一个包含上传文件的request，则返回错误
			if (!commonsMultipartResolver.isMultipart(request)) {
				logger.error("==> Not a multi request,upload file is not in this request");
				return fileInfoList;
			}

			fileInfoList = new ArrayList<FileInfo>();

			// 开始上传

			if (!CommonUtility.isNonEmpty(encode))
				encode = defaultEncode;
			commonsMultipartResolver.setDefaultEncoding(encode);

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multipartRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile multiFile = multipartRequest
						.getFile((String) iter.next());
				if (multiFile != null) {
					FileInfo fileInfo = new FileInfo();
					
					String orignFilename = multiFile.getOriginalFilename();
					
					logger.debug("[orignFilename] = " + orignFilename);
					
					//获取相对站点路径
					String ralativePath = CommonUtility.assemblePath(targetDir,orignFilename);
					logger.debug("[ralativePath] = " + ralativePath);
					
					// 重命名文件
					String ext = "";
					String filename = "";
					if (orignFilename.lastIndexOf(".") != -1) {
						filename = orignFilename.substring(0,orignFilename.lastIndexOf("."));
						ext = orignFilename.substring(orignFilename.lastIndexOf(".") + 1,
								orignFilename.length());
					}else{
						filename = orignFilename;
					}
					logger.debug("[filename] = " + filename);
					
					String newFilename = "";
					if(rename) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
						String suffix = sdf.format(new Date());
						if(CommonUtility.isNonEmpty(ext)) {
							newFilename += "_" + suffix + "." + ext;
						}else{
							newFilename += "_" + suffix;
						}
					}else{
						newFilename = orignFilename;
					}
					newFilename = CommonUtility.removeBlank(newFilename, "_");
					logger.debug("[new filename] = " + newFilename);
					
					String realRelativePath = CommonUtility.assemblePath(targetDir,newFilename);
					logger.debug("[realRelativePath] = " + realRelativePath);
					
					String absolutPath = CommonUtility.assemblePath(dir.getAbsolutePath(),newFilename);
					logger.debug("[absolutPath] = " + absolutPath);
					
					//写入文件
					File file = new File(absolutPath);
					if(file.exists()) {
						logger.debug("==> File["+file.getName()+"] is exist and delete it.");
						logger.debug("==> File["+file.getName()+"] delete " + file.delete());
					}
					multiFile.transferTo(file);
//					InputStream in = multiFile.getInputStream();
//					OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
//					IOUtils.copy(in, out);
//					IOUtils.closeQuietly(in);
//					IOUtils.closeQuietly(out);
					
					//设置返回信息
					fileInfo.setFilename(filename);
					fileInfo.setNewFilename(newFilename);
					fileInfo.setRelativePath(ralativePath);
					fileInfo.setRealRelativePath(realRelativePath);
					fileInfo.setAbsolutePath(absolutPath);
					fileInfo.setSize(String.valueOf(file.length()));
					fileInfo.setType(ext);
					
					fileInfoList.add(fileInfo);
				}
			}
		}catch (UnsupportedEncodingException e) {
			logger.error("upload(HttpServletRequest, HttpServletResponse)", e);

//			e.printStackTrace();
		} catch (IllegalStateException e) {
			logger.error("upload(HttpServletRequest, HttpServletResponse)", e);

//			e.printStackTrace();
		} catch (IOException e) {
			logger.error("upload(HttpServletRequest, HttpServletResponse)", e);
			if("Stream ended unexpectedly".equals(e.getCause())) {
				logger.error("File upload is canceled");
			}else{
//				e.printStackTrace();
			}
		}
		return fileInfoList;
	}
	
	/**
	 * 文件下载
	 * @param path
	 * @param encode
	 * @param response
	 */
	public static boolean download(String absolutePath,String encode,HttpServletResponse response) {
		// #########################################################
		// 检查下载地址是否为空
		// #########################################################
		if (!CommonUtility.isNonEmpty(absolutePath)) {
			logger.error("==> Download path can not be null,path =" + absolutePath);
			return false;
		}


		File file = new File(absolutePath);
		// #########################################################
		// 检查目标目录是否存在，如不存在则建立
		// #########################################################
		if (!file.exists()) {
			logger.error("==> Download path is not exist,path =" + absolutePath);
			return false;
		}
		
		// #########################################################
		// 检查下载地址是否是一个文件
		// #########################################################
		if (!file.isFile()) {
			logger.error("==> Download path is not a file type,path =" + absolutePath);
			return false;
		}
		
		// #########################################################
		// 检查编码
		// #########################################################
		if(!CommonUtility.isNonEmpty(encode)) {
			encode = defaultEncode;
		}
		
		// #########################################################
		// 执行下载
		// #########################################################
		InputStream in = null;
		OutputStream out = null;
		Writer  writer = null;
		try {
			//写入头信息
			response.setContentType("application/x-msdownload");
//			response.setContentType("application/vnd.ms-excel;charset=" + encode);
			String filenameSrc = file.getName();
//			String filename = URLEncoder.encode(filenameSrc,"UTF-8");
//			if(filename.length()>150)//解决IE 6.0 bug
			String	filename = new String(filenameSrc.getBytes("utf-8"),"ISO-8859-1");
			response.setHeader( "Content-Disposition", "attachment;filename="  + filename);
//			response.setHeader("Content-Disposition", "attachment; filename=" + new String(.getBytes("iso-8859-1"),encode));
			response.addHeader("Content-Length", "" + file.length()); 
			
			//读流
			FileInputStream fin = new FileInputStream(file);
//			BufferedReader reader = new BufferedReader(new InputStreamReader(fin, encode));
			in = new BufferedInputStream(fin);
			
			//写流
//			writer  = response.getWriter();
			out  = response.getOutputStream();
			
			IOUtils.copy(in, out);
			
//			byte[] buff = new byte[2048];  
//	        int bytesRead;  
//	  
//	        while(-1 != (bytesRead = reader.read(buff, 0, buff.length))) {  
//	        	out.write(buff,0,bytesRead);  
//	        }  
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(writer);
		}
		return true;
	}
	
	public static boolean remove(String path) {
		File File = new File(path);
		return File.delete();
	}
}
