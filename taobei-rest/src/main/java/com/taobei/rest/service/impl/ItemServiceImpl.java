package com.taobei.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.taobei.common.utils.JsonUtils;
import com.taobei.mapper.TbItemDescMapper;
import com.taobei.mapper.TbItemMapper;
import com.taobei.mapper.TbItemParamItemMapper;
import com.taobei.pojo.TbItem;
import com.taobei.pojo.TbItemDesc;
import com.taobei.pojo.TbItemParamItem;
import com.taobei.pojo.TbItemParamItemExample;
import com.taobei.pojo.TbItemParamItemExample.Criteria;
import com.taobei.rest.component.JedisClient;
import com.taobei.rest.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	
	@Value("${ITEM_BASE_INFO_KEY}")
	private String ITEM_BASE_INFO_KEY;
	
	@Value("${ITEM_EXPIRE_SECOND}")
	private Integer ITEM_EXPIRE_SECOND;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Value("${ITEM_DESC_KEY}")
	private String ITEM_DESC_KEY;
	
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	@Value("${ITEM_PARAM_KEY}")
	private String ITEM_PARAM_KEY;
	
	@Override
	public TbItem getItemById(Long id) throws Exception {
		//查询缓存，如果有缓存，直接返回

		String key = REDIS_ITEM_KEY + ":"+id+":" + ITEM_BASE_INFO_KEY;
		
		try {
			String json = jedisClient.get(key);
			if(!StringUtils.isEmpty(json)){
				return JsonUtils.jsonToPojo(json, TbItem.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//根据商品ID查询商品基本信息
		TbItem tbItem = itemMapper.selectByPrimaryKey(id);
		
		//向redis中添加缓存
		try {
			//注：要设置过期时间redis中只能在key上设置
			//使用string数据类型，为了方便分组可以使用“:”分割命名的方式
			//REDIS_ITEM:BASE_INFO:{ITEM_ID}
			jedisClient.set(key, JsonUtils.objectToJson(tbItem));
			jedisClient.expire(key, ITEM_EXPIRE_SECOND); //设置过期时间
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tbItem;
	}

	@Override
	public TbItemDesc getItemDescById(Long id) throws Exception {
		
		//查询缓存，如果有缓存，直接返回
		String key = REDIS_ITEM_KEY + ":"+id+":" + ITEM_DESC_KEY;
		
		try {
			String json = jedisClient.get(key);
			if(!StringUtils.isEmpty(json)){
				return JsonUtils.jsonToPojo(json, TbItemDesc.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//数据库查询
		TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(id);
		
		//向redis中添加缓存
		try {
			//注：要设置过期时间redis中只能在key上设置
			//使用string数据类型，为了方便分组可以使用“:”分割命名的方式
			//REDIS_ITEM:BASE_INFO:{ITEM_ID}
			jedisClient.set(key, JsonUtils.objectToJson(tbItemDesc));
			jedisClient.expire(key, ITEM_EXPIRE_SECOND); //设置过期时间
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tbItemDesc;
	}

	@Override
	public TbItemParamItem getItemParamById(Long id) throws Exception {
		
		//查询缓存，如果有缓存，直接返回
		String key = REDIS_ITEM_KEY + ":"+id+":" + ITEM_PARAM_KEY;
		try {
			String json = jedisClient.get(key);
			if(!StringUtils.isEmpty(json)){
				return JsonUtils.jsonToPojo(json, TbItemParamItem.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//查询数据库
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(id);
		List<TbItemParamItem> itemParamItems = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(itemParamItems!=null&&itemParamItems.size() > 0){
			TbItemParamItem itemParamItem = itemParamItems.get(0);
			
			
			//向redis中添加缓存
			try {
				//注：要设置过期时间redis中只能在key上设置
				//使用string数据类型，为了方便分组可以使用“:”分割命名的方式
				//REDIS_ITEM:BASE_INFO:{ITEM_ID}
				jedisClient.set(key, JsonUtils.objectToJson(itemParamItem));
				jedisClient.expire(key, ITEM_EXPIRE_SECOND); //设置过期时间
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return itemParamItem;
		}
		return null;
	}

}
