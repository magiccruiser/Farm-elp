/**
 * 
 */
package com.nagarro.productcommunity.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.productcommunity.dao.UserDao;
import com.nagarro.productcommunity.model.Login;
import com.nagarro.productcommunity.model.User;

/**
 * Class to handle the services related to users
 * @author pranshusahu
 *
 */

@Service
public class UserService
{
	@Autowired
	UserDao userDao;
	
	/**
	 * returns all users
	 * @return
	 */
	public List<User> getAllUsers()
	{
		List<User> allUsers=userDao.findAll();
		return allUsers;
	}
	
	
	/**
	 * saves registered user
	 * @param user
	 * @return
	 * @throws SQLIntegrityConstraintViolationException
	 */
	public User registerUser(User user) throws SQLIntegrityConstraintViolationException
	{
		List<User> allUsers=getAllUsers();
		for(User tempUser:allUsers)
		{
			if(user.getEmail().equals(tempUser.getEmail()))
			{
				throw new SQLIntegrityConstraintViolationException();
			}
		}
		return userDao.save(user);
	}
	
	
	/**
	 * returns user data after authentication
	 * @param loginData
	 * @return
	 * @throws EntityNotFoundException
	 */
	public User userAuth(Login loginData)throws EntityNotFoundException
	{
		try
		{
			User gotUser=userDao.getById(loginData.getEmail());
			if(gotUser.getPassword().equals(loginData.getPassword()))
			{
				return gotUser;
			}
			else 
			{
				throw new EntityNotFoundException("Password Incorrect");
			}
		}
		catch(EntityNotFoundException e)
		{
			throw new EntityNotFoundException("No user exist with this email id");
		}	
	}
	
	
	/**
	 * Get the user data with help of emailID
	 * @param email
	 * @return
	 * @throws EntityNotFoundException
	 */
	public User getUserByEmail(String email)throws EntityNotFoundException
	{
		return userDao.getById(email);
	}
	
	/**
	 * returns the no. of users
	 * @return
	 */
	public int getUsersCount()
	{
		int usersCount=(int) userDao.count();
		return usersCount;
	}
	
	
	/**
	 * returns all user names
	 * @return
	 */
	public List<String> getAllUserNames()
	{
		return userDao.findUserNames();
	}
	
	
	
	
	
}
