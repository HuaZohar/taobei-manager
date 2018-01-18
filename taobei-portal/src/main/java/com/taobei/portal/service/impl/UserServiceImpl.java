package com.taobei.portal.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobei.common.pojo.TaobeiResult;
import com.taobei.common.utils.CookieUtils;
import com.taobei.common.utils.HttpClientUtil;
import com.taobei.pojo.TbUser;
import com.taobei.portal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Value("${SS0_BASE_URL}")
	private String SS0_BASE_URL;
	
	@Value("${SSO_USER_TOKEN_SERVICE}")
	private String SSO_USER_TOKEN_SERVICE;
	
	@Override
	public TbUser getUserByToken( HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
			if(token==null||"".equals(token)){
				return null;
			}
			String json = HttpClientUtil.doGet(SS0_BASE_URL + SSO_USER_TOKEN_SERVICE + token);
			
			TaobeiResult result = TaobeiResult.format(json); //判断用户是否为null
			if(result==null||result.getStatus() != 200){
				return null;
			}
			result = TaobeiResult.formatToPojo(json, TbUser.class);
			TbUser data = (TbUser) result.getData();
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
