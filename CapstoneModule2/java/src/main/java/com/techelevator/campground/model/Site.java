package com.techelevator.campground.model;

public class Site {

	private Long siteId;
	private Long campgroundId;
	private Integer siteNumber;
	private Integer maxOccupancy;
	private boolean isAccessible;
	private Integer maxRVLength;
	private boolean utilities;
	
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public Long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public Integer getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(Integer siteNumber) {
		this.siteNumber = siteNumber;
	}
	public Integer getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(Integer maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public boolean isAccessible() {
		return isAccessible;
	}
	public void setAccessible(boolean isAccessible) {
		this.isAccessible = isAccessible;
	}
	public Integer getMaxRVLength() {
		return maxRVLength;
	}
	public void setMaxRVLength(Integer maxRVLength) {
		this.maxRVLength = maxRVLength;
	}
	public boolean isUtilities() {
		return utilities;
	}
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}

}
