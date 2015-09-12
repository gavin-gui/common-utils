package com.easycms.common.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class ZIPUtil {

	private static final Logger logger = Logger.getLogger(ZIPUtil.class);

	
	public static void extract(String from, String desc) {
		String encoding = "utf-8";
		 if(System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0){  
			 encoding = "gbk";
         } 
		extract(new File(from), desc,encoding);
	}
	public static void extract(String from, String desc,String encoding) {
		extract(new File(from), desc,encoding);
	}

	@SuppressWarnings("rawtypes")
	public static void extract(File zipfile, String descDir,String encoding) {
		logger.debug("[descDir] = " + descDir);
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		BufferedInputStream bin = null;
		BufferedOutputStream bout = null;
		ZipFile zip = null;
		try {
			logger.debug("==> encoding:" + encoding);
			zip = new ZipFile(zipfile,encoding);
			Enumeration en = zip.getEntries();
			while (en.hasMoreElements()) {
				 ZipEntry entry = (ZipEntry) en.nextElement();

				// 输出文件路径信息
				String zipEntryName = entry.getName();
				logger.debug("[zipEntryName] = " + zipEntryName);
				
				File f = new File(descDir,zipEntryName);

				//判断是否是目录
				if(entry.isDirectory()) {
					f.mkdirs();
					continue;
				}
				
				// 判断路径是否存在,不存在则创建文件路径
				if(!f.exists()) {
					new File(f.getParent()).mkdirs();
				}
				
				//写文件
				bin = new BufferedInputStream(zip.getInputStream(entry));
                bout = new BufferedOutputStream(new FileOutputStream(f));  
                int b;  
                while((b=bin.read())!=-1){  
                	bout.write(b);  
                }  
                
                bout.flush();
                bout.close();
                bin.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(zip != null) {
				try {
					zip.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		logger.debug("****************** Extract complete ********************");
	}
	
	
	/**
	 * 压缩文件-由于out要在递归调用外,所以封装一个方法用来
	 * 调用ZipFiles(ZipOutputStream out,String path,File... srcFiles)
	 * @param zip
	 * @param path
	 * @param srcFiles
	 * @throws IOException
	 * @author isea533
	 */
	public static void compress(File zip,String path,File... srcFiles) throws IOException{
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
		compress(out,path,srcFiles);
		out.close();
		logger.debug("***************** Compress complete ******************");
	}
	/**
	 * 压缩文件-File
	 * @param zipFile  zip文件
	 * @param srcFiles 被压缩源文件
	 * @author isea533
	 */
	public static void compress(ZipOutputStream out,String path,File... srcFiles){
		path = path.replaceAll("\\*", "/");
		if(!path.endsWith("/")){
			path+="/";
		}
		byte[] buf = new byte[1024];
		try {
			for(int i=0;i<srcFiles.length;i++){
				if(srcFiles[i].isDirectory()){
					File[] files = srcFiles[i].listFiles();
					String srcPath = srcFiles[i].getName();
					srcPath = srcPath.replaceAll("\\*", "/");
					if(!srcPath.endsWith("/")){
						srcPath+="/";
					}
					out.putNextEntry(new ZipEntry(path+srcPath));
					compress(out,path+srcPath,files);
				}
				else{
					FileInputStream in = new FileInputStream(srcFiles[i]);
					logger.debug(path + srcFiles[i].getName());
					out.putNextEntry(new ZipEntry(path + srcFiles[i].getName()));
					int len;
					while((len=in.read(buf))>0){
						out.write(buf,0,len);
					}
					out.closeEntry();
					in.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}