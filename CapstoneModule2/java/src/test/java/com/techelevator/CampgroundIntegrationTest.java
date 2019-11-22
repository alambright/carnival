package com.techelevator;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;

public class CampgroundIntegrationTest {

	private CampgroundDAO campgroundDAO;
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
		// prevents test from affecting the database
		datasource.setAutoCommit(false);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		datasource.destroy();
	}

	@Before
	public void setUp() throws Exception {
		// make new instance of DAO
		campgroundDAO = new JDBCCampgroundDAO(datasource);
	}

	@After
	public void tearDown() throws Exception {
		// make a rollback
		datasource.getConnection().rollback();
	}

	@Test
	public void findsCampgroundNameByParkName() {

		List<Campground> campgroundList = campgroundDAO.getCampgroundByPark(1L);

		assertEquals(campgroundList.size(), 3);
		assertEquals(campgroundList.get(0).getName(), "Blackwoods");
	}

	@Test
	public void getCamgroundFromName() {
		Campground campground = campgroundDAO.getCampgroundFromName("Blackwoods");
		Long actual1 = campground.getParkId();
		Long expected1 = 1L;
		Long actual2 = campground.getCampgroundId();
		Long expected2 = 1L;
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);

	}
	
	@Test
	public void checkAvailabilityReturnsListLength5() {
		List<Campground> campgroundList = campgroundDAO.checkAvailability(1L, startingDate, endingDate);
		assertEquals(campgroundList.size(), 5);
	}
	
	@Test
	public void checksOpenAvailabilityReturnsListLength5() {
		List<Campground> campgroundList = campgroundDAO.openAvailability(5L);
		
		assertEquals(campgroundList.size(), 1);
	}
	
	@Test
	public void combinesTwoLists() {
		List<Campground> campgroundList1 = campgroundDAO.openAvailability(1L);
		List<Campground> campgroundList = campgroundDAO.checkAvailability(1L, startingDate, endingDate);
		List<Campground> combinedList = campgroundDAO.combineList(campgroundList1, campgroundList);
		
		assertEquals(combinedList.size(), 5);
	}
	
	
	@Test
	public void combinesTwoListsThatAreNot5() {
		Campground camp = new Campground();
		List<Campground> campgroundList1 = new ArrayList<>();
		
		List<Campground> campgroundList2 = new ArrayList<>();
		campgroundList2.add(camp);
		campgroundList2.add(camp);
		campgroundList2.add(camp);
		List<Campground> combinedList = campgroundDAO.combineList(campgroundList1, campgroundList2);
		assertEquals(combinedList.size(), 3);
	}
	
	@Test
	public void combinesTwoListsThatAreAlsoNot5() {
		Campground camp = new Campground();
		List<Campground> campgroundList1 = new ArrayList<>();
		campgroundList1.add(camp);

		List<Campground> campgroundList2 = new ArrayList<>();
		campgroundList2.add(camp);
		campgroundList2.add(camp);
		campgroundList2.add(camp);
		List<Campground> combinedList = campgroundDAO.combineList(campgroundList1, campgroundList2);
		assertEquals(combinedList.size(), 4);
	}
	
	@Test
	public void getMonthFromNumber() {
		String march = campgroundDAO.getMonthFromNumber(3);
		assertEquals(march, "March");
		String april = campgroundDAO.getMonthFromNumber(4);
		assertEquals(april, "April");
		String may = campgroundDAO.getMonthFromNumber(5);
		assertEquals(may, "May");
		String june = campgroundDAO.getMonthFromNumber(6);
		assertEquals(june, "June");
		String july = campgroundDAO.getMonthFromNumber(7);
		assertEquals(july, "July");
	}
	
	
	
	
	
	
	

}
