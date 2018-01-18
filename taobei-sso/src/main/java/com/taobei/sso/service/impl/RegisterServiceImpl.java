package com.taobei.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taobei.common.pojo.TaobeiResult;
import com.taobei.mapper.TbUserMapper;
import com.taobei.pojo.TbUser;
import com.taobei.pojo.TbUserExample;
import com.taobei.pojo.TbUserExample.Criteria;
import com.taobei.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Override
	public TaobeiResult checkData(String param, int type) throws Exception {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//type 1、2、3 分别代表 username  phone  email
		if(type == 1){
			criteria.andUsernameEqualTo(param);
		}else if(type == 2){
			criteria.andPhoneEqualTo(param);
		}else if(type == 3){
			criteria.andEmailEqualTo(param);
		}else{
			//do nothing
		}
		//执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		if(list==null || list.isEmpty()){
			return TaobeiResult.ok(true);
		}
		return TaobeiResult.ok(false);
	}

	@Override
	public TaobeiResult register(TbUser user) throws Exception {
		if((user.getUsername()==null&&"".equals(user.getUsername())
				||(user.getPassword()==null&&"".equals(user.getPassword())))){
			return TaobeiResult.build(400, "用户名或密码不能为空");
		}
		
		TaobeiResult result = checkData(user.getUsername(), 1);
		if(!(boolean) result.getData()){
			return TaobeiResult.build(400, "用户名不可用");
		}
		
		if(user.getPhone()!=null&&!"".equals(user.getPhone())){
			result = checkData(user.getPhone(), 2);
			if(!(boolean) result.getData()){
				return TaobeiResult.build(400, "手机号不可用");
			}
		}
		if(user.getEmail()!=null&&!"".equals(user.getEmail())){
			result = checkData(user.getEmail(), 3);
			if(!(boolean) result.getData()){
				return TaobeiResult.build(400, "邮箱不可用");
			}
		}
		user.setCreated(new Date());
		user.setUpdated(new Date());
		
		//对密码进行 MD5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		
		userMapper.insert(user);
		return TaobeiResult.ok();
	}

}
