package com.taobei.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taobei.pojo.TbUser;
import com.taobei.portal.service.UserService;
/**
 * 用户登录拦截器
 * @author Administrator
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;
	
	@Value("${SSO_LOGIN_URL}")
	private String SSO_LOGIN_URL;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//1.拦截请求url
		//2.从cookie中取token
		//3.如果没有token跳转到登录页面
		//4.取token，需要调用sso服务
		TbUser user = userService.getUserByToken(request, response);
 		if(user == null){
			response.sendRedirect(SSO_LOGIN_URL + "?redirectURL=" + request.getRequestURL());
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
