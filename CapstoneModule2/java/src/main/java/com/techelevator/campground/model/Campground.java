package com.techelevator.campground.model;

import java.math.BigDecimal;

public class Campground {

	private Long campgroundId;
	private Long parkId;
	private String name;
	private Integer openFrom;
	private Integer openTo;
	private BigDecimal dailyFee;
	
	
	public Long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public Long getParkId() {
		return parkId;
	}
	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOpenFrom() {
		return openFrom;
	}
	public void setOpenFrom(Integer openFrom) {
		this.openFrom = openFrom;
	}
	public Integer getOpenTo() {
		return openTo;
	}
	public void setOpenTo(Integer openTo) {
		this.openTo = openTo;
	}
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}
	
}
