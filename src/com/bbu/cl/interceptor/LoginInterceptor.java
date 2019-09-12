package com.bbu.cl.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
/**
 * 登陆过滤拦截器
 * @author 疯人愿
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	//请求完成后
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	//请求正在发生
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	//请求发生前
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String url = request.getRequestURI();
		//System.out.println("进入拦截器，url="+url);
		//获得session
	Object user = request.getSession().getAttribute("user");
		if (user == null) {
			//表示未登陆或者登录状态失效
			System.out.println("未登录或者登录失效，url="+url);
			//判断是否为ajax请求
			if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				//ajax请求
				Map<String, String> ret = new HashMap<String , String>();
				ret.put("type", "error");
				ret.put("msg", "登录状态已失效，请重新登录！");
				response.getWriter().write(JSONObject.fromObject(ret).toString());
				return false;
			};
			
			//转发页面 首页的根目录+/system/login
			response.sendRedirect(request.getContextPath()+"/system/login");
			return false;
		}
		return true;
	}

}
