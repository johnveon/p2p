package com.fion.redis;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTool {

	@Resource
	private RedisTemplate<String, String> redisTemplate;
	
	/**
	 * 设值
	 * @param key
	 * @param value
	 * @author zhoufe 2015-9-10 下午9:23:52
	 */
	public void set(String key, String value){
		redisTemplate.opsForValue().set(key,  value);
	}
	/**
	 * 获取
	 * @param key
	 * @return
	 * @author zhoufe 2015-9-10 下午9:24:07
	 */
	public String get(String key){
		return redisTemplate.opsForValue().get(key);
	}
	/**
	 * 删除
	 * @param key
	 * @author zhoufe 2015-9-10 下午9:24:23
	 */
	public void del(String key){
		redisTemplate.delete(key);
	}
}
