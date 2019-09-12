package com.bbu.cl.entity;

import org.springframework.stereotype.Component;

/**
 * 学生实体
 * @author 疯人愿
 *
 */
@Component
public class Student {
	private Long id;					//id,主键，自增
	private String sn;					//学生学号 自动生成
	private Long clazzId;				//班级id
	private String username;			//学生名称
	private String password;			//登陆密码
	private String sex;					//学生性别
	private String photo;				//学生头像
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Long getClazzId() {
		return clazzId;
	}
	public void setClazzId(Long clazzId) {
		this.clazzId = clazzId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
}
