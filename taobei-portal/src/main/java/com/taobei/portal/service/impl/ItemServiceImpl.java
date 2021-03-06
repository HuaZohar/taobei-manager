package com.taobei.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobei.common.pojo.TaobeiResult;
import com.taobei.common.utils.HttpClientUtil;
import com.taobei.common.utils.JsonUtils;
import com.taobei.pojo.TbItem;
import com.taobei.pojo.TbItemDesc;
import com.taobei.pojo.TbItemParamItem;
import com.taobei.portal.pojo.PortalItem;
import com.taobei.portal.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_ITEM_BASE_URL}")
	private String REST_ITEM_BASE_URL;
	
	@Value("${REST_ITEM_DESC_URL}")
	private String REST_ITEM_DESC_URL;
	
	@Value("${REST_ITEM_PARAM_URL}")
	private String REST_ITEM_PARAM_URL;
	/**
	 * 根据商品ID 查询商品基本信息
	 */
	@Override
	public TbItem getItemById(Long itemId) throws Exception {
		//使用httpClient
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASE_URL + itemId);
		//把json数据转为对象
		TaobeiResult taobeiResult = TaobeiResult.formatToPojo(json, PortalItem.class);
		//取商品对象
		TbItem tbItem = (TbItem) taobeiResult.getData();
		return tbItem;
	}
	@Override
	public String getItemDescById(Long itemId) throws Exception {

		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_DESC_URL + itemId);
		TaobeiResult taobeiResult = TaobeiResult.formatToPojo(json, TbItemDesc.class);
		

		TbItemDesc tbItemDesc = (TbItemDesc) taobeiResult.getData();
		
		return tbItemDesc.getItemDesc();
	}
	@Override
	public String getItemParamById(Long itemId) throws Exception {

		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PARAM_URL + itemId);
		TaobeiResult taobeiResult = TaobeiResult.formatToPojo(json, TbItemParamItem.class);
		TbItemParamItem tbItemParamItem = (TbItemParamItem) taobeiResult.getData();
		String paramJson = tbItemParamItem.getParamData();
		
		List<Map> paramList = JsonUtils.jsonToList(paramJson, Map.class);
		
		//遍历list生成html
		StringBuffer sb = new StringBuffer();
		 
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
		sb.append("	<tbody>\n");
		for (Map map : paramList) {
			sb.append("		<tr>\n");
			sb.append("			<th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
			sb.append("		</tr>\n");
			//取规格项
			List<Map>mapList2 = (List<Map>) map.get("params");
			for (Map map2 : mapList2) {
				sb.append("		<tr>\n");
				sb.append("			<td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
				sb.append("			<td>"+map2.get("v")+"</td>\n");
				sb.append("		</tr>\n");
			}
		}
		sb.append("	</tbody>\n");
		sb.append("</table>");
		
		return sb.toString();
	}

}
