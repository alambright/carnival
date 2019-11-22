package com.techelevator.npgeek.model.jdbc;

import static org.junit.Assert.*;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.DAOIntegrationTest;
import com.techelevator.npgeek.model.Park;
import com.techelevator.npgeek.model.Survey;
import com.techelevator.npgeek.model.SurveyRowMapper;
import com.techelevator.npgeek.model.Weather;


public class JdbcDaoIntegrationTest extends DAOIntegrationTest {
	
	private static final JdbcTemplate J_TEMP;

	private static final Random RAND;

	private static final Object[] DUMMY_PARK_DATA_1, DUMMY_PARK_DATA_2, DUMMY_PARK_DATA_3;

	private static final Weather[] DUMMY_WEATHER_DATA_1, DUMMY_WEATHER_DATA_2, DUMMY_WEATHER_DATA_3;
	
	private static final int FORECAST_DAYS = 5;
	private static final int HIGHEST_LOW = 60;
	//private static final int HIGHEST_HIGH = 120;
	
	private static final Map<Integer, String> FORECAST_VALUES;
	
	private static final String[] NAME_VALUES;
	private static final String[] ACTIVITY_VALUES;
	private static final String[] TOP_LEVEL_DOMAINS;
	private static final String[] DOMAIN_VALUES;
	private static final String[] STATE_VALUES;

	private static final List<Park> DUMMY_PARKS;

	private static final JdbcParkDao PDAO;
	private static final JdbcWeatherDao WDAO;
	private static final JdbcSurveyDao SDAO;
	
	static {
		
		J_TEMP = new JdbcTemplate(getDataSource());

		RAND = new Random();
		
		FORECAST_VALUES = ImmutableMap.of(
			Integer.valueOf(0), "meatballs",
			Integer.valueOf(1), "nuclear winter",
			Integer.valueOf(2), "black rain",
			Integer.valueOf(3), "possible wormholes",
			Integer.valueOf(4), "cats and dogs"
		);
		
		NAME_VALUES = new String[] { "DavyTheDemon", 
									 "SaintPeter", 
									 "Beelzebub", 
									 "Mephistopheles", 
									 "NondescriptPurgatoryGuy1",
									 "NondescriptPurgatoryGuy2",
									 "NondescriptPurgatoryGuy3",
									 "Loki",
									 "Amaterasu",
									 "GallowsGodAKAOdin" };
		
		DOMAIN_VALUES = new String[] { "silvercity",
									   "heaven",
									   "h3ll",
									   "purgatory",
									   "netherrealms",
									   "nowhere",
									   "asgard",
									   "mountolympus",
									   "jigoku",
									   "tengoku" };
		
		TOP_LEVEL_DOMAINS = new String[] { ".sc", ".hl", ".hn",
										   ".nr", ".no", ".as",
										   ".mo", ".ji", ".ten",
										   ".hel", ".hev", ".purg"};
		
		ACTIVITY_VALUES = new String[] { "impossibly fit",
										 "walking corpse",
										 "zombie",
										 "superhuman",
										 "neutral",
										 "immortal",
										 "bliss",
										 "infinite stamina" };
		
		STATE_VALUES = new String[] { "Hell", "Heaven", 
									  "Purgatory", "Asgard",
									  "Tengoku", "Jigoku", 
									  "Olympus", "Silver City", 
									  "Nether Realms", "Nowhere" };
		
		DUMMY_WEATHER_DATA_1 = new Weather[5];
		DUMMY_WEATHER_DATA_2 = new Weather[5];
		DUMMY_WEATHER_DATA_3 = new Weather[5];

		DUMMY_PARK_DATA_1 = new Object[] { "HELLP", "Eta Epsilon Lambda Lambda Park", "Nether Realms",
											Integer.valueOf(-12345), Integer.valueOf(-321), Double.valueOf(-432.0), 
											Integer.valueOf(-5), "Hellscape", Integer.valueOf(-50), 
											Integer.valueOf(-987654), "Come to the nether realms and experience HELL!", 
											"Beelzebub", 
											"Experience the wonder of and magic of all the nether realms have to offer."
											+ "Established at the start of the Judeo-Christian era, we have been"
											+ "helping sinners see the lights for over 2000 years",
											Integer.valueOf(0), Integer.valueOf(666) };

		DUMMY_PARK_DATA_2 = new Object[] { "HEAVP", "Eta Epsilon Alpha Upsilon Park", "The Silver City",
											Integer.valueOf(98765), Integer.valueOf(765), Double.valueOf(3456.0), 
											Integer.valueOf(-9999999), "Heveanscape", Integer.valueOf(333), 
											Integer.valueOf(11111),
											"Come to the silver city and experience the delights of heaven!", 
											"Saint Peter",
											"Here in the silver city, your wildest (but clean) delights can all come true."
											+ "Established at the birth of everything, "
											+ "our facilities are pristine and impeccable, "
											+ "you will come away cleansed in body and mind",
											Integer.valueOf(99999999), Integer.valueOf(-12121212) };

		DUMMY_PARK_DATA_3 = new Object[] { "PURGAP", "Pi Upsilon Rho Gamma Alpha Park", "Purgatory", 
											Integer.valueOf(0),	Integer.valueOf(-1), Double.valueOf(-2.0), 
											Integer.valueOf(-3), "Nullscape", Integer.valueOf(-4),
											Integer.valueOf(-5), "Come to Purgatory, it's awesome!", "No One Ever",
											"Here in Purgatory, experience the sheer beingness of continued, "
											+ "unending existence. Never actually established, "
											+ "it's been here for as long as there has been anything at all. "
											+ "So very mild and not unpleasant. We make Pablum sound tasty." 
											+ "You can never leave!", Integer.valueOf(99999999), 
											Integer.valueOf(-12121212) };

		DUMMY_PARKS = Arrays.asList(new Park[] { instantiatePark(DUMMY_PARK_DATA_1), instantiatePark(DUMMY_PARK_DATA_2),
				instantiatePark(DUMMY_PARK_DATA_3) });

		PDAO = new JdbcParkDao(getDataSource());
		WDAO = new JdbcWeatherDao(getDataSource());
		SDAO = new JdbcSurveyDao(getDataSource());
	}
	
	@BeforeClass
	public static void createDummyWeatherData() {
		for (int i = 0; i < FORECAST_DAYS; i++) {
			DUMMY_WEATHER_DATA_1[i] = generateRandomWeather(DUMMY_PARKS.get(0).getParkCode(), i + 1);
			DUMMY_WEATHER_DATA_2[i] = generateRandomWeather(DUMMY_PARKS.get(1).getParkCode(), i + 1);
			DUMMY_WEATHER_DATA_3[i] = generateRandomWeather(DUMMY_PARKS.get(2).getParkCode(), i + 1);
		}
		
	}

	private static Weather generateRandomWeather(String parkCode, int day) {
		Weather w = new Weather();
		w.setParkCode(parkCode);
		w.setFiveDayForecastValue(Integer.valueOf(day));
		w.setLow(Integer.valueOf(RAND.nextInt(HIGHEST_LOW)));
		w.setHigh(Integer.valueOf(RAND.nextInt(HIGHEST_LOW) + HIGHEST_LOW));
		w.setForecast(FORECAST_VALUES.get(Integer.valueOf(RAND.nextInt(FORECAST_DAYS))));
		return w;
	}
	
	private static Survey generateRandomSurvey(String parkCode) {
		Survey s = new Survey();
		s.setParkCode(parkCode);
		s.setEmailAddress(NAME_VALUES[RAND.nextInt(NAME_VALUES.length)] + "@" + 
						  DOMAIN_VALUES[RAND.nextInt(DOMAIN_VALUES.length)] + 
						  TOP_LEVEL_DOMAINS[RAND.nextInt(TOP_LEVEL_DOMAINS.length)]);
		s.setState(STATE_VALUES[RAND.nextInt(STATE_VALUES.length)]);
		s.setActivityLevel(ACTIVITY_VALUES[RAND.nextInt(ACTIVITY_VALUES.length)]);
		
		return s;
	}
	
	
	private static void insertDummyWeather() {
		insertWeatherArray(DUMMY_WEATHER_DATA_1);
		insertWeatherArray(DUMMY_WEATHER_DATA_2);
		insertWeatherArray(DUMMY_WEATHER_DATA_3);
	}
	
	private static void insertWeatherArray(Weather[] weathers) {
		for (Weather w : weathers)
			insertSingleWeather(w);
	}
	
	private static void insertSingleWeather(Weather w) {
		String sql = "INSERT INTO " + JdbcWeatherDao.TABLE_NAME +
					 " (parkcode, fivedayforecastvalue, low, high, forecast)" + 
					 " VALUES(?, ?, ?, ?, ?)";
		J_TEMP.update(sql, w.getParkCode(), w.getFiveDayForecastValue(), w.getLow(),
					  w.getHigh(), w.getForecast());
	}

	private static Park instantiatePark(Object[] data) {
		Park park = new Park();
		try {
			park.setParkCode((String) data[0]);
			park.setParkName((String) data[1]);
			park.setState((String) data[2]);
			park.setAcreage((Integer) data[3]);
			park.setElevationInFeet((Integer) data[4]);
			park.setMilesOfTrail((Double) data[5]);
			park.setNumberOfCampsites((Integer) data[6]);
			park.setClimate((String) data[7]);
			park.setYearFounded((Integer) data[8]);
			park.setAnnualVisitorCount((Integer) data[9]);
			park.setInspirationalQuote((String) data[10]);
			park.setInspirationalQuoteSource((String) data[11]);
			park.setParkDescription((String) data[12]);
			park.setEntryFee((Integer) data[13]);
			park.setNumberOfAnimalSpecies((Integer) data[14]);
		} catch (ArrayIndexOutOfBoundsException e) {
			park = null;
		}
		return park;
	}

	public static void insertDummyParks() throws Exception {
		for (Park park : DUMMY_PARKS)
			insertPark(park);
	}

	public static void insertPark(Park park) {
		String sql = "INSERT INTO " + JdbcParkDao.TABLE_NAME
				+ " (parkcode, parkname, state, acreage, elevationinfeet, milesoftrail, numberofcampsites,"
				+ "climate, yearfounded, annualvisitorcount, inspirationalquote, inspirationalquotesource, "
				+ "parkdescription, entryfee, numberofanimalspecies) "
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		J_TEMP.update(sql, park.getParkCode(), park.getParkName(), park.getState(), park.getAcreage(),
				park.getElevationInFeet(), park.getMilesOfTrail(), park.getNumberOfCampsites(), park.getClimate(),
				park.getYearFounded(), park.getAnnualVisitorCount(), park.getInspirationalQuote(),
				park.getInspirationalQuoteSource(), park.getParkDescription(), park.getEntryFee(),
				park.getNumberOfAnimalSpecies());
	}

	@Test
	public void testGetAllParks() throws Exception {
		List<Park> parksExpected = PDAO.getAllParks();
		parksExpected.addAll(DUMMY_PARKS);
		insertDummyParks();
		assertArrayEquals(parksExpected.toArray(), PDAO.getAllParks().toArray());
	}

	@Test
	public void testGetParkByParkCode() throws Exception {
		insertDummyParks();
		for (Park park : DUMMY_PARKS)
			assertEquals(park, PDAO.getParkByParkCode(park.getParkCode()));
	}

	/* 
	 * This test integrates methods from JdbcParkDao and JdbcWeatherDao and ensures
	 * that the methods function together as they should.
	 * */
	@Test
	public void testGetWeatherByParkCode() throws Exception {
		insertDummyParks();
		insertDummyWeather();
		assertArrayEquals(DUMMY_WEATHER_DATA_1, WDAO.getWeatherByParkCode(DUMMY_PARKS.get(0).getParkCode()).toArray());
		assertArrayEquals(DUMMY_WEATHER_DATA_2, WDAO.getWeatherByParkCode(DUMMY_PARKS.get(1).getParkCode()).toArray());
		assertArrayEquals(DUMMY_WEATHER_DATA_3, WDAO.getWeatherByParkCode(DUMMY_PARKS.get(2).getParkCode()).toArray());
	}
	
	@Test
	public void testSaveSurvey() throws Exception {
		Survey surveyExpected = generateRandomSurvey(DUMMY_PARKS.get(0).getParkCode());
		SDAO.saveSurvey(surveyExpected);
		String sql = "SELECT * FROM " + JdbcSurveyDao.TABLE_NAME +
				" WHERE parkcode = ? AND emailaddress = ? AND state = ? AND activitylevel = ?";
		Survey surveyActual = J_TEMP.queryForObject(sql, new SurveyRowMapper(), surveyExpected.getParkCode(),
				surveyExpected.getEmailAddress(), surveyExpected.getState(), surveyExpected.getActivityLevel());
		assertEquals(surveyExpected, surveyActual);
		
		surveyExpected = generateRandomSurvey(DUMMY_PARKS.get(1).getParkCode());
		SDAO.saveSurvey(surveyExpected);
		surveyActual = J_TEMP.queryForObject(sql, new SurveyRowMapper(), surveyExpected.getParkCode(),
				surveyExpected.getEmailAddress(), surveyExpected.getState(), surveyExpected.getActivityLevel());
		assertEquals(surveyExpected, surveyActual);
		
		surveyExpected = generateRandomSurvey(DUMMY_PARKS.get(2).getParkCode());
		SDAO.saveSurvey(surveyExpected);
		surveyActual = J_TEMP.queryForObject(sql, new SurveyRowMapper(), surveyExpected.getParkCode(),
				surveyExpected.getEmailAddress(), surveyExpected.getState(), surveyExpected.getActivityLevel());
		assertEquals(surveyExpected, surveyActual);
	}

	/* 
	 * This test integrates a methods from JdbcParkDao and JdbcSurveyDao and
	 * ensures that everything is working together as it should.
	 * */
	@Test
	public void testGetFavoriteParks() throws Exception {
		List<Park> favParksExpected, favParksActual;
		
		/*
		 * Here we will make sure our three dummy parks have the most surverys so they
		 * will be the top three favorite parks.
		 */
		int maxSurveyCount = PDAO.getFavoriteParks().get(0).getSurveyCount().intValue();
		insertDummyParks();
		System.out.println(maxSurveyCount);
		for (int i = 1; i <= maxSurveyCount + 2; i++) 
			SDAO.saveSurvey(generateRandomSurvey(DUMMY_PARKS.get(0).getParkCode()));
		
		/* 
		 * Here we make sure the last 2 dummy parks have the same number of surveys
		 * so we can test that when two parks have the same number of surveys 
		 * they are ordered alphabetically. Note the way we created our dummy data
		 * the park name for dummy park 2 comes before the park name of dummy park 3
		 * in alphabetical order. This is exactly what we need to test our get favorite
		 * parks method.
		 * */
		for (int i = 1; i <= maxSurveyCount + 1; i++) {
			SDAO.saveSurvey(generateRandomSurvey(DUMMY_PARKS.get(1).getParkCode()));
			SDAO.saveSurvey(generateRandomSurvey(DUMMY_PARKS.get(2).getParkCode()));
		}
		/* 
		 * Now we can test that the top three parks returned by our get favorite parks
		 * method are the dummy parks we ourselves added. This will verify that out method
		 * is doing exactly what we want. And it doesn't rely on what's currently in the 
		 * database because it only uses our dummy parks and dummy surveys. 
		 * */
		favParksActual = PDAO.getFavoriteParks();
		for (int i = 0; i < DUMMY_PARKS.size(); i++)
			assertEquals(DUMMY_PARKS.get(i), favParksActual.get(i));
		
	}

}
