package com.taobei.portal.service;

import com.taobei.common.pojo.TaobeiResult;

public interface StaticPageService {
	TaobeiResult getItemHtml(Long id) throws Exception;
}
