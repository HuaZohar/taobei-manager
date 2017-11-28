package com.taobei.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.taobei.search.pojo.SearchResult;

public interface SearchDao {
	SearchResult search(SolrQuery query) throws Exception;
}
