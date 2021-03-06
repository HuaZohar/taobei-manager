package com.taobei.service;

import java.util.List;

import com.taobei.common.pojo.EasyUITreeNode;
import com.taobei.common.pojo.TaobeiResult;

public interface ContentCatgoryService {
	List<EasyUITreeNode> getContentCatList(Long parentId) throws Exception;
	
	TaobeiResult insertCategory(Long parentId,String name) throws Exception;
	
	TaobeiResult updateCategory(Long id,String name) throws Exception;
	
	TaobeiResult deleteCategory(Long id) throws Exception;
}
