package com.easycms.common.upload;

import java.io.Serializable;

public class FileInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1309929409013846568L;
	private String filename;	//文件名称不含后缀
	private String relativePath;	//文档相对路径
	private String absolutePath;	//文档绝对路径
	private String realRelativePath;	//文档绝对路径
	private String size;	//文档大小
	private String type;	//文档类型
	private String newFilename;	//文档类型
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getNewFilename() {
		return newFilename;
	}
	public void setNewFilename(String newFilename) {
		this.newFilename = newFilename;
	}
	public String getRealRelativePath() {
		return realRelativePath;
	}
	public void setRealRelativePath(String realRelativePath) {
		this.realRelativePath = realRelativePath;
	}

}
