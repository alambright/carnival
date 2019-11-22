package com.techelevator.npgeek.model.jdbc;

import com.techelevator.npgeek.model.Survey;
import com.techelevator.npgeek.model.SurveyDao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcSurveyDao implements SurveyDao {

	public static final String TABLE_NAME = "survey_result";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcSurveyDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void saveSurvey(Survey survey) {
		String sql = "INSERT INTO " + TABLE_NAME + 
					 "(parkcode, emailaddress, state, activitylevel) VALUES (?,?,?,?)";
		this.jdbcTemplate.update(sql, survey.getParkCode(), 
								 	  survey.getEmailAddress(), 
								 	  survey.getState(),		 
								 	  survey.getActivityLevel());

	}

}
