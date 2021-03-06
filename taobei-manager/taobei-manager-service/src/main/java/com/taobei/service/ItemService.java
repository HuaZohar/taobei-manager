package com.taobei.service;

import com.taobei.common.pojo.EasyUIDataGridResult;
import com.taobei.common.pojo.TaobeiResult;
import com.taobei.pojo.TbItem;

public interface ItemService {
	
	TbItem getItemById(Long itemId) throws Exception;
	
	EasyUIDataGridResult getItemList(int page,int rows) throws Exception;
	
	TaobeiResult CreateItem(TbItem tbItem, String desc, String itemParams) throws Exception;
	
	String getItemParamHtml(Long itemId) throws Exception;
	
}
