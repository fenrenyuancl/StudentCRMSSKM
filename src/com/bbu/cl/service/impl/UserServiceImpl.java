package com.bbu.cl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbu.cl.dao.UserDao;
import com.bbu.cl.entity.User;
import com.bbu.cl.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	//从容其中拿userdao变量
	@Autowired
	private UserDao userdao;
	@Override
	public User findByUserName(String username) {
		// TODO Auto-generated method stub
		return userdao.findByUserName(username);
	}
	@Override
	public int add(User user) {
		// TODO Auto-generated method stub
		return userdao.add(user);
	}
	@Override
	public List<User> findList(Map<String,Object> queryMap) {
		// TODO Auto-generated method stub
		return userdao.findList(queryMap);
	}
	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return userdao.getTotal(queryMap);
	}
	@Override
	public int edit(User user) {
		// TODO Auto-generated method stub
		return userdao.edit(user);
	}
	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		return userdao.delete(ids);
	}

}
