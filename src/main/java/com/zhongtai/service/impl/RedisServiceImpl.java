package com.zhongtai.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongtai.redis.RedisManager;
import com.zhongtai.service.RedisService;

//@Service("redisService")
public class RedisServiceImpl implements RedisService{

	//@Autowired
	RedisManager redisManager ;
	
	@Override
	public void delete(String key) {
		// TODO Auto-generated method stub
		redisManager.del(key);
	}
	
	

}
