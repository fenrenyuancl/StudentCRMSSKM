package com.bbu.cl.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bbu.cl.entity.Clazz;

/**
 * 班级Service
 * @author 疯人愿
 *
 */
@Service
public interface ClazzService {
		//添加班级
		public int add(Clazz clazz);
		//修改班级
		public int edit(Clazz clazz);
		//删除班级
		public int delete(String ids);
		//查询列表
		public List<Clazz> findList(Map<String,Object> queryMap);
		//查询所有列表
		public List<Clazz> findAll();
		//获取数量
		public int getTotal(Map<String,Object> queryMap);
}
