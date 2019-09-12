package com.bbu.cl.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bbu.cl.entity.Grade;

/**
 * 年级Service
 * @author 疯人愿
 *
 */
@Service
public interface GradeService {
		//添加年级
		public int add(Grade grade);
		//修改年级
		public int edit(Grade grade);
		//删除年级
		public int delete(String ids);
		//查询列表
		public List<Grade> findList(Map<String,Object> queryMap);
		//查询所有列表
		public List<Grade> findAll();
		//获取数量
		public int getTotal(Map<String,Object> queryMap);
}
