package com.taobei.sso.service;

import com.taobei.common.pojo.TaobeiResult;
import com.taobei.pojo.TbUser;

public interface RegisterService {

	public TaobeiResult checkData(String param,int type) throws Exception;
	
	public TaobeiResult register(TbUser tbUser) throws Exception;
}
