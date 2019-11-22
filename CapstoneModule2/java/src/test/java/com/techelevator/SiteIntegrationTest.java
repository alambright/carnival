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
import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;
import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.model.jdbc.JDBCSiteDAO;

public class SiteIntegrationTest {

	
	private SiteDAO siteDAO;
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
		siteDAO = new JDBCSiteDAO(datasource);
	}

	@After
	public void tearDown() throws Exception {
		// make a rollback
		datasource.getConnection().rollback();
	}
	
//	@Test
//	public void findsSiteFromSiteNumber() {
//		
//		Site site = new Site();
//		siteDAO.returnSiteFromSiteNumber(4);
//		
//
//	}
	
	@Test
	public void checkAvailabilityReturnsListLength5() {
		List<Site> siteList = siteDAO.checkAvailability(1L, startingDate, endingDate);
		assertEquals(siteList.size(), 5);
	}
	
	@Test
	public void checksOpenAvailabilityReturnsListLength5() {
		List<Site> siteList = siteDAO.openAvailability(5L);
		
		assertEquals(siteList.size(), 1);
	}

	
	@Test
	public void combinesTwoListsThatAreNot5() {
		Site site = new Site();
		List<Site> siteList1 = new ArrayList<>();
		
		List<Site> siteList2 = new ArrayList<>();
		siteList2.add(site);
		siteList2.add(site);
		siteList2.add(site);
		List<Site> combinedList = siteDAO.combineList(siteList1, siteList2);
		assertEquals(combinedList.size(), 3);
	}
	
	@Test
	public void combinesTwoListsThatAreAlsoNot5() {
		Site site = new Site();
		List<Site> siteList1 = new ArrayList<>();
		siteList1.add(site);

		List<Site> siteList2 = new ArrayList<>();
		siteList2.add(site);
		siteList2.add(site);
		siteList2.add(site);
		List<Site> combinedList = siteDAO.combineList(siteList1, siteList2);
		assertEquals(combinedList.size(), 4);
	}

}
