package xin.mengzuo.customer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xin.mengzuo.customer.pojo.User;

/**
 * @author 左利伟
 */
@Repository
public interface LoginDao extends JpaRepository<User, Integer>{
 
	public User findByEmail(String email);
}