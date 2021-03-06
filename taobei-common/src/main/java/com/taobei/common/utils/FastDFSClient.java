package com.taobei.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * FDS上传工具类
 * @author Administrator
 *
 */
public class FastDFSClient {
	private TrackerClient trackerClient = null;
	private TrackerServer trackerServer = null;
	private StorageClient storageClient = null;
	private StorageServer storageServer = null;
	
	
	public FastDFSClient(String conf) throws FileNotFoundException, IOException, MyException {
		if(conf.contains("classpath:")){
			conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
		}
		
		ClientGlobal.init(conf);
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageClient = new StorageClient(trackerServer, storageServer);
	}
	
	
	public String uploadFile(String fileName,String extName,NameValuePair[] metas) 
			throws Exception{
		String res[] = storageClient.upload_file(fileName,extName,metas);
		String result = res[0] + "/" + res[1];
		return result;
	}
	
	
	public String uploadFile(String fileName) throws Exception{
		return uploadFile(fileName,null,null);
	}
	
	public String uploadFile(String fileName,String extName) throws Exception{
		return uploadFile(fileName,extName,null);
	}
	
	
	//**********************************
	public String uploadFile(byte[] fileContent,String extName,NameValuePair[] metas) throws IOException, MyException{
		String res[] = storageClient.upload_file(fileContent, extName, metas);
		String result = res[0] + "/" + res[1];
		return result;
	}
	
	public String uploadFile(byte[] fileContent) throws IOException, MyException{
		return uploadFile(fileContent,null,null);
	}
	
	public String uploadFile(byte[] fileContent,String extName) throws IOException, MyException{
		return uploadFile(fileContent,extName,null);
	}
	
	
	
	
}
