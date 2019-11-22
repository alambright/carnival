package com.techelevator.npgeek.model.jdbc;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.techelevator.npgeek.model.Park;
import com.techelevator.npgeek.model.ParkDao;
import com.techelevator.npgeek.model.ParkRowMapper;

@Component
public class JdbcParkDao implements ParkDao {
	
	public static final String TABLE_NAME = "park";
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcParkDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		String sql = "SELECT * FROM " + TABLE_NAME; // + " ORDER BY parkname ASC"; 
 		return this.jdbcTemplate.query(sql, new ParkRowMapper()); 		
	}

	@Override
	public Park getParkByParkCode(String code) {
		String sql = "SELECT * FROM " + TABLE_NAME +
					 " WHERE parkcode = ?";
		return this.jdbcTemplate.queryForObject(sql, new ParkRowMapper(), code);
	}

	@Override
	public List<Park> getFavoriteParks() {
		String sql = "SELECT * FROM ( SELECT " +
					 "count(survey_result.parkcode) AS surveycount, park.parkcode, "+
					 "park.parkname, park.state, park.acreage, " +
					 "park.elevationinfeet, park.milesoftrail, park.numberofcampsites, " +
					 "park.climate, park.yearfounded, park.annualvisitorcount, " +
					 "park.inspirationalquote, park.inspirationalquotesource, " +
					 "park.parkdescription, park.entryfee, park.numberofanimalspecies " +
					 "FROM park " +
					 "LEFT JOIN survey_result " +
					 "ON survey_result.parkcode = park.parkcode " +
					 "GROUP BY survey_result.parkcode, park.parkcode " +
					 "ORDER BY count(survey_result.parkcode) DESC, park.parkname ASC) as surveyordering " +
					 "WHERE surveycount > 0";
		return this.jdbcTemplate.query(sql, new ParkRowMapper());
	}

}
