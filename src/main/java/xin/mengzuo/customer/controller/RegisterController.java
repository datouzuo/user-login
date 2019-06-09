package xin.mengzuo.customer.controller;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xin.mengzuo.customer.config.TtmsResult;
import xin.mengzuo.customer.config.VerifyUtil;
import xin.mengzuo.customer.pojo.User;
import xin.mengzuo.customer.service.RegisterService;
/**
 * 
 * @author 左利伟
 *
 */
@RestController
public class RegisterController {
	private  final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private RegisterService rese;
    //注册
	@RequestMapping(value="/user/registerservice",method=RequestMethod.POST)
	public TtmsResult registerSave(@RequestBody User user,HttpServletRequest request,@RequestBody String revix) {
		
		boolean b=false;
			if (request.getSession().getAttribute("revix").equals(revix)) {
				 b= rese.register(user, request.getLocalAddr() + ":" + request.getRemotePort());
			
		}
			if(b)
		   return TtmsResult.build(200, "成功");
			
			return TtmsResult.build(400, "数据重复");
	}
	//动态验证是否信息是否重复
	@RequestMapping(value="/user/registercheck",method=RequestMethod.GET)
	public TtmsResult registerCheck(String msg ,int type) {
	
		boolean b = rese.checkSave(msg,type);
		if(!b) {
			return TtmsResult.build(200, "成功");
		}else {
			return TtmsResult.build(400, "数据重复");
		}
	}	
	//激活账号
	@RequestMapping(value="/user/mail/{phone}")
	public String mailPhone(@PathVariable String phone) {
		rese.mailPhoneActive(phone);
		return "激活成功";
	}
	//生成验证码
	
    @GetMapping("/getcode")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws Exception{
        HttpSession session=request.getSession();
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.createImage();
        //将验证码存入Session
        session.setAttribute("revix",objs[0]);
         System.out.println(objs[0]);
        //将图片输出给浏览器
         System.out.println(request.getSession().getAttribute("revix"));
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }
	
}
