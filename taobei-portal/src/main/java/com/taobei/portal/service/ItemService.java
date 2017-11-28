package com.taobei.portal.service;

import com.taobei.pojo.TbItem;

public interface ItemService {
	TbItem getItemById(Long itemId) throws Exception;
	String getItemDescById(Long itemId) throws Exception;
	String getItemParamById(Long itemId) throws Exception;
}
