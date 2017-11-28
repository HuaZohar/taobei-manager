package com.taobei.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {
	
	@Test
	public void testSolrJ() throws Exception, IOException{
		SolrServer solrServer = new HttpSolrServer("http://192.168.255.131:8080/solr");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "solrtest01");
		document.addField("item_title", "测试商品");
		document.addField("item_sell_point", "卖点");
		
		solrServer.add(document);
		
		solrServer.commit();
		
	}
	
	@Test
	public void testQuery() throws SolrServerException{
		SolrServer solrServer = new HttpSolrServer("http://192.168.255.131:8080/solr");
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		QueryResponse response = solrServer.query(query);
		
		SolrDocumentList documentList = response.getResults();
		for(SolrDocument solrDocument :documentList){
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_sell_point"));
		}
	}
	
	
}
