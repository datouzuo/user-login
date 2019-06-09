package xin.mengzuo.customer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import xin.mengzuo.customer.config.CookieUtils;
import xin.mengzuo.customer.config.TtmsResult;
import xin.mengzuo.customer.service.LoginService;
import xin.mengzuo.customer.serviceImp.LoginServiceImp;

/**
 * 
 * @author 左利伟 
 * 登录模块
 */
@RestController
public class LoginController {
	@Autowired
	private LoginService ls;

	@RequestMapping(value = "/user/login",method=RequestMethod.POST)
	@ResponseBody
	public TtmsResult register(@RequestBody String email, @RequestBody String password, HttpServletRequest request, HttpServletResponse response,
			@RequestBody String revix) throws Exception {
         System.out.println(revix);
         System.out.println(request.getSession().getAttribute("revix"));
		if (request.getSession().getAttribute("revix")!=null&&revix!=null)
				if(revix.equals(request.getSession().getAttribute("revix"))) {
			TtmsResult tm = ls.login(email, password,request,response);
			return tm;
		} 
		
			return TtmsResult.build(400, "验证码错误");
		
		
	}
}
