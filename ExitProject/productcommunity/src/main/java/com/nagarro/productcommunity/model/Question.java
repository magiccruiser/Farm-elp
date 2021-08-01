/**
 * 
 */
package com.nagarro.productcommunity.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Model class for Question
 * @author pranshusahu
 *
 */

@SuppressWarnings("serial")
@Entity
@Table(name="Question_Details")
@JsonIgnoreProperties({"hibernateLaztInitializer","handler"})
public class Question implements Serializable{
	
	@Id
  	@Column(name = "Question_Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	int questionId;

	@ManyToOne
    @JoinColumn(name = "Join_with_mail", nullable = false)
	User user;
  	
	@Column
	String userEmail;
	
	@Column
	String userFirstName;

	@Column(name = "Question_Text",columnDefinition = "LONGTEXT")
	String questionText;
  	
  	@Column(name = "Product_Code",unique = true)
	String productCode;
  	
  	@Column(name="Product_label")
  	String productLabel;
  	
  	@Column(name = "Created_Date")
	String createdDate;
  	
  	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Comment> comments = new HashSet<>();
  	
  	@Column(name="Answered",columnDefinition = "boolean default false")
  	boolean answer;
  	
  	@Column(name="Likes")
  	@ElementCollection
    Set<String> likes = new HashSet<>();

	/**
	 * 
	 */
	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param questionId
	 * @param user
	 * @param userEmail
	 * @param userFirstName
	 * @param questionText
	 * @param productCode
	 * @param productLabel
	 * @param createdDate
	 * @param comments
	 * @param answer
	 * @param likes
	 */
	public Question(int questionId, User user, String userEmail, String userFirstName, String questionText,
			String productCode, String productLabel, String createdDate, Set<Comment> comments, boolean answer,
			Set<String> likes) {
		super();
		this.questionId = questionId;
		this.user = user;
		this.userEmail = userEmail;
		this.userFirstName = userFirstName;
		this.questionText = questionText;
		this.productCode = productCode;
		this.productLabel = productLabel;
		this.createdDate = createdDate;
		this.comments = comments;
		this.answer = answer;
		this.likes = likes;
	}

	/**
	 * @return the questionId
	 */
	public int getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
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
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * @return the userFirstName
	 */
	public String getUserFirstName() {
		return userFirstName;
	}

	/**
	 * @param userFirstName the userFirstName to set
	 */
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	/**
	 * @return the questionText
	 */
	public String getQuestionText() {
		return questionText;
	}

	/**
	 * @param questionText the questionText to set
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the productLabel
	 */
	public String getProductLabel() {
		return productLabel;
	}

	/**
	 * @param productLabel the productLabel to set
	 */
	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
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

	/**
	 * @return the comments
	 */
	public Set<Comment> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	/**
	 * @return the answer
	 */
	public boolean getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(boolean answer) {
		this.answer = answer;
	}

	/**
	 * @return the likes
	 */
	public Set<String> getLikes() {
		return likes;
	}

	/**
	 * @param likes the likes to set
	 */
	public void setLikes(Set<String> likes) {
		this.likes = likes;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", user=" + user + ", userEmail=" + userEmail + ", userFirstName="
				+ userFirstName + ", questionText=" + questionText + ", productCode=" + productCode + ", productLabel="
				+ productLabel + ", createdDate=" + createdDate + ", comments=" + comments + ", answer=" + answer
				+ ", likes=" + likes + "]";
	}
  	
  	
}
