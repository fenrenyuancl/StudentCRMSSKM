package com.bbu.cl.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bbu.cl.entity.User;

/**
 * 用户持久层
 * @author 疯人愿
 *
 */
@Repository
public interface UserDao {
	public User findByUserName(String username);
	//添加用户
	public int add(User user);
	//查询列表
	public List<User> findList(Map<String,Object> queryMap);
	//获取数量
	public int getTotal(Map<String,Object> queryMap);
	//修改用户
	public int edit(User user);
	//删除用户
	public int delete(String ids);
}
