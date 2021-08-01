package com.nagarro.productcommunity.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.productcommunity.service.QuestionService;
import com.nagarro.productcommunity.service.UserService;

/**
 * controller to handles the stats of the tables
 * @author pranshusahu
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class StatsController 
{
	
	@Autowired
	UserService userService;
	
	@Autowired
	QuestionService questionService;
	
	
	@SuppressWarnings("unchecked")
	@GetMapping(value="/stats")
	public ResponseEntity<JSONObject> getStats()
	{
		int registeredUsers=userService.getUsersCount();
		int questionsRaised=questionService.getQuestionsCount();
		int questionsSolved=questionService.solvedQuestionsCount();
		
		JSONObject statsJsonObj=new JSONObject();
		
		statsJsonObj.put("registeredUser", registeredUsers);
		statsJsonObj.put("questionsRaised", questionsRaised);
		statsJsonObj.put("questionsSolved", questionsSolved);
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.add("Access-Control-Allow-Origin", "http://localhost:4200");

		
		return new ResponseEntity<>(statsJsonObj,HttpStatus.OK);

	}
}
