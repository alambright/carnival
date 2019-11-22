package com.techelevator;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.model.jdbc.JDBCParkDAO;

public class ParkIntegrationTest {

	private ParkDAO parkDAO;
	private static SingleConnectionDataSource datasource;
	private JdbcTemplate jdbcTemplate;
	
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
		parkDAO = new JDBCParkDAO(datasource);
	}

	@After
	public void tearDown() throws Exception {
		// make a rollback
		datasource.getConnection().rollback();
	}
	
	@Test
	public void getsListOfAllParks() {
		List<Park> allParks = parkDAO.getAllParks();
		assertEquals(3, allParks.size());
		assertEquals("Acadia", allParks.get(0).getName());
	}
	
	
	@Test
	public void getsParkFormName() {
		Park newPark = parkDAO.returnParkFromName("Arches");
		
		assertEquals("Utah", newPark.getLocation());
	}
	
	

}
