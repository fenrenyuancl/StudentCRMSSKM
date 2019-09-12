package com.bbu.cl.controller;

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

import com.bbu.cl.entity.User;
import com.bbu.cl.page.Page;
import com.bbu.cl.service.UserService;

/**
 * 用户（管理员）控制器
 * @author 疯人愿
 *
 */
@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	public UserService userService;
	/**
	 * 用户管理列表页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("user/user_list");
		return model;
	}
	/**
	 * 获取用户列表
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/get_list",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getList(
			@RequestParam(value = "username",required = false,defaultValue = "") String username,
			Page page
			){
		Map<String, Object> ret = new HashMap<String , Object>();
		Map<String, Object> queryMap = new HashMap<String , Object>();
		queryMap.put("username", "%"+username+"%");//%%表示为空
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", userService.findList(queryMap));//存放查询的列表
		ret.put("total", userService.getTotal(queryMap));//一页记录的数量
		return ret;
	}
	
	/**
	 * 添加用户
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(User user){
		Map<String, String> ret = new HashMap<String , String>();
		//进行判断
		if (user == null) {
			//user为空
			ret.put("type", "error");
			ret.put("msg", "数据绑定出错");
			return ret;
		}
		if (StringUtils.isEmpty(user.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "用户名不能为空");
			return ret;
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "密码不能为空");
			return ret;
		}
		User existUser = userService.findByUserName(user.getUsername());
		if (existUser != null) {
			ret.put("type", "error");
			ret.put("msg", "用户名已存在");
			return ret;
		}
		if (userService.add(user)<=0) {
			ret.put("type", "error");
			ret.put("msg", "添加失败");
			return ret;
		}
		//添加管理员用户成功
		ret.put("type", "success");
		ret.put("msg", "添加成功");
		return ret;
	}
	/**
	 * 修改用户
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(User user){
		Map<String, String> ret = new HashMap<String , String>();
		//进行判断
		if (user == null) {
			//user为空
			ret.put("type", "error");
			ret.put("msg", "数据绑定出错");
			return ret;
		}
		if (StringUtils.isEmpty(user.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "用户名不能为空");
			return ret;
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "密码不能为空");
			return ret;
		}
		User existUser = userService.findByUserName(user.getUsername());
		if (existUser != null) {
			if (user.getId() != existUser.getId()) {
				ret.put("type", "error");
				ret.put("msg", "用户名已存在");
				return ret;
			}
		}
		if (userService.edit(user)<=0) {
			ret.put("type", "error");
			ret.put("msg", "修改失败");
			return ret;
		}
		//添加管理员用户成功
		ret.put("type", "success");
		ret.put("msg", "修改成功");
		return ret;
	}


	/**
	 * 删除用户
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(
			@RequestParam(value = "ids[]",required = true) Long[] ids
			){
		Map<String, String> ret = new HashMap<String , String>();
		if (ids == null) {
			ret.put("type", "error");
			ret.put("msg", "请选择要删除的数据");
			return ret;
		}
		String idsString = "";
		for(Long id:ids) {
			idsString += id + ",";
		}
		idsString = idsString.substring(0, idsString.length()-1);
		if (userService.delete(idsString)<=0) {
			ret.put("type", "error");
			ret.put("msg", "删除失败");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功");
		return ret;
	}
		
}

