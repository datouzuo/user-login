package xin.mengzuo.customer.serviceImp;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.alibaba.druid.support.spring.stat.SpringStatUtils;

import xin.mengzuo.customer.dao.RegisterDao;
import xin.mengzuo.customer.pojo.User;
import xin.mengzuo.customer.service.RegisterService;
/**
 * 注册与检查数据库是否重复
 * 1.用户名2.email3.phone
 * @author 左利伟
 *
 */
@Service
@Transactional
public class RegisterServiceImp implements RegisterService {
 @Autowired
 private RegisterDao redao;
 @Autowired
 private JavaMailSender javaMailSender;	
	
	@Override
	public boolean register(User user,String local) {
	   boolean check;
	   check = checkSave(user.getUsername(), 1);
	   if(check) {
		   return false;
	   }
	   check = checkSave(user.getEmail(), 2);
	   if(check) {
		   return false;
	   }
	   check = checkSave(user.getPhone(), 3);
	   if(check) {
		   return false;
	   }
	   String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
	   user.setPassword(password);
	   user.setActive(0);
	   redao.save(user);
	   sendSimpleMail(user.getEmail(),local,user.getPhone());
		return true;
	}

	@Override
	public boolean checkSave(String msg, int type) {
		User user = null;
		if(type==1) {
			
	     user = redao.findByUsername(msg);
	     if(user!=null)
	     return true;
		}
		if(type==2) {
			  user = redao.findByEmail(msg);
			     if(user!=null)
			    	 return true;
		}
		if(type==3) {
			  user = redao.findByPhone(msg);
			  if(user!=null)
			    	 return true;
		}
		
		return false;
	}
	//发送邮件激活账号
	 public void sendSimpleMail(String mail,String local,String phone){
	        MimeMessage message = null;
	        try {
	            message = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
	            helper.setFrom("1203224763@qq.com");
	            helper.setTo(mail);
	            helper.setSubject("点击连接激活账号");

	            StringBuffer sb = new StringBuffer();
	            sb.append("<h1>点击激活账号</h1>")
	                    .append("<a herf="+local+"/user/mail/"+phone+">");
	                    
	            helper.setText(sb.toString(), true);
	            javaMailSender.send(message);
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	    }
//激活账号
	 public void mailPhoneActive(String phone) {
		User user = redao.findByPhone(phone);
		 user.setActive(1);
	 }
}
