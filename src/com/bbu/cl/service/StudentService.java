package com.bbu.cl.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bbu.cl.entity.Clazz;
import com.bbu.cl.entity.Student;

/**
 * 学生Service
 * @author 疯人愿
 *
 */
@Service
public interface StudentService {
		//页面显示查询
		public Student findByUserName(String username);
		//添加学生
		public int add(Student student);
		//修改学生
		public int edit(Student student);
		//删除学生
		public int delete(String ids);
		//查询列表
		public List<Clazz> findList(Map<String,Object> queryMap);
		//查询所有列表
		public List<Student> findAll();
		//获取数量
		public int getTotal(Map<String,Object> queryMap);
}
