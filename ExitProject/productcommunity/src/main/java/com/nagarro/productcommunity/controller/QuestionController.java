package com.nagarro.productcommunity.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.json.simple.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.productcommunity.model.Comment;
import com.nagarro.productcommunity.model.Question;
import com.nagarro.productcommunity.model.User;
import com.nagarro.productcommunity.service.CommentService;
import com.nagarro.productcommunity.service.QuestionService;
import com.nagarro.productcommunity.service.UserService;

import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;

import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

/**
 * Controller class for handling questions
 * @author pranshusahu
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionController {

	@Autowired
	UserService userService;

	@Autowired
	QuestionService questionService;

	@Autowired
	CommentService commentService;

	/**
	 * raise a new question
	 * @param userEmail
	 * @param question
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("unused")
	@PostMapping(value = "/raiseQuestion", consumes = "application/json")
	public ResponseEntity<?> raiseQuestion(@RequestParam("userEmail") String userEmail, @RequestBody Question question)
			throws ParseException {
		User loggedInUser;
		try {
			loggedInUser = userService.getUserByEmail(userEmail);
		}
		// Email id is not registered
		catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		System.out.println(question.getQuestionText());
		//question.setProductCode(questionService.getProductCode(loggedInUser).toString());
		question.setUser(loggedInUser);
		question.setUserEmail(loggedInUser.getEmail());
		question.setUserFirstName(loggedInUser.getFirstName());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		question.setCreatedDate(formatter.format(new Date()));
		try {
			Question addedQuestion = questionService.postNewQuestion(question);
		} catch (SQLIntegrityConstraintViolationException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	/**
	 * sends all the questions raised by the user
	 * @param userEmail
	 * @return
	 */
	@GetMapping(value = "/user/my-questions")
	public ResponseEntity<JSONArray> userQuestions(@RequestParam("userEmail") String userEmail) {
		User loggedInUser = null;
		try {
			loggedInUser = userService.getUserByEmail(userEmail);
		}
		// Email id is not registered
		catch (EntityNotFoundException e) {
			System.out.println("hey");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		List<Question> userQuestions = new ArrayList<>(loggedInUser.getQuestions());
		userQuestions
				.sort((Question ques1, Question ques2) -> ques2.getCreatedDate().compareTo(ques1.getCreatedDate()));
		JSONArray questionsByUser = questionService.listToJsonArray(userQuestions);
		System.out.println(questionsByUser);
		return new ResponseEntity<>(questionsByUser, HttpStatus.OK);
	}

	
	/**
	 * this function filters the question with respect to specification
	 * @param spec
	 * @return
	 */
	@GetMapping(value = "/questions/filter")
	public ResponseEntity<?> filterQuestions(@And({
			@Spec(path = "userFirstName", params = "userFirstName", spec = Equal.class),
			@Spec(path = "productLabel", params = "productLabel", spec = Equal.class),
			@Spec(path = "createdDate", params = "createdDate", spec = Equal.class), }) Specification<Question> spec) {
		List<Question> result = questionService.getQuestionsBySearchParameters(spec);
		System.out.println(result);

		JSONArray searchResult = questionService.listToJsonArray(questionService.getQuestionsBySearchParameters(spec));
		System.out.println(searchResult);

		return new ResponseEntity<>(searchResult, HttpStatus.OK);
	}

	
	/**
	 * this function searches the question with respect to specification
	 * @param spec
	 * @return
	 */
	@GetMapping(value = "/questions/search")
	public ResponseEntity<JSONArray> searchQuestions(@Or({
			@Spec(path = "questionText", params = "searchText", spec = Like.class),
			@Spec(path = "productCode", params = "searchText", spec = Like.class),
			@Spec(path = "userEmail", params = "searchText", spec = Like.class),
			@Spec(path = "productLabel", params = "searchText", spec = Like.class),
			@Spec(path = "createdDate", params = "searchText", spec = Like.class), }) Specification<Question> spec) {
		JSONArray searchResult = questionService.listToJsonArray(questionService.getQuestionsBySearchParameters(spec));
		System.out.println(searchResult);

		return new ResponseEntity<>(searchResult, HttpStatus.OK);
	}

	
	/**
	 * function adds the comment to the particular question	
	 * @param commentId
	 * @return
	 */
	@PutMapping(value = "/questions/answer")
	public ResponseEntity<?> markCommentAsAnswer(@RequestParam("commentId") int commentId) {
		Comment comment = null;
		try {
			comment = commentService.getCommentById(commentId);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
		}

		Question question = comment.getQuestion();
		if (question.getAnswer() == true) {
			return new ResponseEntity<>("Question already answered", HttpStatus.CONFLICT);
		}
		comment.setAnswer(true);
		commentService.updateComment(comment);
		question.setAnswer(true);
		questionService.updateQuesion(question);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	/**
	 * this function adds likes to the questions
	 * @param userEmail
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings("unused")
	@PutMapping(value = "/questions/like")
	public ResponseEntity<?> likeQuestion(@RequestParam("userEmail") String userEmail,
			@RequestParam("questionId") int questionId) {
		Question question = null;
		try {
			question = questionService.getQuestionById(questionId);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
		}

		if (question.getLikes().contains(userEmail)) {
			return new ResponseEntity<>("You have already liked this question", HttpStatus.CONFLICT);
		} else {
			question.getLikes().add(userEmail);
			Question updatedQuestion = questionService.updateQuesion(question);
			return new ResponseEntity<>("Question liked", HttpStatus.OK);
		}
	}

	
	/**
	 * returns all the questions
	 * @return
	 */
	@GetMapping(value = "/questions")
	public ResponseEntity<?> getQuestions() {
		List<Question> allQuestions = questionService.getAllQuestions();
		//System.out.println(allQuestions);
		JSONArray allQuestionsJson = questionService.listToJsonArray(allQuestions);
		//System.out.println(allQuestionsJson);
		return new ResponseEntity<>(allQuestionsJson, HttpStatus.OK);
	}

	
	/**
	 * this function returns all the labels
	 * @return
	 */
	@GetMapping(value = "/questions/labels")
	public ResponseEntity<?> getAllProductLabels() {
		Set<String> productLabels = new LinkedHashSet<String>(questionService.getAllProductLabels());
		return new ResponseEntity<>(productLabels, HttpStatus.OK);
	}

	
	/**
	 * this function marks the question as un-answered
	 * @param questionId
	 * @param commentId
	 * @return
	 */
	@PutMapping(value = "/questions/unanswer")
	public ResponseEntity<?> unanswerQuestion(@RequestParam int questionId, @RequestParam int commentId) {
		Question question = null;
		try {
			question = questionService.getQuestionById(questionId);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
		}

		Comment comment = null;
		try {
			comment = commentService.getCommentById(commentId);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
		}

		question.setAnswer(false);
		comment.setAnswer(false);
		questionService.updateQuesion(question);
		commentService.updateComment(comment);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	/**
	 * this function deletes the question
	 * @param questionId
	 * @return
	 */
	@DeleteMapping(value = "/questions/delete")
	public ResponseEntity<?> removeQuestion(@RequestParam int questionId) {
		Question question = questionService.getQuestionById(questionId);
		questionService.deleteQuestion(question);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	/**
	 * this function sends the question by id
	 * @param questionId
	 * @return
	 */
	@GetMapping(value= "/questions/email")
	public ResponseEntity<?> getQuestion(@RequestParam int questionId) {
		Question question = questionService.getQuestionById(questionId);
		List<Question> list = new ArrayList<>();
		list.add(question);
		JSONArray allQuestionsJson = questionService.listToJsonArray(list);
		return new ResponseEntity<>(allQuestionsJson , HttpStatus.OK);
	}

}
