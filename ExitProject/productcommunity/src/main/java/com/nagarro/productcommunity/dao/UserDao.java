package com.nagarro.productcommunity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nagarro.productcommunity.model.User;

/**
 * 
 * @author pranshusahu
 *
 */
public interface UserDao extends JpaRepository<User,String>
{

	
	@Query("SELECT firstName FROM User")
	List<String> findUserNames();
}
