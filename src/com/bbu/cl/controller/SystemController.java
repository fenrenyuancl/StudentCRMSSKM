package com.bbu.cl.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bbu.cl.entity.Student;
import com.bbu.cl.entity.User;
import com.bbu.cl.service.StudentService;
import com.bbu.cl.service.UserService;
import com.bbu.cl.utils.CpachaUtil;

import javafx.beans.binding.StringBinding;


@RequestMapping("/system")
@Controller	
public class SystemController {

	@Autowired
	private UserService userService;
	@Autowired
	private StudentService studentService;
//主界面
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model) {
		model.setViewName("system/index");
		return model;
	}	
//登陆界面
@RequestMapping(value = "/login",method = RequestMethod.GET)
public ModelAndView login(ModelAndView model) {
	model.setViewName("system/login");
	return model;
}

//登录表单提交
@RequestMapping(value = "/login",method = RequestMethod.POST)
@ResponseBody //将ret对象装化成json字符串对象返回
public Map<String, String> login(
		//登录必须携带以下四个参数，required为true，否则无法登陆
		@RequestParam(value="username",required=true) String username,
		@RequestParam(value="password",required=true) String password,
		@RequestParam(value="vcode",required=true) String vcode,
		@RequestParam(value="type",required=true) int type,
		HttpServletRequest request
		) {
	Map<String,String> ret = new HashMap<String,String>();
	//对参数进行判断
	if (StringUtils.isEmpty(username)) {
		ret.put("type","error");
		ret.put("msg","用户名不能为空!");
		return ret;
	}
	if (StringUtils.isEmpty(password)) {
		ret.put("type","error");
		ret.put("msg","密码不能为空!");
		return ret;
	}
	if (StringUtils.isEmpty(vcode)) {
		ret.put("type","error");
		ret.put("msg","验证码不能为空!");
		return ret;
	}
	
	String loginCpacha = (String)request.getSession().getAttribute("loginCpacha");
	if (StringUtils.isEmpty(loginCpacha)) {
		ret.put("type","error");
		ret.put("msg","长时间未操作，请刷新后重试!");
		return ret;
	}
	if (!vcode.toUpperCase().equals(loginCpacha.toUpperCase())) {
		ret.put("type","error");
		ret.put("msg","验证码错误");
		return ret;
	}
	request.getSession().setAttribute("loginCpacha", null);
	//从数据库中查找用户
	User user = userService.findByUserName(username);
	if (type == 1) {
		//管理员
		if (user == null) {
			ret.put("type","error");
			ret.put("msg","不存在该用户!");
			return ret;
		}
		if (!password.equals(user.getPassword())) {
			ret.put("type","error");
			ret.put("msg","密码错误!");
			return ret;
		}
		request.getSession().setAttribute("user", user);
	}
	if (type == 2) {
		//学生
		Student student = studentService.findByUserName(username);
		if (student == null) {
			ret.put("type","error");
			ret.put("msg","不存在该学生!");
			return ret;
		}
		if (!password.equals(student.getPassword())) {
			ret.put("type","error");
			ret.put("msg","密码错误!");
			return ret;
		}
		request.getSession().setAttribute("user", student);
	}
	request.getSession().setAttribute("userType", type);
	
	ret.put("type","success");
	ret.put("msg","登陆成功!");
	return ret;
}

//显示验证码
@RequestMapping(value = "/get_cpacha",method = RequestMethod.GET)
public void getcpacha(HttpServletRequest request ,
		@RequestParam(value="v1",defaultValue="4",required=false) Integer v1,
		@RequestParam(value="w",defaultValue="98",required=false) Integer w,
		@RequestParam(value="h",defaultValue="33",required=false) Integer h,
		HttpServletResponse response) {
	//新建一个验证码对象，传入参数
	CpachaUtil cpachaUtil = new CpachaUtil(v1,w,h);
	//生成一个验证码
	String generatorVCode = cpachaUtil.generatorVCode();
	//获取session，将验证码信息传入session
	request.getSession().setAttribute("loginCpacha", generatorVCode);
	//生成一个验证码图片 true表示画干扰线
	BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
	
	try {
		ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	//退出登录功能
	@RequestMapping(value = "/login_out",method = RequestMethod.GET)
	public String  loginOut(HttpServletRequest request) {
		request.getSession().setAttribute("user", null);
		return "redirect:login";
	}	

}
