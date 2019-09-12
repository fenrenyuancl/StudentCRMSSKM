package com.bbu.cl.entity;

import org.springframework.stereotype.Component;

/**
 * 班级实体
 * @author 疯人愿
 *
 */
@Component
public class Clazz {
	private Long id;				//id,主键，自增
	private Long gradeId;			//年级id
	private String name;			//班级名称
	private String remark;			//班级备注
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGradeId() {
		return gradeId;
	}
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
