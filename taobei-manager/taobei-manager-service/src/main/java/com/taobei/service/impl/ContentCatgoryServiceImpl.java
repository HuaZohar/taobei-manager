package com.taobei.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobei.common.pojo.EasyUITreeNode;
import com.taobei.common.pojo.TaobeiResult;
import com.taobei.mapper.TbContentCategoryMapper;
import com.taobei.mapper.TbContentMapper;
import com.taobei.pojo.TbContentCategory;
import com.taobei.pojo.TbContentCategoryExample;
import com.taobei.pojo.TbContentCategoryExample.Criteria;
import com.taobei.pojo.TbContentExample;
import com.taobei.service.ContentCatgoryService;

@Service
public class ContentCatgoryServiceImpl implements ContentCatgoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Autowired
	private TbContentMapper contentMapper;
	
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

	@Override
	public TaobeiResult deleteCategory(Long id) throws Exception {
		//1.如果为顶层节点，则不删除
		if(id.equals(0)){
			return TaobeiResult.build(500, "删除失败：顶层节点不可被删除！");
		}
		//2.获取当前节点
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);

		//3.查询是否有子节点，如果有子节点，根据parentID=当前节点的ID，删除子节点，，，，此处需要递归删除子孙节点
		//如果当前节点是父节点，则递归删除子孙节点
		if(category.getIsParent()){
			deleteDescendantNode(category.getId());	
		}
		
		//4.查询当前节点的父节点是否还有子节点，如果没有，修改父节点的is_parent字段为0
		TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(category.getParentId());
		parentCategory.setIsParent(false);
		contentCategoryMapper.updateByPrimaryKey(parentCategory); //提交更新
		
		//5.删除当前节点
		contentCategoryMapper.deleteByPrimaryKey(id);
		
		//6.当前节点中的内容文章一起删除
		TbContentExample example = new TbContentExample();
		com.taobei.pojo.TbContentExample.Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(id);
		contentMapper.deleteByExample(example);
		
		return TaobeiResult.build(200, "删除成功");
	}
	

	/**
	 * 递归删除子孙节点
	 * @param parentId 
	 */
	private void deleteDescendantNode(Long parentId){
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> categories = contentCategoryMapper.selectByExample(example);
		for(TbContentCategory contentCategory :categories){
			if(contentCategory.getIsParent()){
				deleteDescendantNode(contentCategory.getId());
			}
			//同时删除节点对应的文章内容
			TbContentExample contentExample = new TbContentExample();
			com.taobei.pojo.TbContentExample.Criteria contentCriteria = contentExample.createCriteria();
			contentCriteria.andCategoryIdEqualTo(contentCategory.getId());
			contentMapper.deleteByExample(contentExample);
			//删除节点
			contentCategoryMapper.deleteByPrimaryKey(contentCategory.getId());
		}
	}
	

}
