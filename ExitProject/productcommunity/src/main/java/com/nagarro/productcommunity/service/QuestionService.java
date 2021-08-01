/**
 * 
 */
package com.nagarro.productcommunity.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.nagarro.productcommunity.dao.QuestionDao;
import com.nagarro.productcommunity.model.Comment;
import com.nagarro.productcommunity.model.Question;
import com.nagarro.productcommunity.model.User;

/**
 * Class to handle the services related to questions
 * @author pranshusahu
 *
 */

@Service
public class QuestionService 
{
	@Autowired
	QuestionDao questionDao;
	
	
	/**
	 * saves new question
	 * @param newQuestion
	 * @return
	 * @throws SQLIntegrityConstraintViolationException
	 */
	public Question postNewQuestion(Question newQuestion) throws SQLIntegrityConstraintViolationException
	{
		List<Question> allQuestions=questionDao.findAll();
		for(Question question:allQuestions)
		{
			if(newQuestion.getProductCode().equals(question.getProductCode()))
			{
				throw new SQLIntegrityConstraintViolationException();
			}
		}
		return questionDao.save(newQuestion);
	}
	
	
	/**
	 * updates the question
	 * @param question
	 * @return
	 */
	public Question updateQuesion(Question question)
	{
		return questionDao.save(question);
	}
	
	
	/**
	 * converts all the question in json format
	 * @param questions
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONArray listToJsonArray(List<Question> questions)
	{
		JSONArray questionsJsonArray = new JSONArray();
		for(Question ques:questions)
		{
		    JSONObject questionObj = new JSONObject();
		    questionObj.put("questionId",ques.getQuestionId());
		    questionObj.put("questionText",ques.getQuestionText());
		    questionObj.put("productCode",ques.getProductCode());
		    questionObj.put("dateCreated",ques.getCreatedDate());
		    questionObj.put("productLabel",ques.getProductLabel());
		    questionObj.put("author",ques.getUser().getFirstName());
		    questionObj.put("userEmail",ques.getUserEmail());
		    questionObj.put("showComments",false);
		    questionObj.put("readMore",true);

//			userQuestions.sort((Question ques1,Question ques2)->ques2.getCreatedDate().compareTo(ques1.getCreatedDate()));

			List<Comment> questionComment=new ArrayList<>(ques.getComments());
			questionComment.sort((Comment c1,Comment c2)->c2.getCreatedDate().compareTo(c1.getCreatedDate()));

			JSONArray comments=new JSONArray();
		    for(Comment comment:questionComment)
		    {
			    JSONObject commentObj = new JSONObject();
			    commentObj=new JSONObject();
			    commentObj.put("commentId",comment.getCommentId());
			    commentObj.put("commentText",comment.getCommentText());
			    commentObj.put("commentUserEmail", comment.getUser().getEmail());
			    commentObj.put("commentUserName", comment.getUser().getFirstName()+" "+comment.getUser().getLastName());
			    commentObj.put("commentQuestionId",comment.getQuestion().getQuestionId());
			    commentObj.put("answer", comment.isAnswer());
			    commentObj.put("createdDate", comment.getCreatedDate());
			    comments.add(commentObj);
		    }
		  questionObj.put("comments",comments);
		  questionObj.put("commentsCount",comments.size());
		    
		  questionObj.put("answered",ques.getAnswer());
		   
		    List<String> likesUserEmail=new ArrayList<>();
		    
		    for(String userEmail:ques.getLikes())
		    {
		    	likesUserEmail.add(userEmail);
		    }
		    questionObj.put("likes",likesUserEmail);
		    questionObj.put("likesCount",likesUserEmail.size());
		    questionsJsonArray.add(questionObj);
		}
		return questionsJsonArray;
	}
	
	
	/**
	 * returns question with help of Id
	 * @param questionId
	 * @return
	 * @throws EntityNotFoundException
	 */
	public Question getQuestionById(int questionId)throws EntityNotFoundException
	{
		return questionDao.getById(questionId);
	}
	
	
	/**
	 * returns question by using specification
	 * @param spec
	 * @return
	 */
	public List<Question> getQuestionsBySearchParameters(Specification<Question> spec)
	{
		System.out.println(spec);
		return questionDao.findAll(spec,Sort.by(Sort.Direction.DESC, "createdDate"));
	}
	
	/**
	 * returns no. of questions asked
	 * @return
	 */
	public int getQuestionsCount()
	{
		return (int) questionDao.count();
	}
	
	/**
	 * returns np. of solved questions
	 * @return
	 */
	public int solvedQuestionsCount()
	{
		return (int) questionDao.countByAnswer(true);
	}
	
	/**
	 * returns all the questions
	 * @return
	 */
	public List<Question> getAllQuestions()
	{
		return questionDao.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
	}
	
	
	/**
	 * returns questions according to labels
	 * @return
	 */
	public List<String> getAllProductLabels()
	{
		return questionDao.findAllProductLabels();
	}
	
	
	/**
	 * Deletes the question
	 * @param question
	 */
	public void deleteQuestion(Question question)
	{
		questionDao.delete(question);
	}


	//creates a unique product code
	public String getProductCodez(User loggedInUser) {
		List<Question> ques = new ArrayList<Question>();
		String code ="";
		while(ques.isEmpty()) {
			int num= (int) Math.random()*(10000-1000+1)+1000;
			code = loggedInUser.getFirstName() + String.valueOf(num);
			ques= questionDao.findAll(Sort.by(code));	
		}
		return code;
	}
}
