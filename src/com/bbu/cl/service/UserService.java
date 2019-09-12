package com.bbu.cl.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bbu.cl.entity.User;

@Service
public interface UserService {
	//页面显示查询
	public User findByUserName(String username);
	//添加用户
	public int add(User user);
	//修改用户
	public int edit(User user);
	//删除用户
	public int delete(String ids);
	//查询列表
	public List<User> findList(Map<String,Object> queryMap);
	//获取数量
	public int getTotal(Map<String,Object> queryMap);
}
