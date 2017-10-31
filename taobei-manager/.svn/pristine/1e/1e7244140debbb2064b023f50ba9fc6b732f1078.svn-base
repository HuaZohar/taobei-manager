package com.taobei.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taobei.mapper.TbItemMapper;
import com.taobei.pojo.TbItem;
import com.taobei.pojo.TbItemExample;

public class TestPageHelper {
	
	@Test
	public void testPageHelper() throws Exception{
		
		//获取mapper代理对象
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);
		//设置分页
		PageHelper.startPage(1, 30);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		//取分页后的结果
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		long total = pageInfo.getTotal();
		System.out.println(total);
		int pages = pageInfo.getPages();
		System.out.println(pages);
		int pagesize = pageInfo.getPageSize();
		System.out.println(pagesize);
		
		
	}
}
