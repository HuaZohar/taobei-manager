package com.taobei.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobei.mapper.TbItemCatMapper;
import com.taobei.pojo.TbItemCat;
import com.taobei.pojo.TbItemCatExample;
import com.taobei.pojo.TbItemCatExample.Criteria;
import com.taobei.rest.pojo.CatNode;
import com.taobei.rest.pojo.ItemCatResult;
import com.taobei.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public ItemCatResult getItemCatList() throws Exception {
		List catList = getItemCatList(0l);
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
