package xin.mengzuo.customer.serviceImp;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Jedis;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisCluster;
import xin.mengzuo.customer.config.CookieUtils;
import xin.mengzuo.customer.config.TtmsResult;
import xin.mengzuo.customer.dao.LoginDao;
import xin.mengzuo.customer.pojo.User;
import xin.mengzuo.customer.service.LoginService;

/**
 * @author 左利伟
 */
@Service
@Transactional
public class LoginServiceImp implements LoginService {
	@Autowired
	private LoginDao ld;
	@Autowired
	private JedisCluster cluster;
	
	@Autowired
	private ObjectMapper json;
	public TtmsResult login(String email, String password,HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
		User user = ld.findByEmail(email);
		String pString = DigestUtils.md5DigestAsHex(password.getBytes());
		// 密码比对
		if(user!=null&&user.getActive()==1) {
		if (pString.equals(user.getPassword())) {
			String session = UUID.randomUUID().toString();
			user.setPassword(null);
			// 将session写到redis集群
			cluster.set("tokenId:" + session, json.writeValueAsString(user));
			cluster.expire("tokenId:" + session, 1800);
			CookieUtils.setCookie(request, response, "tokenId", session);
	       return TtmsResult.build(200, "登录成功");
		} 
		}	return TtmsResult.build(400, "用户名或密码错误");
		
	}
}
