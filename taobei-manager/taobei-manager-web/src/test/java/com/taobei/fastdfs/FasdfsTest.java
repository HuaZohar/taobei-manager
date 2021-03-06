package com.taobei.fastdfs;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class FasdfsTest {
	
	@Test
	public void testUpload() throws FileNotFoundException, IOException, MyException{
		ClientGlobal.init("G:\\java\\taobei_pro\\taobei-manager\\taobei-manager-web\\src\\main\\resources\\properties\\client.conf");
		
		
		TrackerClient trackerClient = new TrackerClient();
		
		TrackerServer trackerServer = trackerClient.getConnection();
		
		StorageServer storageServer = null;
		
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		
		String strs[] = storageClient.upload_file("C:\\Users\\Administrator\\Pictures\\3.png", "png", null);
		
		for(String str :strs){
			System.out.println(str);
		}
		
		
	}
}
