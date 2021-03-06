package com.taobei.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobei.common.pojo.EasyUIDataGridResult;
import com.taobei.common.pojo.TaobeiResult;
import com.taobei.mapper.TbContentMapper;
import com.taobei.pojo.TbContent;
import com.taobei.pojo.TbContentExample;
import com.taobei.pojo.TbItem;
import com.taobei.pojo.TbContentExample.Criteria;
import com.taobei.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	@Override
	public EasyUIDataGridResult getContentList(Long categoryId,int page, int rows) throws Exception {
		
		//分页处理
		PageHelper.startPage(page, rows);
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExample(example);
		
		//查询数据放入结果集中
		EasyUIDataGridResult dataGridResult = new EasyUIDataGridResult();
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		dataGridResult.setTotal(pageInfo.getTotal());
		dataGridResult.setRows(list);
		
		return dataGridResult;
	}

	@Override
	public TaobeiResult insertContent(TbContent content) throws Exception {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		return TaobeiResult.ok();
	}

	@Override
	public TaobeiResult deleteContent(Long id) throws Exception {
		contentMapper.deleteByPrimaryKey(id);
		return TaobeiResult.ok();
	}

	@Override
	public TaobeiResult updateContent(TbContent content) throws Exception {
		contentMapper.updateByPrimaryKey(content);
		return TaobeiResult.ok();
	}

	@Override
	public TbContent getContentById(Long contentId) throws Exception {
		return contentMapper.selectByPrimaryKey(contentId);
	}

}
