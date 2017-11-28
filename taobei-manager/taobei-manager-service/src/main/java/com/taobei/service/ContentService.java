package com.taobei.service;


import com.taobei.common.pojo.EasyUIDataGridResult;
import com.taobei.common.pojo.TaobeiResult;
import com.taobei.pojo.TbContent;
import com.taobei.pojo.TbItem;

public interface ContentService {

	EasyUIDataGridResult getContentList(Long categoryId,int page, int rows) throws Exception;
	
	TaobeiResult insertContent(TbContent content) throws Exception;
	
	TaobeiResult deleteContent(Long id) throws Exception;
	
	TaobeiResult updateContent(TbContent content) throws Exception;
	
	TbContent getContentById(Long contentId) throws Exception;
}
