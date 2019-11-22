package com.techelevator.campground.model.jdbc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.Site;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	@Override
	public void makeAReservation(Reservation res) {//LocalDate startDate, LocalDate endDate, LocalDate todaysDate, String reservationName, Long siteId) {
		String sqlResAdd = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) "
				+ "VALUES ( ?, ?, CAST(? AS DATE), CAST(? AS DATE), CAST(? AS DATE))";
		jdbcTemplate.update(sqlResAdd, res.getSiteId(), res.getName(), res.getFromDate(), res.getToDate(), res.getCreateDate());
		//siteId, reservationName, startDate, endDate, todaysDate);
		
				
	}
	
	

	@Override
	public BigDecimal totalcostOfStay(LocalDate startDate, LocalDate EndDate, Campground campground) {
		long numberOfDays = daysBetween(startDate, EndDate);
		
		BigDecimal lengthOfStay = new BigDecimal(numberOfDays);
		BigDecimal totalCostOfStay = campground.getDailyFee().multiply(lengthOfStay);
		
		return totalCostOfStay;
	}
	
	public Date asDate(LocalDate date) {
	    return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	  }
	
	public long daysBetween(LocalDate start, LocalDate end) {
		Date startDate = asDate(start);
		Date endDate = asDate(end);
		long difference = (startDate.getTime() - endDate.getTime()) / 86400000; //milliseconds per day
		return Math.abs(difference);
	}


	@Override
	public Reservation searchForReservation(String name, Long siteId) {
		Reservation reservation = new Reservation();
		String yourReservation = "SELECT * FROM reservation WHERE site_id = ? AND name = ? ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(yourReservation, siteId, name);
		result.next();
		reservation.setReservationId(result.getLong("reservation_id"));
		reservation.setName(result.getString("name"));
		reservation.setFromDate(result.getDate("from_date").toLocalDate());
		reservation.setToDate(result.getDate("to_date").toLocalDate());
		reservation.setCreateDate(result.getDate("create_date").toLocalDate());
		
		return reservation;
	}
	
}
