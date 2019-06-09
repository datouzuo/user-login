package xin.mengzuo.customer.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;

import xin.mengzuo.customer.config.TtmsResult;

/**
 * @author 左利伟
 */
public interface LoginService {
	public TtmsResult login(String email, String password,HttpServletRequest request, HttpServletResponse response)throws JsonProcessingException;
}
