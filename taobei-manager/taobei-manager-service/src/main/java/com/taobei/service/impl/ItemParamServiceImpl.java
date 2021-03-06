package com.taobei.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobei.common.pojo.EasyUIDataGridResult;
import com.taobei.common.pojo.TaobeiResult;
import com.taobei.mapper.TbItemCatMapper;
import com.taobei.mapper.TbItemParamMapper;
import com.taobei.pojo.TbItem;
import com.taobei.pojo.TbItemCat;
import com.taobei.pojo.TbItemParam;
import com.taobei.pojo.TbItemParamExample;
import com.taobei.pojo.TbItemParamExample.Criteria;
import com.taobei.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public EasyUIDataGridResult getItemParamList(int page, int rows) throws Exception {		//分页处理
		//分页处理
		PageHelper.startPage(page, rows);
		
		TbItemParamExample example = new TbItemParamExample();
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		
		for(TbItemParam tbItemParam :list){
			TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(tbItemParam.getItemCatId());
			tbItemParam.setItemCatName(itemCat.getName());
		}
		EasyUIDataGridResult dataGridResult = new EasyUIDataGridResult();
		//添加至分页插件中
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
		dataGridResult.setTotal(pageInfo.getTotal());
		dataGridResult.setRows(list);
		
		return dataGridResult;
	}
	
	/**
	 * 根据类目ID 查找商品规格参数模板
	 */
	@Override
	public TaobeiResult getItemParamByCid(Long cid){
		
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		if(list != null && list.size() > 0){
			TbItemParam itemParam = list.get(0);
			return TaobeiResult.ok(itemParam);
		}
		return TaobeiResult.ok();
	}

	@Override
	public TaobeiResult insertItemParam(Long cid, String paramData) throws Exception {
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		itemParamMapper.insert(itemParam);
		return TaobeiResult.ok();
	}

	@Override
	public TaobeiResult deleteItemParam(Long id) throws Exception {
		itemParamMapper.deleteByPrimaryKey(id);
		return TaobeiResult.ok();
	}
	
	

}
