package com.techelevator.npgeek.model.jdbc;

import java.util.List;

import com.techelevator.npgeek.model.Weather;
import com.techelevator.npgeek.model.WeatherDao;
import com.techelevator.npgeek.model.WeatherRowMapper;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcWeatherDao implements WeatherDao {

	public static final String TABLE_NAME = "weather";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcWeatherDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Weather> getWeatherByParkCode(String code) {
		String sql = "SELECT * FROM " + TABLE_NAME +
					 " WHERE parkcode = ?";
		
 		return this.jdbcTemplate.query(sql, new WeatherRowMapper(), code);
	}

}
