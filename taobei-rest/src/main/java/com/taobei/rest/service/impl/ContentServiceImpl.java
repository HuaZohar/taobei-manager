package com.taobei.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.taobei.common.pojo.TaobeiResult;
import com.taobei.common.utils.JsonUtils;
import com.taobei.mapper.TbContentMapper;
import com.taobei.pojo.TbContent;
import com.taobei.pojo.TbContentExample;
import com.taobei.pojo.TbContentExample.Criteria;
import com.taobei.rest.component.JedisClient;
import com.taobei.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;
	
	@Override
	public List<TbContent> getContentList(Long cid) throws Exception {
		//添加缓存
		//查询数据库之前先查询缓存，如果有直接返回
		try {
			//从redis中取缓存数据
			String json = jedisClient.hget(REDIS_CONTENT_KEY, cid+"");
			if(!StringUtils.isEmpty(json)){
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
		}
		
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		
		
		//返回结果之前，向缓存中添加
		//注意：
		try {
			//为了规范key可以使用hash
			//定义一个保存内容的key，hash中每个项就是cid
			//value是list，需要把list转为json数据
			jedisClient.hset(REDIS_CONTENT_KEY, cid+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list;
	}

	@Override
	public TaobeiResult syncContent(Long cid) throws Exception {
		jedisClient.hdel(REDIS_CONTENT_KEY, cid+"");
		return TaobeiResult.ok();
	}

}
