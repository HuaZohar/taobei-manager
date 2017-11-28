package com.taobei.search.service;

import com.taobei.search.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String queryString,int page,int rows) throws Exception;
}
