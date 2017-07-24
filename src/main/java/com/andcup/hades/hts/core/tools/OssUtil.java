package com.andcup.hades.hts.core.tools;

import java.io.File;

import com.aliyun.oss.model.PutObjectResult;
import com.andcup.hades.hts.core.model.OSS;
import com.aliyun.oss.OSSClient;
import com.andcup.hades.httpserver.utils.LogUtils;

/**
 * OSS(文件存储)工具包
 * @author chenwj
 * @Copyright 游龙网络
 * @Version v1.0
 * @date 2017年6月14日 下午1:44:14
 */
public class OssUtil {
	

	/**
	 * 获取阿里云OSS客户端对象
	 * @author chenwj
	 * @ModifiedBy chenwj(2017年6月14日 下午1:44:39)
	 * @date 2017年6月14日 下午1:44:39
	 * @return
	 */
	public static final OSSClient getOSSClient(String endpoint, String accessKeyId, String accessKeySecret) {
		
		return new OSSClient(endpoint, accessKeyId, accessKeySecret);
	}
	
	/**
	 * 上传Object
	 * @author chenwj
	 * @ModifiedBy chenwj(2017年6月14日 上午11:38:02)
	 * @date 2017年6月14日 上午11:38:02
	 * @param uploadFilePath	：文件保存路径(不允许null)，如：aaa/bbb/ccc/xxx.jpg
	 * @return url=aaa/bbb/ccc/test_xxx.jpg
	 */
	public static final boolean uploadFile(OSS oss, String localpath, String uploadFilePath) throws Exception{
		
		OSSClient client = getOSSClient(oss.endpoint, oss.accessKeyId, oss.accessKeySecret);
		String bucketName = oss.bucketName;
		
		//根据规则获取key
		String ossKey = uploadFilePath;
		if(uploadFilePath.startsWith("/")) 
			ossKey = uploadFilePath.substring(1); //文件保存路径开头是 / 则去掉，改成：aaa/bbb/ccc/xxx.jpg

		//上传object
		client.putObject(bucketName, ossKey, new File(localpath));
		return true;
	}
	
}