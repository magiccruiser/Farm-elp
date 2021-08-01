package com.nagarro.productcommunity.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Model class for comment
 * @author pranshusahu
 *
 */
@Entity
@Table(name="Comment_Details")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comment implements Serializable{
	
	@Id
  	@Column(name = "Comment_Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	int commentId;
	
	
	@Column(name="Comment_Text")
	String commentText;
	
	@ManyToOne
    @JoinColumn(name = "Username", nullable = false)
	User user;
	
	@ManyToOne
    @JoinColumn(name = "Question", nullable = false)
	Question question;
	
	@Column(name="Answer",columnDefinition = "boolean default false")
  	boolean answer;
	
	@Column(name="Created_Date")
	String createdDate;

	/**
	 * 
	 */
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param commentId
	 * @param commentText
	 * @param user
	 * @param question
	 * @param answer
	 * @param createdDate
	 */
	public Comment(int commentId, String commentText, User user, Question question, boolean answer,
			String createdDate) {
		super();
		this.commentId = commentId;
		this.commentText = commentText;
		this.user = user;
		this.question = question;
		this.answer = answer;
		this.createdDate = createdDate;
	}

	/**
	 * @return the commentId
	 */
	public int getCommentId() {
		return commentId;
	}

	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	/**
	 * @return the commentText
	 */
	public String getCommentText() {
		return commentText;
	}

	/**
	 * @param commentText the commentText to set
	 */
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the question
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * @return the answer
	 */
	public boolean isAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(boolean answer) {
		this.answer = answer;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", commentText=" + commentText + ", user=" + user + ", question="
				+ question + ", answer=" + answer + ", createdDate=" + createdDate + "]";
	}
	
	
}
