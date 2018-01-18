package com.taobei.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taobei.common.pojo.TaobeiResult;

public interface LoginService {

	TaobeiResult login(String username,String password,HttpServletRequest request,HttpServletResponse response) throws Exception;
	
	TaobeiResult getUserByToken(String token) throws Exception;
}
