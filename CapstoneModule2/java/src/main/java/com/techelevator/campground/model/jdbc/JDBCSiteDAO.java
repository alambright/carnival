package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> checkAvailability(Long id, LocalDate startDate, LocalDate endDate) {
		List<Site> availableSitesList = new ArrayList<>();
		String sqlSites = "SELECT count(reservation.reservation_id), S.site_id, site_number, campground.name, open_from_mm, open_to_mm, daily_fee\r\n"
				+ "FROM site S " + "INNER JOIN reservation ON s.site_id = reservation.site_id "
				+ "INNER JOIN campground ON campground.campground_id = S.campground_id\r\n"
				+ "WHERE ((from_date > cast(? as date) or To_date <= cast(? as date)) "
				+ "        and (from_date >= cast(? as date) or to_date < cast(? as date))) "
				+ "        AND campground.campground_id = ? "
				+ "GROUP BY s.site_id, campground.name, open_from_mm, open_to_mm, daily_fee "
				+ "order by count(reservation.reservation_id) desc " + "limit 5";
		SqlRowSet results1 = jdbcTemplate.queryForRowSet(sqlSites, startDate, startDate, endDate, endDate, id);
		while (results1.next()) {
			Site site = new Site();
			site.setSiteId(results1.getLong("site_id"));
			site.setSiteNumber(results1.getInt("site_number"));

			if (!siteExists(availableSitesList, results1.getLong("site_id"))) {
				availableSitesList.add(site);
			}
		}
		List<Site> openSitesList = openAvailability(id);
		List<Site> topSites = combineList(availableSitesList, openSitesList);
		return topSites;
	}

	public boolean siteExists(List<Site> siteLists, Long id) {
		for (Site thisSite : siteLists) {
			if (thisSite.getSiteId() == id) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Site> openAvailability(Long id) {
		List<Site> openSitesList = new ArrayList<>();
		String sqlOpenSites = "SELECT site.site_id, site_number, campground.name, open_from_mm, open_to_mm, daily_fee FROM SITE "
				+ "INNER JOIN campground " + "on site.campground_id = campground.campground_id "
				+ "WHERE site_id not in ( " + "SELECT S.site_id FROM site S " + "INNER JOIN reservation "
				+ "ON s.site_id = reservation.site_id " + "INNER JOIN campground "
				+ "ON campground.campground_id = site.campground_id "
				+ "WHERE campground.campground_id = ?) AND campground.campground_id = ? " + "LIMIT 5";
		SqlRowSet results2 = jdbcTemplate.queryForRowSet(sqlOpenSites, id, id);
		while (results2.next()) {
			Site site = new Site();
			site.setSiteId(results2.getLong("site_id"));
			site.setSiteNumber(results2.getInt("site_number"));
			openSitesList.add(site);
		}
		return openSitesList;
	}

	@Override
	public List<Site> combineList(List<Site> list1, List<Site> list2) {
		List<Site> topSites = new ArrayList<>();
		
		if (list1.size() == 5) { 						// if List1 has 5
			for (int i = 0; i < list1.size(); i++) {
				topSites.add(list1.get(i));
			}
		} else if(list1.size()<5 && list1.size()>0) { 	// if List1 doesnt have 5
			for (int i = 0; i < list1.size(); i++) {
				topSites.add(list1.get(i));
			}
			if (list2.size() ==5) { 					//if list 2 has 5
				for (int i = 0; i < 5; i++) {
					topSites.add(list2.get(i));
				}
			} else if (list2.size() < 5 && list2.size() > 0) { //if list 2 doesnt have 5
				for (int i = 0; i < list2.size(); i++) {
					topSites.add(list2.get(i));
				}
			}
		} else if (list1.size() == 0) { 				// if List1 has zero
			if (list2.size() ==5) { 					//if list 2 has 5
				for (int i = 0; i < 5; i++) {
					topSites.add(list2.get(i));
				}
			} else if (list2.size() < 5 && list2.size() > 0) { //if list 2 doesnt have 5
				for (int i = 0; i < list2.size(); i++) {
					topSites.add(list2.get(i));
				}
			}
		} 
		return topSites;
	}

}
