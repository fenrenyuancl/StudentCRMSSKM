package com.bbu.cl.entity;

import org.springframework.stereotype.Component;
/**
 * 年级实体
 * @author 疯人愿
 *
 */
@Component
public class Grade {
	private Long id;				//id,主键，自增
	private String name;			//年级名称
	private String remark;			//年级备注
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
