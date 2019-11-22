package com.techelevator.npgeek.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SurveyRowMapper implements RowMapper<Survey> {

	@Override
	public Survey mapRow(ResultSet rs, int rowNum) throws SQLException {
		Survey s = new Survey();
		
		s.setParkCode(rs.getString("parkcode"));
		s.setEmailAddress(rs.getString("emailaddress"));
		s.setState(rs.getString("state"));
		s.setActivityLevel(rs.getString("activitylevel"));
		
		return s;
	}

}
