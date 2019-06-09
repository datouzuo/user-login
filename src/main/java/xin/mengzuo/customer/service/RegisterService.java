package xin.mengzuo.customer.service;

import xin.mengzuo.customer.pojo.User;

public interface RegisterService {
	
boolean register(User user,String local) ;
boolean checkSave(String msg ,int type);
public void mailPhoneActive(String phone);

}
