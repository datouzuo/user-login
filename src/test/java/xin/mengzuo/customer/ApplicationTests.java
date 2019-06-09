package xin.mengzuo.customer;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisCluster;
import xin.mengzuo.customer.pojo.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private  StringRedisTemplate jc;
	@Autowired
	private ObjectMapper json;
	@Autowired
	private JedisCluster cluster;
	

	@Test
	public void contextLoads() throws JsonProcessingException {
	
		User use = new User();
		
		use.setEmail("1203");
		cluster.set("tokenId:" +  1231466, json.writeValueAsString(use));
		cluster.expire("tokenId:" +  1231466, 1800);
	}
	@Test
	public void test() throws JsonProcessingException {
		User use = new User();
		use.setUserid(123);
		use.setEmail("1203");
		System.out.println(json.writeValueAsString(use));
	}
	
}
