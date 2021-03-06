package com.taobei.service;

import com.taobei.common.pojo.EasyUIDataGridResult;
import com.taobei.common.pojo.TaobeiResult;

public interface ItemParamService {
	EasyUIDataGridResult getItemParamList(int page,int rows) throws Exception;
	
	TaobeiResult getItemParamByCid(Long cid)  throws Exception;
	
	TaobeiResult insertItemParam(Long cid,String paramData) throws Exception;
	
	TaobeiResult deleteItemParam(Long id) throws Exception;
}
