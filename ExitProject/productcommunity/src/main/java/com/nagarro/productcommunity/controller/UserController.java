package com.nagarro.productcommunity.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.productcommunity.model.Login;
import com.nagarro.productcommunity.model.User;
import com.nagarro.productcommunity.service.UserService;

/**
 * Controller to handle the login and register related activities
 * @author pranshusahu
 *
 */
@RestController
@CrossOrigin(origins = "*")
public class UserController
{
	@Autowired
	UserService userService;
	
	/**
	 * Registers the user
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/register",consumes = "application/json")
	public ResponseEntity<JSONObject> createUser(@RequestBody User user)
	{
		try 
		{
			//User created and saved in database successfully 
			userService.registerUser(user);
			JSONObject userDetails=new JSONObject();
			userDetails.put("email", user.getEmail());
			userDetails.put("firstName", user.getFirstName());
			userDetails.put("lastName", user.getLastName());
			return new ResponseEntity<>(userDetails,HttpStatus.OK);
		}
		catch(SQLIntegrityConstraintViolationException e)
		{
			// User with existing email want to register
		    return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	
	/**
	 * Logins the user
	 * @param loginData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/login",consumes = "application/json")
	public ResponseEntity<JSONObject> loginUser(@RequestBody Login loginData)
	{
		User loggedInUser=null;
		try
		{
			loggedInUser=userService.userAuth(loginData); 
		}
		catch(EntityNotFoundException e)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		JSONObject userDetails=new JSONObject();
		userDetails.put("email", loggedInUser.getEmail());
		userDetails.put("firstName", loggedInUser.getFirstName());
		userDetails.put("lastName", loggedInUser.getLastName());
		return new ResponseEntity<>(userDetails,HttpStatus.OK);
		
	}
	
	
	/**
	 * returns all the user names
	 * @return
	 */
	@GetMapping(value="/users")
	public ResponseEntity<?> getAllUsers()
	{
	//List<String> userNames=userService.getAllUserNames();
		Set<String> userNames = new LinkedHashSet<String>(userService.getAllUserNames());
		return new ResponseEntity<>(userNames,HttpStatus.OK);
	}
	
	
	/**
	 * returns the user data
	 * @param email
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value="/userData/{email}")
	public ResponseEntity<JSONObject> getUserData(@PathVariable String email){
		User loggedInUser;
		try {
			loggedInUser = userService.getUserByEmail(email);
		}
		//Email id is not registered
		catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		JSONObject userDetails=new JSONObject();
		userDetails.put("email", loggedInUser.getEmail());
		userDetails.put("firstName", loggedInUser.getFirstName());
		userDetails.put("lastName", loggedInUser.getLastName());
		return new ResponseEntity<>(userDetails,HttpStatus.OK);
	}
	
}