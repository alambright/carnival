package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public interface CampgroundDAO {

	public List<Campground> getCampgroundByPark(Long id);
	
	public Campground getCampgroundFromName(String name);
	
	public String getMonthFromNumber(Integer month);
	
	public List<Campground> checkAvailability(Long id, LocalDate startDate, LocalDate endDate);
	
	public List<Campground> openAvailability(Long id);
	
	public List<Campground> combineList(List<Campground> list1, List<Campground> list2);
	
}
