package com.taobei.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobei.mapper.TbContentMapper;
import com.taobei.pojo.TbContent;
import com.taobei.pojo.TbContentExample;
import com.taobei.pojo.TbContentExample.Criteria;
import com.taobei.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	@Override
	public List<TbContent> getContentList(Long cid) throws Exception {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		
		return contentMapper.selectByExampleWithBLOBs(example);
	}

}
