package xin.mengzuo.customer.config;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//@ControllerAdvice
public class MyException {
	
	 @Autowired
	 private JavaMailSender javaMailSender;
	@ResponseBody
    @ExceptionHandler(value = Exception.class)
	 public TtmsResult exceptionHandler(Exception ex){ 
		 sendSimpleMail(ex.toString());
		return TtmsResult.build(400, "网速不好，请稍后再试");


		 }
	 public void sendSimpleMail(String local){
	        MimeMessage message = null;
	        try {
	            message = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
	            helper.setFrom("1203224763@qq.com");
	            helper.setTo("1052122348@qq.com");
	            helper.setSubject("大人你的系统出问题了");

	            StringBuffer sb = new StringBuffer();
	            sb.append("<h1>出问题了</h1>")
	                    .append(local);
	                    
	            helper.setText(sb.toString(), true);
	            javaMailSender.send(message);
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	    } 
	
}
