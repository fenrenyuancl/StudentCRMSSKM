package com.bbu.cl.page;

import java.security.KeyStore.PrivateKeyEntry;

import org.springframework.stereotype.Component;

/**
 * 分页类封装
 * @author 疯人愿
 *
 */
@Component
public class Page {
	private int page;				//当前页码
	private int rows;				//每页显示数量
	private int offset;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getOffset() {
		this.offset = (page-1)*rows;
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = (page-1)*rows;
	}					
}
