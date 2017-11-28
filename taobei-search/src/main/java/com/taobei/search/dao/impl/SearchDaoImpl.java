package com.taobei.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taobei.search.dao.SearchDao;
import com.taobei.search.pojo.SearchItem;
import com.taobei.search.pojo.SearchResult;

/**
 * solr查询DAO 
 * @author Administrator
 *
 */
@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SolrServer solrServer;
	
	@Override
	public SearchResult search(SolrQuery query) throws Exception {
		QueryResponse response = solrServer.query(query);
		
		//取查询结果列表
		SolrDocumentList documentList = response.getResults();
		
		List<SearchItem> searchItems = new ArrayList<>();
		
		for(SolrDocument document :documentList){
			SearchItem item = new SearchItem();
			item.setCategory_name((String) document.get("item_category_name"));
			item.setId((String) document.get("id"));
			item.setImages((String) document.get("item_image"));
			item.setPrice((Long) document.get("item_price"));
			item.setSell_point((String) document.get("item_sell_point"));
			
			//取高亮显示 ☆☆☆☆☆☆☆
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(document.get("id")).get("item_title");
			String itemTitle = null;
			if(list!=null&& list.size() > 0){
				itemTitle = list.get(0);
			}else{
				itemTitle = (String) document.get("item_title");
			}
			item.setTitle(itemTitle);
			
			
			item.setDesc((String) document.get("item_desc"));
			searchItems.add(item);
		}
		
		SearchResult result = new SearchResult();
		result.setSearchItems(searchItems);
		result.setRecordCount(documentList.getNumFound());
				
		return result;
	}

}
