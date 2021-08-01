/**
 * 
 */
package com.nagarro.productcommunity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.nagarro.productcommunity.model.Question;

/**
 * @author pranshusahu
 *
 */
public interface QuestionDao extends JpaRepository<Question,Integer>, JpaSpecificationExecutor<Question>
{
	
	long countByAnswer(boolean answer);
	
	
	@Query("SELECT productLabel FROM Question")
	List<String> findAllProductLabels();
}
