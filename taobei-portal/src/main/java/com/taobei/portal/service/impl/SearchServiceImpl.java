package com.taobei.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobei.common.pojo.TaobeiResult;
import com.taobei.common.utils.HttpClientUtil;
import com.taobei.common.utils.JsonUtils;
import com.taobei.portal.pojo.SearchResult;
import com.taobei.portal.service.SearchService;

/**
 * 搜索service
 * @author Administrator
 *
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	@Override
	public SearchResult search(String keyword, int page, int rows) throws Exception {
		Map<String, String> param = new HashMap<>();
		param.put("keyword", keyword);
		param.put("page", page+"");
		param.put("rows", rows+"");
		String json = HttpClientUtil.doGet(SEARCH_BASE_URL,param);
		
		//转换为java对象
		TaobeiResult taobeiResult = TaobeiResult.formatToPojo(json, SearchResult.class);
		SearchResult searchResult =  (SearchResult) taobeiResult.getData();
		
		return searchResult;
	}

}
