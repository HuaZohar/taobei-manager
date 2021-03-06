package com.taobei.rest.service;

import com.taobei.pojo.TbItem;
import com.taobei.pojo.TbItemDesc;
import com.taobei.pojo.TbItemParamItem;

public interface ItemService {
	TbItem getItemById(Long id) throws Exception;
	TbItemDesc getItemDescById(Long id) throws Exception;
	TbItemParamItem getItemParamById(Long id) throws Exception;
}
