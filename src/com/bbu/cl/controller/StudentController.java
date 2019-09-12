package com.bbu.cl.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bbu.cl.entity.Clazz;
import com.bbu.cl.entity.Grade;
import com.bbu.cl.entity.Student;
import com.bbu.cl.page.Page;
import com.bbu.cl.service.ClazzService;
import com.bbu.cl.service.StudentService;
import com.bbu.cl.utils.StringUtil;

import net.sf.json.JSONArray;

/**
 * 学生信息管理
 * @author 疯人愿
 *
 */
@RequestMapping("/student")
@Controller
public class StudentController {
	@Autowired
	private ClazzService clazzService;
	@Autowired
	private StudentService studentService;
	/**
	 * 学生管理列表页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("student/student_list");
		List<Clazz> clazzList = clazzService.findAll();
		model.addObject("clazzList", clazzList);
		model.addObject("clazzListJson", JSONArray.fromObject(clazzList));
		return model;
	}
	/**
	 * 获取学生列表
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/get_list",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getList(
			@RequestParam(value = "name",required = false,defaultValue = "") String name,
			@RequestParam(value = "clazzId",required = false) Long clazzId,
			HttpServletRequest request,
			Page page
			){
		Map<String, Object> ret = new HashMap<String , Object>();
		Map<String, Object> queryMap = new HashMap<String , Object>();
		queryMap.put("username", "%"+name+"%");//%%表示为空
		Object attribute = request.getSession().getAttribute("userType");
		//判断是否是学生
		if ("2".equals(attribute.toString())) {
			//说明是学生
			Student loginStudent = (Student)request.getSession().getAttribute("user");
			queryMap.put("username", "%"+loginStudent.getUsername()+"%");//%%表示为空
		}
		if (clazzId != null) {
			queryMap.put("clazzId", clazzId);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", studentService.findList(queryMap));//存放查询的列表
		ret.put("total", studentService.getTotal(queryMap));//一页记录的数量
		return ret;
	}
	/**
	 * 添加学生信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Student student){
		Map<String, String> ret = new HashMap<String , String>();
		//进行判断
		
		if (StringUtils.isEmpty(student.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "学生姓名不能为空");
			return ret;
		}

		if (StringUtils.isEmpty(student.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "登录密码不能为空");
			return ret;
		}
		if (student.getClazzId() == null) {
			ret.put("type", "error");
			ret.put("msg", "请选择所属班级");
			return ret;
		}
		if (isExist(student.getUsername(), null)) {
			ret.put("type", "error");
			ret.put("msg", "该姓名已存在！");
			return ret;
		}
		//设置学号的前缀和后缀
		student.setSn(StringUtil.generateSn("516", ""));
		if (studentService.add(student)<=0) {
			ret.put("type", "error");
			ret.put("msg", "学生添加失败");
			return ret;
		}
		//添加学生成功
		ret.put("type", "success");
		ret.put("msg", "添加成功");
		return ret;
	}
	//判断用户名是否存在
	private boolean isExist(String username,Long id) {
		Student student = studentService.findByUserName(username);
		if (student != null) {
			if (id == null) {
				//表示该用户是新增用户
				return true;
			}
			if (student.getId().longValue() != id.longValue()) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 上传头像
	 * @throws IOException 
	 */
	@RequestMapping(value = "/upload_photo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> uploadPhoto(MultipartFile photo,
			HttpServletRequest request,
			HttpServletResponse response
			) throws IOException{
		Map<String, String> ret = new HashMap<String , String>();
		if (photo == null) {
			//文件没有选择
			ret.put("type", "error");
			ret.put("msg", "请选择文件！");
			return ret;
		}
		//判断文件大小
		if (photo.getSize()>10485760) {
			ret.put("type", "error");
			ret.put("msg", "文件大小超过10M，请上传小于10M的图片！");
			return ret;
		}
		//获取图片的后缀 最后一个.后面的字符串
		String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")+1,photo.getOriginalFilename().length());
		//判断是否是图片
		if (!"jpg,png,gif,jpeg".contains(suffix.toLowerCase())) {
			ret.put("type", "error");
			ret.put("msg", "文件格式不正确，请上传jpg,png,gif,jpeg格式的图片！");
			return ret;
			}
		//获取图片的绝对路径
		String savePath = request.getServletContext().getRealPath("/") + "\\upload\\";
		System.out.println(savePath);
		File savePathFile = new File(savePath);
		//如果不存在，则创建一个文件夹upload
		if (!savePathFile.exists()) {
			savePathFile.mkdir();
		}
		//把文件转存到这个文件夹下
		String fileName = new Date().getTime() + "." + suffix;
		photo.transferTo(new File( savePath + fileName));
		ret.put("type", "success");
		ret.put("msg", "图片上传成功！");
		//返回图片的路径
		ret.put("src", request.getServletContext().getContextPath() + "/upload/" +fileName);		
		return ret;
	}
	/**
	 * 修改学生
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Student student){
		Map<String, String> ret = new HashMap<String , String>();
		//进行判断
		if (StringUtils.isEmpty(student.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "学生姓名不能为空");
			return ret;
		}

		if (StringUtils.isEmpty(student.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "登录密码不能为空");
			return ret;
		}
		if (student.getClazzId() == null) {
			ret.put("type", "error");
			ret.put("msg", "请选择所属班级");
			return ret;
		}
		if (isExist(student.getUsername(), student.getId())) {
			ret.put("type", "error");
			ret.put("msg", "该姓名已存在！");
			return ret;
		}
		//设置学号的前缀和后缀
		student.setSn(StringUtil.generateSn("516", ""));
		if (studentService.edit(student)<=0) {
			ret.put("type", "error");
			ret.put("msg", "学生添加失败");
			return ret;
		}
		//修改学生成功
		ret.put("type", "success");
		ret.put("msg", "添加成功");
		return ret;
	}
	/**
	 * 删除学生
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
		}
			if (studentService.delete(StringUtil.joinString(Arrays.asList(ids), ","))<=0) {
				ret.put("type", "error");
				ret.put("msg", "删除失败");
				return ret;
			}
		ret.put("type", "success");
		ret.put("msg", "删除成功");
		return ret;
	}
}
