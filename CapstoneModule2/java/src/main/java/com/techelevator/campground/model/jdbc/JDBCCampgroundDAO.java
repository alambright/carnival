package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.Site;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> getCampgroundByPark(Long id) {
		List<Campground> campgroundList = new ArrayList<>();
		String sqlParks = "SELECT * FROM campground INNER JOIN park ON park.park_id = campground.park_id WHERE park.park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlParks, id);

		while (results.next()) {
			Campground campground = new Campground();
			campground.setCampgroundId(results.getLong("campground_id"));
			campground.setParkId(results.getLong("park_id"));
			campground.setName(results.getString("name"));
			campground.setOpenFrom(results.getInt("open_from_mm"));
			campground.setOpenTo(results.getInt("open_to_mm"));
			campground.setDailyFee(results.getBigDecimal("daily_fee"));
			campgroundList.add(campground);
		}
		return campgroundList;
	}

	@Override
	public Campground getCampgroundFromName(String name) {
		String sqlGetCampgroundObject = "SELECT * FROM campground WHERE name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCampgroundObject, name);
		results.next();
		Campground campground = new Campground();
		campground.setCampgroundId(results.getLong("campground_id"));
		campground.setParkId(results.getLong("park_id"));
		campground.setName(results.getString("name"));
		campground.setOpenFrom(results.getInt("open_from_mm"));
		campground.setOpenTo(results.getInt("open_to_mm"));
		campground.setDailyFee(results.getBigDecimal("daily_fee"));
		return campground;
	}

	@Override
	public String getMonthFromNumber(Integer months) {
		String monthsArr[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		String month = "";
		if (months == 1) {
			month = monthsArr[0];
		} else if (months == 2) {
			month = monthsArr[1];
		} else if (months == 3) {
			month = monthsArr[2];
		} else if (months == 4) {
			month = monthsArr[3];
		} else if (months == 5) {
			month = monthsArr[4];
		} else if (months == 6) {
			month = monthsArr[5];
		} else if (months == 7) {
			month = monthsArr[6];
		} else if (months == 8) {
			month = monthsArr[7];
		} else if (months == 9) {
			month = monthsArr[8];
		} else if (months == 10) {
			month = monthsArr[9];
		} else if (months == 11) {
			month = monthsArr[10];
		} else if (months == 12) {
			month = monthsArr[11];
		}
		return month;
	}

	@Override
	public List<Campground> checkAvailability(Long id, LocalDate startDate, LocalDate endDate) {
		List<Campground> availableCampgroundList = new ArrayList<>();
		String sqlSites = "SELECT count(reservation.reservation_id), S.site_id, site_number, campground.name, open_from_mm, open_to_mm, daily_fee\r\n"
				+ "FROM site S " + "INNER JOIN reservation ON s.site_id = reservation.site_id "
				+ "INNER JOIN campground ON campground.campground_id = S.campground_id\r\n"
				+ "WHERE ((from_date > cast(? as date) or To_date <= cast(? as date)) "
				+ "        and (from_date >= cast(? as date) or to_date < cast(? as date))) "
				+ "        AND campground.campground_id = ? "
				+ "GROUP BY s.site_id, campground.name, open_from_mm, open_to_mm, daily_fee "
				+ "order by count(reservation.reservation_id) desc " + "limit 5";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSites, startDate, startDate, endDate, endDate, id);
		while (results.next()) {
			Campground campground = new Campground();
			campground.setName(results.getString("name"));
			campground.setOpenFrom(results.getInt("open_from_mm"));
			campground.setOpenTo(results.getInt("open_to_mm"));
			campground.setDailyFee(results.getBigDecimal("daily_fee"));
			availableCampgroundList.add(campground);
		}

		List<Campground> openCampgroundList = openAvailability(id);
		List<Campground> topCampground = combineList(availableCampgroundList, openCampgroundList);
		
		return topCampground;
	}

	@Override
	public List<Campground> openAvailability(Long id) {
		List<Campground> openCampgroundList = new ArrayList<>();
		String sqlCampground = "SELECT site.site_id, campground.name, open_from_mm, open_to_mm, daily_fee FROM SITE "
				+ "INNER JOIN campground " + "on site.campground_id = campground.campground_id "
				+ "WHERE site_id not in ( " + "SELECT S.site_id FROM site S " + "INNER JOIN reservation "
				+ "ON s.site_id = reservation.site_id " + "INNER JOIN campground "
				+ "ON campground.campground_id = site.campground_id "
				+ "WHERE campground.campground_id = ?) AND campground.campground_id = ? LIMIT 5";
		SqlRowSet results2 = jdbcTemplate.queryForRowSet(sqlCampground, id, id);
		while (results2.next()) {
			Campground campground = new Campground();
			campground.setName(results2.getString("name"));
			campground.setOpenFrom(results2.getInt("open_from_mm"));
			campground.setOpenTo(results2.getInt("open_to_mm"));
			campground.setDailyFee(results2.getBigDecimal("daily_fee"));
			openCampgroundList.add(campground);

		}
		return openCampgroundList;

	}

	@Override
	public List<Campground> combineList(List<Campground> list1, List<Campground> list2) {
		List<Campground> topCampground = new ArrayList<>();

		if (list1.size() == 5) { // if 1 is 5
			for (int i = 0; i < list1.size(); i++) {
				topCampground.add(list1.get(i));
			}
		} else if (list1.size() < 5 && list1.size() > 0) { // if list 1 doesnt have 5
			for (int i = 0; i < list1.size(); i++) {
				topCampground.add(list1.get(i));
				
			}
			if (list2.size() == 5) { // if list 2 has 5
				for (int i = 0; i < 5; i++) {
					topCampground.add(list2.get(i));
				}
			} else if (list2.size() < 5 && list2.size() > 0) { // if list 2 doesnt have 5
				for (int i = 0; i < list2.size(); i++) {
					topCampground.add(list2.get(i));
				}
			}
		} else if (list1.size() == 0) { // if 1 is zero
			if (list2.size() == 5) { // if list 2 has 5
				for (int i = 0; i < 5; i++) {
					topCampground.add(list2.get(i));
				}
			} else if (list2.size() < 5 && list2.size() > 0) { // if list 2 doesnt have 5
				for (int i = 0; i < list2.size(); i++) {
					topCampground.add(list2.get(i));
				}
			}
		}

		return topCampground;
	}
}
