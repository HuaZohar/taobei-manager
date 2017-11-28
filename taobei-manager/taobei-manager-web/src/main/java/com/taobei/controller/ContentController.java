package com.taobei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobei.common.pojo.EasyUIDataGridResult;
import com.taobei.common.pojo.TaobeiResult;
import com.taobei.common.utils.HttpClientUtil;
import com.taobei.pojo.TbContent;
import com.taobei.service.ContentService;

@RequestMapping("/content")
@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	@RequestMapping("/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(Long categoryId,int page,int rows)
			throws Exception{
		return contentService.getContentList(categoryId, page, rows);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaobeiResult insertContent(TbContent content) 
			throws Exception{
		TaobeiResult result = contentService.insertContent(content);
		
		//发布请求清空缓存  zzh171118
		HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaobeiResult deleteContent(String ids) throws Exception{
		//要把删除逻辑放在controller中
		if(ids!=null&&!"".equals(ids)){
			String idArray[] = ids.split(",");
			for(String id :idArray){
				TbContent content = contentService.getContentById(Long.parseLong(id));
				//发布请求清空缓存  zzh171118
				HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
				//删除内容
				contentService.deleteContent(Long.parseLong(id));
			}
		}
		return TaobeiResult.ok();
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public TaobeiResult updateContent(TbContent content)  throws Exception{
		TaobeiResult result = contentService.updateContent(content);
		
		//发布请求清空缓存  zzh171118
		HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		
		return result;
	}
	
	
}
