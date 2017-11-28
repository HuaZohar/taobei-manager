package com.taobei.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.taobei.common.utils.JsonUtils;
import com.taobei.mapper.TbItemCatMapper;
import com.taobei.pojo.TbContent;
import com.taobei.pojo.TbItemCat;
import com.taobei.pojo.TbItemCatExample;
import com.taobei.pojo.TbItemCatExample.Criteria;
import com.taobei.rest.component.JedisClient;
import com.taobei.rest.pojo.CatNode;
import com.taobei.rest.pojo.ItemCatResult;
import com.taobei.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_ITEM_CAT_KEY}")
	private String REDIS_ITEM_CAT_KEY;
	
	@Override
	public ItemCatResult getItemCatList() throws Exception {
		
		//添加缓存
		//查询数据库之前先查询缓存，如果有直接返回
		try {
			//从redis中取缓存数据
			String json = jedisClient.get(REDIS_ITEM_CAT_KEY);
			if(!StringUtils.isEmpty(json)){
				List list = JsonUtils.jsonToList(json, CatNode.class);
				ItemCatResult result = new ItemCatResult();
				result.setData(list);
				return result;
			}
		} catch (Exception e) {
			//如果出错也必须继续执行，不能断
			e.printStackTrace();
		}
		
		List catList = getItemCatList(0l);
		
		
		//返回结果之前，向缓存中添加
		//注意：
		try {
			//为了规范key可以使用hash
			//定义一个保存内容的key，hash中每个项就是cid
			//value是list，需要把list转为json数据
			jedisClient.set(REDIS_ITEM_CAT_KEY,JsonUtils.objectToJson(catList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ItemCatResult result = new ItemCatResult();
		result.setData(catList);
		return result;
	}
	
	private List getItemCatList(Long parentId){
		
		//根据parentId查询列表
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List resultList = new ArrayList<>();
		int index = 0;
		for(TbItemCat itemCat :list){
			if(index >= 14) break;
			//如果是父节点
			if(itemCat.getIsParent()){
				CatNode catNode = new CatNode();
				catNode.setUrl("/products/"+itemCat.getId()+".html");
				
				if(itemCat.getParentId() == 0){
					catNode.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
					index++; //第一级节点不能超过14个
				}else{
					catNode.setName(itemCat.getName());
				}
				catNode.setItems(getItemCatList(itemCat.getId()));
				//把catNode添加到List
				resultList.add(catNode);
			}else{
				String item = "/products/"+itemCat.getId()+".html|" + itemCat.getName();
				resultList.add(item);
			}
		}
		
		return resultList;
		
		
	}

}
