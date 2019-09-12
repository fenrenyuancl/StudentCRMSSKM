package com.bbu.cl.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bbu.cl.entity.Grade;
import com.bbu.cl.entity.User;
import com.bbu.cl.page.Page;
import com.bbu.cl.service.GradeService;
import com.bbu.cl.utils.StringUtil;

/**
 * 年级信息管理
 * @author 疯人愿
 *
 */
@RequestMapping("/grade")
@Controller
public class GradeController {
	@Autowired
	private GradeService gradeService;
	/**
	 * 年级管理列表页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("grade/grade_list");
		return model;
	}
	/**
	 * 获取年级列表
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/get_list",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getList(
			@RequestParam(value = "name",required = false,defaultValue = "") String name,
			Page page
			){
		Map<String, Object> ret = new HashMap<String , Object>();
		Map<String, Object> queryMap = new HashMap<String , Object>();
		queryMap.put("name", "%"+name+"%");//%%表示为空
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", gradeService.findList(queryMap));//存放查询的列表
		ret.put("total", gradeService.getTotal(queryMap));//一页记录的数量
		return ret;
	}
	/**
	 * 添加年级
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Grade grade){
		Map<String, String> ret = new HashMap<String , String>();
		//进行判断
		
		if (StringUtils.isEmpty(grade.getName())) {
			ret.put("type", "error");
			ret.put("msg", "年级名不能为空");
			return ret;
		}
		
		
		if (gradeService.add(grade)<=0) {
			ret.put("type", "error");
			ret.put("msg", "年级添加失败");
			return ret;
		}
		//添加年级成功
		ret.put("type", "success");
		ret.put("msg", "添加成功");
		return ret;
	}
	/**
	 * 修改年级
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Grade grade){
		Map<String, String> ret = new HashMap<String , String>();
		//进行判断
		
		if (StringUtils.isEmpty(grade.getName())) {
			ret.put("type", "error");
			ret.put("msg", "年级名不能为空");
			return ret;
		}
		
		
		if (gradeService.edit(grade)<=0) {
			ret.put("type", "error");
			ret.put("msg", "年级修改失败");
			return ret;
		}
		//修改年级成功
		ret.put("type", "success");
		ret.put("msg", "修改成功");
		return ret;
	}
	/**
	 * 删除年级
	 * @param grade
	 * @return
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(
			@RequestParam(value = "ids[]",required = true) Long[] ids
			){
		Map<String, String> ret = new HashMap<String , String>();
		if (ids == null || ids.length == 0) {
			ret.put("type", "error");
			ret.put("msg", "请选择要删除的数据");
			return ret;
		}try {
			if (gradeService.delete(StringUtil.joinString(Arrays.asList(ids), ","))<=0) {
				ret.put("type", "error");
				ret.put("msg", "删除失败");
				return ret;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该年级下存在班级，请勿冲动");
			return ret;
			
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功");
		return ret;
	}
		
}
