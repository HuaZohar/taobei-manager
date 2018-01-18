package com.taobei.portal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taobei.pojo.TbUser;

public interface UserService {
	TbUser getUserByToken(HttpServletRequest request,HttpServletResponse response) throws Exception;
}
