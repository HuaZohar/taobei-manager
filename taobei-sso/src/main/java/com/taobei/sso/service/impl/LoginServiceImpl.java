package com.taobei.sso.service.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taobei.common.pojo.TaobeiResult;
import com.taobei.common.utils.CookieUtils;
import com.taobei.common.utils.JsonUtils;
import com.taobei.mapper.TbUserMapper;
import com.taobei.pojo.TbUser;
import com.taobei.pojo.TbUserExample;
import com.taobei.pojo.TbUserExample.Criteria;
import com.taobei.sso.component.JedisClient;
import com.taobei.sso.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_SESSION_KEY}")
	private String REDIS_SESSION_KEY;
	
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public TaobeiResult login(String username, String password, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//校验用户名和密码是否正确
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		
		List<TbUser> list = userMapper.selectByExample(example);
		//取用户信息
		if(list == null || list.isEmpty()){
			return TaobeiResult.build(400, "用户名和密码错误");
		}
		
		TbUser user = list.get(0);
		//校验密码
		if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
			return TaobeiResult.build(400, "用户名和密码错误");
		}
		
		//登录成功
		//生成token
		String token = UUID.randomUUID().toString();
		
		//写入redis
		user.setPassword(null); //清空密码
		jedisClient.set(REDIS_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
		//设置过期时间
		jedisClient.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE);
		
		//写入cookie
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		
		return TaobeiResult.ok(token);
	}

	@Override
	public TaobeiResult getUserByToken(String token) throws Exception {
		String json = jedisClient.get(REDIS_SESSION_KEY + ":" + token);
		
		if(json == null || "".equals(json)){
			return TaobeiResult.build(400, "用户session已经过期");
		}
		
		//把json转为java对象
		TbUser user  = JsonUtils.jsonToPojo(json, TbUser.class);
		
		//更新session的过期时间
		jedisClient.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE);
		
		return TaobeiResult.ok(user);
	}

}
