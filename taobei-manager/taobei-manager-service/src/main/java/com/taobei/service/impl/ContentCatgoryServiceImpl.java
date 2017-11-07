package com.taobei.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobei.common.pojo.EasyUITreeNode;
import com.taobei.common.pojo.TaobeiResult;
import com.taobei.mapper.TbContentCategoryMapper;
import com.taobei.pojo.TbContentCategory;
import com.taobei.pojo.TbContentCategoryExample;
import com.taobei.pojo.TbContentCategoryExample.Criteria;
import com.taobei.service.ContentCatgoryService;

@Service
public class ContentCatgoryServiceImpl implements ContentCatgoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	/**
	 * 获取内容分类列表
	 */
	@Override
	public List<EasyUITreeNode> getContentCatList(Long parentId) throws Exception {
		
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		
		List<EasyUITreeNode> result = new ArrayList<EasyUITreeNode>();
		for(TbContentCategory tbContentCategory :list){
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			result.add(node);
		}
		return result;
	}

	@Override
	public TaobeiResult insertCategory(Long parentId, String name) throws Exception {
		TbContentCategory category = new TbContentCategory();
		category.setName(name);
		category.setParentId(parentId);
		
		category.setStatus(1); //1正常 2删除
		category.setSortOrder(1); //排列序号，表示同级类目的展现次序，如数值相等则按照名称次序排列。取值范围大于0的整数
		
		category.setIsParent(false);
		category.setCreated(new Date());
		category.setUpdated(new Date());
		//插入
		contentCategoryMapper.insert(category);
		//去返回的主键
		Long id = category.getId();
		
		//判断父节点的isparent属性
		TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentNode.getIsParent()){
			parentNode.setIsParent(true);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		
		return TaobeiResult.ok(id);
	}

	@Override
	public TaobeiResult updateCategory(Long id, String name) throws Exception {
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		category.setName(name);
		contentCategoryMapper.updateByPrimaryKey(category);
		return TaobeiResult.ok();
	}

}
