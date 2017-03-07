package com.zhongtai.service;

import java.util.List;

import com.zhongtai.modle.UserGroup;

/**
 * 用户相关service
 * @author zhangfan
 *
 */
public interface IUserServie {

	/**
	 * 验证用户是否合法
	 * @param username
	 * @param password
	 * @return
	 */
	public Integer validateUser(String username,String password); 
	
	/**
	 * 获取用户所有组
	 */
	public List<UserGroup> listGroup(Integer userid);
	
	/**
	 * 根据组获取所有所有 用户id
	 */
	public List<Integer> listUserId(Integer groupid);
	
	/**
	 * 根据用户id查询有关系的userid
	 */
	public List<Integer> listUseridByUserid(Integer userid);
	
	/**
	 * 根据用户id查询对应组id
	 */
	public Integer getGroupid(Integer userid);
	
	/**
	 * 根据用户名密码查询对应组id
	 */
	public Integer getGroupid(String username,String pwd);
}
