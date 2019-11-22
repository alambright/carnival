package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {
	
	public List<Site> checkAvailability(Long id, LocalDate startDate, LocalDate endDate);
	
	public List<Site> openAvailability(Long id);
	
	public List<Site> combineList(List<Site> list1, List<Site> list2);

}
