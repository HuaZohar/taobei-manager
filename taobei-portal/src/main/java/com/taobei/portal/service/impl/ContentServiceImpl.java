package com.taobei.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobei.common.pojo.TaobeiResult;
import com.taobei.common.utils.HttpClientUtil;
import com.taobei.common.utils.JsonUtils;
import com.taobei.pojo.TbContent;
import com.taobei.portal.pojo.AdNode;
import com.taobei.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService{

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_CONTENT_URL}")
	private String REST_CONTENT_URL;
	
	@Value("${REST_CONTENT_AD1_CID}")
	private String REST_CONTENT_AD1_CID;
	/**
	 * 获得大广告位
	 */
	@Override
	public String getAd1List() {
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_URL + REST_CONTENT_AD1_CID);
		TaobeiResult result = TaobeiResult.formatToList(json, TbContent.class);
		List<TbContent> list = (List<TbContent>) result.getData();
		
		List<AdNode> adNodes = new ArrayList<>();
		for(TbContent tbContent :list){
			AdNode adNode = new AdNode();
			adNode.setHeight(240);
			adNode.setWidth(670);
			adNode.setSrc(tbContent.getPic());
			
			adNode.setHeightB(240);
			adNode.setWidthB(550);
			adNode.setSrcB(tbContent.getPic2());
			
			adNode.setAlt(tbContent.getSubTitle());
			adNode.setHref(tbContent.getUrl());
			
			adNodes.add(adNode);
		}
		String resultJson = JsonUtils.objectToJson(adNodes);
		return resultJson;
	}
	
}
