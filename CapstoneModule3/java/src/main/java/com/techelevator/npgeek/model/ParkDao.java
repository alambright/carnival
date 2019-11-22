package com.techelevator.npgeek.model;

import java.util.List;

public interface ParkDao {
	
	List<Park> getAllParks();
	
	Park getParkByParkCode(String code);
	
	List<Park> getFavoriteParks();

}
