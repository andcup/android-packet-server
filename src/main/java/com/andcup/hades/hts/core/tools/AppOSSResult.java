package com.andcup.hades.hts.core.tools;

import java.io.Serializable;

/**
 * 文件存储OSS-文件上传到OSS后的相关信息
 * @author chenwj
 * @Copyright 农村小卖部
 * @Version v1.0
 * @date 2017年2月6日 上午10:40:42
 */
public class AppOSSResult implements Serializable {
	
	private static final long serialVersionUID = 8507981737043684554L;
	
	private long size;			//文件大小
	private String md5;			//文件的md5值
	private String sha;			//文件的sha值
	private String type;		//文件类型
	private String bucket;		//文件在oss上存放的bucket
	private String folder;		//文件在oss上存放的文件夹位置
	private String fileName;	//文件在oss上的文件名
	private String key;			//文件在oss上的key
	private String url;			//文件在oss上的访问路径

	/** 设置文件相关的信息 */
	public void setFileInfo(long size, String md5, String sha, String type) {
		this.size = size;
		this.md5 = md5;
		this.sha = sha;
		this.type = type;
	}
	
	/** 设置oss相关的信息 */
	public void setOssInfo(String bucket, String folder, String fileName, String key, String url) {
		this.bucket = bucket;
		this.folder = folder;
		this.fileName = fileName;
		this.key = key;
		this.url = url;
	}
	
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getSha() {
		return sha;
	}
	public void setSha(String sha) {
		this.sha = sha;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}