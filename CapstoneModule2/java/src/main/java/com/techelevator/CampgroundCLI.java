package com.techelevator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;
import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.model.jdbc.JDBCParkDAO;
import com.techelevator.campground.model.jdbc.JDBCReservationDAO;
import com.techelevator.campground.model.jdbc.JDBCSiteDAO;

public class CampgroundCLI {

	private static final String PARKS_MENU_OPTION_VIEWCAMPS = "View campgrounds";
	private static final String PARKS_MENU_OPTION_CHECKAVAIL = "Check for Availability";
	private static final String PARKS_MENU_OPTION_RETURN = "Return to Previous Screen";
	private static final String[] PARKS_MENU_OPTIONS = new String[] { PARKS_MENU_OPTION_VIEWCAMPS,
			PARKS_MENU_OPTION_CHECKAVAIL, PARKS_MENU_OPTION_RETURN };

	private static final String CAMPGROUND_MENU_OPTION_CHECKAVAIL = "Check for Availability";
	private static final String CAMPGROUND_MENU_OPTIONS_MAKERESERVATION = "Make a reservation";
	private static final String CAMPGROUND_MENU_OPTIONS_RETURNTOPREVIOUS = "Return to Previous Screen";
	private static final String[] CAMPGROUND_MENU_OPTIONS = new String[] { CAMPGROUND_MENU_OPTION_CHECKAVAIL,
			CAMPGROUND_MENU_OPTIONS_MAKERESERVATION, CAMPGROUND_MENU_OPTIONS_RETURNTOPREVIOUS };

	private Menu menu;
	private CampgroundDAO campgroundDAO;
	private ReservationDAO reservationDAO;
	private SiteDAO siteDAO;
	private ParkDAO parkDAO;

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();

	}

	public CampgroundCLI(DataSource datasource) {
		this.menu = new Menu(System.in, System.out);

		campgroundDAO = new JDBCCampgroundDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
		siteDAO = new JDBCSiteDAO(datasource);
		parkDAO = new JDBCParkDAO(datasource);

	}

	public void run() {

		while (true) {
			printHeading("Park Options");
			String selectedPark = getParkSelectionFromUser();
			if (selectedPark.equals("Exit")) {
				System.exit(0);
			} else {
				printParkInformation(selectedPark);
				parksMenu(selectedPark);
			}
		}
	}

	public void parksMenu(String selectedParkName) {
		// boolean bool = true;

		printHeading("Park Options Menu");
		String choice = (String) menu.getChoiceFromOptions(PARKS_MENU_OPTIONS);
		while (true) {

			if (choice.equals(PARKS_MENU_OPTION_VIEWCAMPS)) {
				printCampgroundsForPark(selectedParkName);
				// handleCamps(selectedParkName);
				campgroundMenu(selectedParkName);
			} else if (choice.equals(PARKS_MENU_OPTION_CHECKAVAIL)) {
				getAvailability(selectedParkName);
				campgroundMenu(selectedParkName);
			} else if (choice.equals(PARKS_MENU_OPTION_RETURN)) {
				run();
			}
		}
	}

	public void campgroundMenu(String selectedParkName) {
		printHeading("Campground Menu");
		String choice = (String) menu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS);
		while (true) {
			if (choice.equals(CAMPGROUND_MENU_OPTION_CHECKAVAIL)) {
				getAvailability(selectedParkName);
			} else if (choice.equals(CAMPGROUND_MENU_OPTIONS_MAKERESERVATION)) {
				handleReservation(selectedParkName);
				System.exit(0);
			} else if (choice.equals(CAMPGROUND_MENU_OPTIONS_RETURNTOPREVIOUS)) {
				parksMenu(selectedParkName);
			}
		}
	}

	public void handleReservation(String selectedParkName) {
		printHeading("Make a reservation");
		String campgroundName = getCampgroundSelectionFromUser(selectedParkName);
		if (campgroundName.equals("Exit")) {
			campgroundMenu(selectedParkName);
		} else {

			System.out.println("the campground you selected is: " + campgroundName);

			Campground campground = campgroundDAO.getCampgroundFromName(campgroundName);
			LocalDate startDate = getUserStart();
			LocalDate endDate = getUserEnd();
			
			LocalDate todaysDate = getTodaysDate();
			Long siteSelection = getSiteSelectionFromUser(selectedParkName, startDate, endDate, campground);
			String reservationName = getReservationName();
			
			Reservation res = new Reservation();
			res.setName(reservationName);
			res.setFromDate(startDate);
			res.setToDate(endDate);
			res.setCreateDate(todaysDate);
			res.setSiteId(siteSelection);
			

			reservationDAO.makeAReservation(res);//startDate, endDate, todaysDate, reservationName, siteSelection);

			System.out.println(
					reservationConfirmation(reservationName, siteSelection, selectedParkName, startDate, endDate));
			printTotalCost(startDate, endDate, campground);
			System.out.println("Thank you for supporting National Parks!");
		}

	}

	// ************method to capture user name for reservation************
	public String getReservationName() {
		Scanner userInput = new Scanner(System.in);
		System.out.println("Enter a name to save the reservation under: ");
		String reservationName = userInput.nextLine();
		userInput.close();

		return reservationName;
	}

	// ************method to put together print reservation confirmation************
	public String reservationConfirmation(String reservationName, Long siteSelection, String selectedParkName,
			LocalDate startingDate, LocalDate endingDate) {
		String confirmationMessage = "Reservation for " + reservationName + " confirmed at " + selectedParkName
				+ " from " + startingDate + " to " + endingDate;
		return confirmationMessage;
	}

	public void getAvailability(String selectedParkName) {
		String campgroundName = getCampgroundSelectionFromUser(selectedParkName);
		if (campgroundName.equals("Exit")) {
			campgroundMenu(selectedParkName);
		} else {
			// printAvailability(selectedParkName);
			Campground campground = campgroundDAO.getCampgroundFromName(campgroundName);
			LocalDate startDate = getUserStart();
			LocalDate endDate = getUserEnd();
			List<Site> availableSites = siteDAO.checkAvailability(campground.getCampgroundId(), startDate, endDate);
			List<Campground> availableCampgrounds = campgroundDAO.checkAvailability(campground.getCampgroundId(),
					startDate, endDate);
			

			//List<Site> openSites = siteDAO.openAvailability(campground.getCampgroundId());
			//List<Campground> openCampground = campgroundDAO.openAvailability(campground.getCampgroundId());

			int count = printSiteList(availableSites, availableCampgrounds);//, openSites, openCampground);
			if (count>0) {
				printTotalCost(startDate, endDate, campground);
			}
			campgroundMenu(selectedParkName);
		}
	}

	// Enters Park names as number choices
	private String getParkSelectionFromUser() {
		System.out.println("Choose a park:");
		List<Park> allParks = parkDAO.getAllParks();

		List<String> parkNames = new ArrayList<>();
		for (Park parkName : allParks) {
			parkNames.add(parkName.getName());
		}
		parkNames.add("Exit");

		return (String) menu.getChoiceFromOptions(parkNames.toArray());
	}

	// Enters Campsites as number choices
	private Long getSiteSelectionFromUser(String selectedParkName, LocalDate startDate, LocalDate endDate,
			Campground campground) {
		List<Site> availableSites = siteDAO.checkAvailability(campground.getCampgroundId(), startDate, endDate);
		
		//gets the site id from teh object site
		List<Long> siteIDs = new ArrayList<>();
		for (Site site : availableSites) {
			siteIDs.add(site.getSiteId());
		}
	
		List<Campground> availableCampgrounds = campgroundDAO.checkAvailability(campground.getCampgroundId(), startDate,
				endDate);
	
		int count = printSiteList(availableSites, availableCampgrounds);//, openSites, openCampground);
		
		if (count>0) {
			printTotalCost(startDate, endDate, campground);
		}
		
		return (Long) menu.getChoiceFromOptions(siteIDs.toArray());

	}

	// Enters Campground names as number choices
	private String getCampgroundSelectionFromUser(String name) {
		System.out.println("\n ==================================\n Choose a Campground:");
		Park selectedPark = parkDAO.returnParkFromName(name);
		List<Campground> campgrounds = campgroundDAO.getCampgroundByPark(selectedPark.getParkId());

		List<String> campgroundNames = new ArrayList<>();
		for (Campground campgroundName : campgrounds) {
			campgroundNames.add(campgroundName.getName());
		}
		campgroundNames.add("Exit");

		return (String) menu.getChoiceFromOptions(campgroundNames.toArray());
	}
	
	
	// Gets & Returns customer check-in date
	private LocalDate getUserStart() {
		System.out.println("Enter the start date (YYYY/MM/DD): ");
		Scanner in = new Scanner(System.in);
		String userStartDate = in.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate startingDate = LocalDate.parse(userStartDate, formatter);
		return startingDate;
	}

	// Gets & Returns customer check-out date
	private LocalDate getUserEnd() {
		System.out.println("Enter the end date (YYYY/MM/DD): ");
		Scanner in = new Scanner(System.in);
		String userEndDate = in.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate endingDate = LocalDate.parse(userEndDate, formatter);
		return endingDate;
	}

	// Gets & returns todays date
	public LocalDate getTodaysDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		String Date = dtf.format(now);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate todaysDate = LocalDate.parse(Date, formatter);
		return todaysDate;
	}

	// Prints total cost from selected stay
	private void printTotalCost(LocalDate startDate, LocalDate EndDate, Campground campground) {
		System.out.println("The total cost of your stay is: $" + reservationDAO.totalcostOfStay(startDate, EndDate, campground));
	}


	public static String generateLargeSpace(String str) {
		int spaceLength = 32 - str.length();
		String space = "";

		for (int i = 1; i <= spaceLength; i++) {
			space += " ";
		}

		return space;
	}

	public static String generateSpace(String str) {
		int spaceLength = 15 - str.length();
		String space = "";

		for (int i = 1; i <= spaceLength; i++) {
			space += " ";
		}

		return space;
	}

	// Prints up to five available sites
	private int printSiteList(List<Site> sites, List<Campground> campgrounds){//, List<Site> openSites,
			//List<Campground> openCampgrounds) {
		int count = 0;

		System.out.println();

		System.out.println("Site ID" + generateSpace("Site ID") + "Site Number" + generateSpace("Site Number")
				+ "Campground Name" + generateLargeSpace("Campground Name") + "Open From" + generateSpace("Open From")
				+ "Open Until" + generateSpace("Open Until") + "Daily Fee");
		System.out.println(String.join("", Collections.nCopies(100, "-")));
//		while (newList.size() < 5) {
		
		
		
		if (sites.size()>0) {
			for (int i = 0; i < sites.size(); i++) {
				count += 1;
				System.out.println((sites.get(i).getSiteId() + generateSpace(sites.get(i).getSiteId().toString())
						+ sites.get(i).getSiteNumber() + generateSpace(sites.get(i).getSiteNumber().toString())
						+ campgrounds.get(i).getName() + generateLargeSpace(campgrounds.get(i).getName())
						+ campgroundDAO.getMonthFromNumber(campgrounds.get(i).getOpenFrom())
						+ generateSpace(campgroundDAO.getMonthFromNumber(campgrounds.get(i).getOpenFrom()))
						+ campgroundDAO.getMonthFromNumber(campgrounds.get(i).getOpenTo())
						+ generateSpace(campgroundDAO.getMonthFromNumber(campgrounds.get(i).getOpenTo())) + "$"
						+ campgrounds.get(i).getDailyFee()));
			}
		} else {
			System.out.println("\n*** No results ***");
			
		}
		
		

		return count;
	}

	// Prints heading before each menu selection
	private void printHeading(String headingText) {
		System.out.println("\n" + headingText);
		for (int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}

	// Prings campgrounds' details from selected Park
	public void printCampgroundsForPark(String name) {
		Park selectedPark = parkDAO.returnParkFromName(name);
		List<Campground> campgrounds = campgroundDAO.getCampgroundByPark(selectedPark.getParkId());
		System.out.println("\n" + "=========================================\n" + selectedPark.getName()
				+ " Park Campgrounds: \n");
		System.out.println("Campground Name" + generateLargeSpace("Campground Name") + "Open From"
				+ generateSpace("Open From") + "Open Until" + generateSpace("Open Until") + "Daily Fee");
		System.out.println(String.join("", Collections.nCopies(74, "-")));
		for (Campground camps : campgrounds) {
			System.out.println(camps.getName() + generateLargeSpace(camps.getName())
					+ campgroundDAO.getMonthFromNumber(camps.getOpenFrom())
					+ generateSpace(campgroundDAO.getMonthFromNumber(camps.getOpenFrom()))
					+ campgroundDAO.getMonthFromNumber(camps.getOpenTo())
					+ generateSpace(campgroundDAO.getMonthFromNumber(camps.getOpenTo())) + "$" + camps.getDailyFee());
		}
	}

	// Prints details about selected Park
	public void printParkInformation(String name) {
		Park selectedPark = parkDAO.returnParkFromName(name);
		System.out.println("=============================\n" + selectedPark.getName() + " Park information: ");
		System.out.println(generateLargeSpace("Location: ") + "Location: " + selectedPark.getLocation());
		System.out.println(
				generateLargeSpace("Established Year: ") + "Established Year: " + selectedPark.getEstablishDate());
		System.out.println(generateLargeSpace("Area: ") + "Area: " + selectedPark.getArea());
		System.out.println(generateLargeSpace("Annual Visitors: ") + "Annual Visitors: " + selectedPark.getVisitors());
		System.out.println("\n");
		String description = selectedPark.getDescription();
		String[] descriptionArray = description.split(" ");
		System.out.println(selectedPark.getName() + " Park Description: \n");
		for (int i = 0; i < (descriptionArray.length / 3 + 1); i++) {
			System.out.print(descriptionArray[i] + " ");
		}
		System.out.println();
		for (int i = descriptionArray.length / 3 + 1; i < descriptionArray.length / 3 + 1 + descriptionArray.length / 3
				+ 1; i++) {
			System.out.print(descriptionArray[i] + " ");
		}
		System.out.println();
		for (int i = descriptionArray.length / 3 + 1 + descriptionArray.length / 3
				+ 1; i < (descriptionArray.length); i++) {
			System.out.print(descriptionArray[i] + " ");
		}
		System.out.println("\n");

	}

}
