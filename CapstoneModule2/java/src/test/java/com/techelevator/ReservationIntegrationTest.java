package com.techelevator;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.jdbc.JDBCReservationDAO;


public class ReservationIntegrationTest {


	private ReservationDAO reservationDAO;
	private static SingleConnectionDataSource datasource;
	private JdbcTemplate jdbcTemplate;
	
	String userStartDate = "2020/03/01";
	String userEndDate = "2020/03/06";
	String todaysDate = "2020/02/20";
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	LocalDate startingDate = LocalDate.parse(userStartDate, formatter);
	LocalDate endingDate = LocalDate.parse(userEndDate, formatter);
	LocalDate todaysDateL = LocalDate.parse(todaysDate, formatter);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		datasource = new SingleConnectionDataSource();
		datasource.setUrl("jdbc:postgresql://localhost:5432/campground");
		datasource.setUsername("postgres");
		datasource.setPassword("postgres1");
		//prevents test from affecting the database
		datasource.setAutoCommit(false);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		datasource.destroy();
	}

	@Before
	public void setUp() throws Exception {
		//make new instance of DAO
		reservationDAO = new JDBCReservationDAO(datasource);
	}

	@After
	public void tearDown() throws Exception {
		//make a rollback
		datasource.getConnection().rollback();
	}

	@Test
	public void createdReservationHasCorrectName() {
		String testReservation = "smith family";
		
		Reservation reservation = new Reservation();
		reservation.setName("smith family");
		reservation.setSiteId(500L);
		reservation.setToDate(endingDate);
		reservation.setFromDate(startingDate);
		reservation.setCreateDate(todaysDateL);
		reservation.setReservationId(99L);
		reservationDAO.makeAReservation(reservation);
		Reservation actual = reservationDAO.searchForReservation(reservation.getName(), reservation.getSiteId());
		
		assertEquals(actual.getName(), "smith family");
	
	}
	
	@Test
	public void calculatesCost () {
		
		Campground campground = new Campground();
		campground.setDailyFee(new BigDecimal("1"));
		
		assertEquals(new BigDecimal("5"), reservationDAO.totalcostOfStay(startingDate, endingDate, campground));
		
	}
	
	@Test
	public void findsLengthOfStay() {
		
		assertEquals(5L, reservationDAO.daysBetween(startingDate, endingDate));

	}
	
	@Test
	public void findsReservationByName() {
		Reservation reservation = new Reservation();
		reservation.setName("Leela Family");
		reservation.setSiteId(46L);
		String name = reservation.getName();
		Long siteId = reservation.getSiteId();
		
		Reservation testRes = reservationDAO.searchForReservation(name, siteId);
		Long id = testRes.getReservationId();
		Long expectedID = 43L;
		assertEquals(expectedID , id);
		
	}

}
	
	
	
	


