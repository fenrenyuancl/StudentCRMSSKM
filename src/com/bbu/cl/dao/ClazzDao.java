package com.bbu.cl.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bbu.cl.entity.Clazz;
import com.bbu.cl.entity.Grade;

/**
 * 班级dao
 */
@Repository
public interface ClazzDao {
	// 添加班级
	public int add(Clazz clazz);

	// 修改班级
	public int edit(Clazz clazz);

	// 删除班级
	public int delete(String ids);

	// 查询列表
	public List<Clazz> findList(Map<String, Object> queryMap);

	// 查询所有列表
	public List<Clazz> findAll();

	// 获取数量
	public int getTotal(Map<String, Object> queryMap);
}
