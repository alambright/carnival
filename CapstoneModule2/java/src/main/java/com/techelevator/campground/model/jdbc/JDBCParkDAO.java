package com.techelevator.campground.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Park> getAllParks() {
		List<Park> parkList = new ArrayList<>();
		String sqlParks = "SELECT * FROM park";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlParks);
		
		while (results.next()) {
			Park park = new Park();
			park.setParkId(results.getLong("park_id"));
			park.setName(results.getString("name"));
			park.setLocation(results.getString("location"));
			park.setEstablishDate(results.getDate("establish_date").toLocalDate());
			park.setArea(results.getInt("area"));
			park.setVisitors(results.getInt("visitors"));
			park.setDescription(results.getString("description"));
			parkList.add(park);
		}
		
		return parkList;
	}

	@Override
	public Park returnParkFromName(String name) {
		String sqlGetParkObject = "SELECT * FROM park WHERE name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParkObject, name);
		results.next();
		Park park = new Park();
		park.setParkId(results.getLong("park_id"));
		park.setName(results.getString("name"));
		park.setLocation(results.getString("location"));
		park.setEstablishDate(results.getDate("establish_date").toLocalDate());
		park.setArea(results.getInt("area"));
		park.setVisitors(results.getInt("visitors"));
		park.setDescription(results.getString("description"));
		return park;
	}

}
