package com.taobei.service.impl;

import java.io.IOException;

import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taobei.common.pojo.PictureResult;
import com.taobei.common.utils.FastDFSClient;
import com.taobei.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {

	@Value("${IMAGE_SERVER_BASE_URL}")
	private String IMAGE_SERVER_BASE_URL;
	
	@Override
	public PictureResult uploadPic(MultipartFile multipartFile) {
		PictureResult pictureResult = new PictureResult();
		
		if(multipartFile.isEmpty()){
			pictureResult.setError(1);
			pictureResult.setMessage("图片为空");
			return pictureResult;
		}
		
		try {
			//获取后缀名
			String originalFileName = multipartFile.getOriginalFilename();
			String extName = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
			
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:properties/client.conf");
			String url = fastDFSClient.uploadFile(multipartFile.getBytes(), extName);
			
			url = IMAGE_SERVER_BASE_URL + url;
			//url响应给客户端
			pictureResult.setError(0);
			pictureResult.setUrl(url);
			
		} catch (IOException | MyException e) {
			e.printStackTrace();
			pictureResult.setError(1);
			pictureResult.setMessage("图片上传失败");
		}
		
		return pictureResult;
	}

}
