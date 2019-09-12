package com.bbu.cl.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bbu.cl.entity.Clazz;
import com.bbu.cl.entity.Grade;
import com.bbu.cl.entity.Clazz;
import com.bbu.cl.page.Page;
import com.bbu.cl.service.ClazzService;
import com.bbu.cl.service.GradeService;
import com.bbu.cl.utils.StringUtil;

import net.sf.json.JSONArray;

/**
 * 班级信息管理
 * @author 疯人愿
 *
 */
@RequestMapping("/clazz")
@Controller
public class ClazzController {
	@Autowired
	private ClazzService clazzService;
	@Autowired
	private GradeService gradeService;
	/**
	 * 班级管理列表页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("clazz/clazz_list");
		//向下拉框传入参数
		List<Grade> findAll = gradeService.findAll();
		model.addObject("gradeList", findAll);
		model.addObject("gradeListJson", JSONArray.fromObject(findAll));
		return model;
	}
	/**
	 * 获取班级列表
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/get_list",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getList(
			@RequestParam(value = "name",required = false,defaultValue = "") String name,
			@RequestParam(value = "gradeId",required = false) Long gradeId,
			Page page
			){
		Map<String, Object> ret = new HashMap<String , Object>();
		Map<String, Object> queryMap = new HashMap<String , Object>();
		queryMap.put("name", "%"+name+"%");//%%表示为空
		if (gradeId != null) {
			queryMap.put("gradeId", gradeId);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", clazzService.findList(queryMap));//存放查询的列表
		ret.put("total", clazzService.getTotal(queryMap));//一页记录的数量
		return ret;
	}
	/**
	 * 添加班级信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Clazz clazz){
		Map<String, String> ret = new HashMap<String , String>();
		//进行判断
		
		if (StringUtils.isEmpty(clazz.getName())) {
			ret.put("type", "error");
			ret.put("msg", "班级名不能为空");
			return ret;
		}
		if (clazz.getGradeId() == null) {
			ret.put("type", "error");
			ret.put("msg", "请选择所属年级");
			return ret;
		}
		
		if (clazzService.add(clazz)<=0) {
			ret.put("type", "error");
			ret.put("msg", "班级添加失败");
			return ret;
		}
		//添加班级成功
		ret.put("type", "success");
		ret.put("msg", "添加成功");
		return ret;
	}
	/**
	 * 修改班级
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Clazz clazz){
		Map<String, String> ret = new HashMap<String , String>();
		//进行判断
		
		if (StringUtils.isEmpty(clazz.getName())) {
			ret.put("type", "error");
			ret.put("msg", "班级名不能为空");
			return ret;
		}
		if (clazz.getGradeId() == null) {
			ret.put("type", "error");
			ret.put("msg", "所属年级不能为空");
			return ret;
		}
		if (clazzService.edit(clazz)<=0) {
			ret.put("type", "error");
			ret.put("msg", "班级修改失败");
			return ret;
		}
		//修改班级成功
		ret.put("type", "success");
		ret.put("msg", "修改成功");
		return ret;
	}
	/**
	 * 删除班级
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
			if (clazzService.delete(StringUtil.joinString(Arrays.asList(ids), ","))<=0) {
				ret.put("type", "error");
				ret.put("msg", "删除失败");
				return ret;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该班级下存在学生，请勿冲动");
			return ret;
			
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功");
		return ret;
	}
		
}
