package com.taobei.rest.service;

import java.util.List;

import com.taobei.common.pojo.TaobeiResult;
import com.taobei.pojo.TbContent;

public interface ContentService {

	List<TbContent> getContentList(Long cid) throws Exception;
	TaobeiResult syncContent(Long cid) throws Exception;
}
