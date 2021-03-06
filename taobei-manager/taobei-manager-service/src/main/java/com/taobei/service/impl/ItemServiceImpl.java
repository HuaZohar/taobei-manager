package com.taobei.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobei.common.pojo.EasyUIDataGridResult;
import com.taobei.common.pojo.TaobeiResult;
import com.taobei.common.utils.IDUtils;
import com.taobei.common.utils.JsonUtil;
import com.taobei.mapper.TbItemDescMapper;
import com.taobei.mapper.TbItemMapper;
import com.taobei.mapper.TbItemParamItemMapper;
import com.taobei.pojo.TbItem;
import com.taobei.pojo.TbItemDesc;
import com.taobei.pojo.TbItemExample;
import com.taobei.pojo.TbItemExample.Criteria;
import com.taobei.pojo.TbItemParamItem;
import com.taobei.pojo.TbItemParamItemExample;
import com.taobei.service.ItemService;
import com.taobei.common.utils.JsonUtils;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Override
	public TbItem getItemById(Long itemId) throws Exception {
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		TbItem item = null;
		if(list!=null && list.size() > 0) {
			item = list.get(0);
		}
		return item;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) throws Exception {
		//分页处理
		PageHelper.startPage(page, rows);
		//查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		EasyUIDataGridResult dataGridResult = new EasyUIDataGridResult();
		//添加至分页插件中
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		dataGridResult.setTotal(pageInfo.getTotal());
		dataGridResult.setRows(list);
		return dataGridResult;
	}

	@Override
	public TaobeiResult CreateItem(TbItem tbItem, String desc, String itemParams) {
		//注此处不添加try catch
		
		//生成ItemId
		long itemId = IDUtils.genItemId();
		
		//补全TbItem
		tbItem.setId(itemId);
		//商品状态 1 正常    2 下架    3删除
		tbItem.setStatus((byte)1);
		
		Date date = new Date();
		tbItem.setCreated(date);
		tbItem.setUpdated(date);
		//执行插入
		itemMapper.insert(tbItem);
		
		
		//商品描述
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(date);
		tbItemDesc.setUpdated(date);
		//执行插入
		itemDescMapper.insert(tbItemDesc);
		
		
		//添加商品规格参数
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		//插入操作 
		itemParamItemMapper.insert(itemParamItem);
		
		return TaobeiResult.ok();
	}

	@Override
	public String getItemParamHtml(Long itemId) throws Exception {
		TbItemParamItemExample example = new TbItemParamItemExample();
		com.taobei.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		
		
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		
		if(list == null || list.isEmpty()){
			return "";
		}
		
		TbItemParamItem itemParamItem = list.get(0);
		//取json数据
		String paramData = itemParamItem.getParamData();
		//转为java对象
		List<Map> mapList = JsonUtils.jsonToList(paramData, Map.class);
		
		//遍历list生成html
		StringBuffer sb = new StringBuffer();
		 
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
		sb.append("	<tbody>\n");
		for (Map map : mapList) {
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
