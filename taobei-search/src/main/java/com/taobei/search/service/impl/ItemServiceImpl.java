package com.taobei.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobei.common.pojo.TaobeiResult;
import com.taobei.search.mapper.ItemMapper;
import com.taobei.search.pojo.SearchItem;
import com.taobei.search.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private SolrServer solrServer;
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Override
	public TaobeiResult importItems() throws Exception {
		List<SearchItem> items = itemMapper.getItemList();
		for(SearchItem item :items){
			SolrInputDocument document = new SolrInputDocument();
			document.setField("id", item.getId());
			document.setField("item_title", item.getTitle());
			document.setField("item_sell_point", item.getSell_point());
			document.setField("item_price", item.getPrice());
			document.setField("item_image", item.getImages());
			document.setField("item_category_name", item.getCategory_name());
			document.setField("item_desc", item.getDesc());
			solrServer.add(document);
		}
		
		solrServer.commit();
		return TaobeiResult.ok();
	}

}
