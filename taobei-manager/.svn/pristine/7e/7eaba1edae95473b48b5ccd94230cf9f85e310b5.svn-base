package com.taobei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobei.common.pojo.EasyUIDataGridResult;
import com.taobei.mapper.TbItemMapper;
import com.taobei.pojo.TbItem;
import com.taobei.pojo.TbItemExample;
import com.taobei.pojo.TbItemExample.Criteria;
import com.taobei.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
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

}
