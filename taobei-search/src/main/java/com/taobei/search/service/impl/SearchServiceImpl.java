package com.taobei.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobei.search.dao.SearchDao;
import com.taobei.search.pojo.SearchResult;
import com.taobei.search.service.SearchService;

/**
 * solr查询service
 *
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;
	
	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		//创建查询条件对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery(queryString);
		//设置分页条件
		query.setStart(page);
		query.setRows(rows);
		//设置默认搜索域
		query.set("df", "item_title");
		//设置高亮
		query.setHighlight(true);
		//设置高亮显示的域
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
		query.setHighlightSimplePost("</font>");
		//执行查询
		SearchResult result = searchDao.search(query);
		
		//计算总页数
		Long recordCount = result.getRecordCount();
		int pageCount = (int) (recordCount / rows);
		if(recordCount % rows > 0){
			pageCount++;
		}
		
		result.setPageCount(pageCount);
		result.setCurPage(page);
		return result;
	}

}
