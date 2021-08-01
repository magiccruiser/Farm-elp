/**
 * 
 */
package com.nagarro.productcommunity.service;

import javax.persistence.EntityNotFoundException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.productcommunity.dao.CommentDao;
import com.nagarro.productcommunity.model.Comment;

/**
 * Class to handle the services related to comments
 * @author pranshusahu
 *
 */

@Service
public class CommentService
{
	@Autowired
	CommentDao commentDao;
	
	
	/**
	 * Adds new comment to question
	 * @param newComment
	 * @return
	 */
	public Comment addNewComment(Comment newComment)
	{
		return commentDao.save(newComment);
	}
	
	
	/**
	 * returns comment after search by id
	 * @param commentId
	 * @return
	 * @throws EntityNotFoundException
	 */
	public Comment getCommentById(int commentId)throws EntityNotFoundException
	{
		return commentDao.getById(commentId);
	}
	
	
	/**
	 * updates comment
	 * @param newComment
	 * @return
	 */
	public Comment updateComment(Comment newComment)
	{
		return commentDao.save(newComment);
	}
	
	
	/**
	 * converts the comment to JSON
	 * @param addedComment
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject objectToJsonObj(Comment addedComment)
	{
	    JSONObject commentObj = new JSONObject();
	    commentObj.put("commentUserEmail",addedComment.getUser().getEmail());
	    commentObj.put("commentUserName",addedComment.getUser().getFirstName()+" "+addedComment.getUser().getLastName());
	    commentObj.put("commentQuestionId",addedComment.getQuestion().getQuestionId());
	    commentObj.put("commentId",addedComment.getCommentId());
	    commentObj.put("commentText",addedComment.getCommentText());
	    return commentObj;
	}
}