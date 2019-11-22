package com.techelevator.campground.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public interface ReservationDAO {
	
	public void makeAReservation(Reservation res);//LocalDate startDate, LocalDate endDate, LocalDate todaysDate, String reservationName, Long siteId);
	
	public BigDecimal totalcostOfStay(LocalDate startDate, LocalDate EndDate, Campground campground);
	
	public  Date asDate(LocalDate date);
	
	public long daysBetween(LocalDate start, LocalDate end);
	
	public Reservation searchForReservation(String name, Long site_id);
	
}
