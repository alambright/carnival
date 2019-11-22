package com.techelevator.npgeek.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class WeatherRowMapper implements RowMapper<Weather> {

	@Override
	public Weather mapRow(ResultSet rs, int rowNum) throws SQLException {
		Weather w = new Weather();
		w.setParkCode(rs.getString("parkcode"));
		w.setFiveDayForecastValue(rs.getInt("fivedayforecastvalue"));
		w.setLow(rs.getInt("low"));
		w.setHigh(rs.getInt("high"));
		w.setForecast(rs.getString("forecast"));
		
		return w;
	}

}
