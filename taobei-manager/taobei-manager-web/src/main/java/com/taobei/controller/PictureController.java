package com.taobei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taobei.common.pojo.PictureResult;
import com.taobei.common.utils.JsonUtil;
import com.taobei.service.PictureService;

@Controller
public class PictureController {
	
	@Autowired
	private PictureService pictureService;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile){
		PictureResult pictureResult = pictureService.uploadPic(uploadFile);
		String result = JsonUtil.ObjectToJson(pictureResult);
 		return result;
	}
	
}
