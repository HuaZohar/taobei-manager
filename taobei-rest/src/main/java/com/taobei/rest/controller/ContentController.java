package com.taobei.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobei.common.pojo.TaobeiResult;
import com.taobei.common.utils.ExceptionUtil;
import com.taobei.pojo.TbContent;
import com.taobei.rest.service.ContentService;

@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/{cid}")
	@ResponseBody
	public TaobeiResult getContentList(@PathVariable Long cid){
		try {
			List<TbContent> list = contentService.getContentList(cid);
			return TaobeiResult.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			return TaobeiResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	/**
	 * 同步内容
	 * @param cid
	 * @return
	 */
	@RequestMapping("/sync/content/{cid}")
	@ResponseBody
	public TaobeiResult syncContent(@PathVariable Long cid){
		
		try {
			TaobeiResult result = contentService.syncContent(cid);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaobeiResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
