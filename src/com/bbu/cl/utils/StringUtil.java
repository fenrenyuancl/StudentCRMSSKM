package com.bbu.cl.utils;

import java.util.Date;
import java.util.List;

/**
 * 实用工具类
 * @author 疯人愿
 *
 */
public class StringUtil {
	/**
	 * 将给定的list按照制定的分隔符分割成字符串返回
	 * @param list
	 * @param splist
	 * @return
	 */
	public static String joinString(List<Long> list,String splist) {
		String ret = "";
		for(Long l:list) {
			ret += l + splist;
		}
		if (!"".equals(ret)) {
			ret = ret.substring(0,ret.length()-splist.length());
		}
		return ret;
	}
	/**
	 * 自动生成学生学号
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public static String generateSn(String prefix,String suffix) {
		//返回的学号格式：前缀+时间戳+后缀
		return prefix + new Date().getTime() + suffix;
		
	}
}
