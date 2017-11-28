package com.taobei.portal.service;

import com.taobei.portal.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String keyword,int page,int rows) throws Exception;
}
