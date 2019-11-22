package com.techelevator.npgeek.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.npgeek.model.ParkDao;
import com.techelevator.npgeek.model.Survey;
import com.techelevator.npgeek.model.SurveyDao;
import com.techelevator.npgeek.model.Weather;
import com.techelevator.npgeek.model.WeatherDao;

@Controller
public class UserController {

	@Autowired
	private ParkDao parkDao;

	@Autowired
	private WeatherDao weatherDao;

	@Autowired
	private SurveyDao surveyDao;

	@GetMapping("/")
	public String displayParks(ModelMap model) {
		model.addAttribute("parks", parkDao.getAllParks());
		return "home";
	}

	@GetMapping("/details")
	public String displayDetails(@RequestParam String code, ModelMap model) {
		model.addAttribute("park", parkDao.getParkByParkCode(code));
		model.addAttribute("weathers", weatherDao.getWeatherByParkCode(code));
		return "details";
	}

	@PostMapping("/details")
	public String changeTemperatureUnit(@RequestParam Boolean chooseCelcius, @RequestParam String code,
			HttpSession session) {
		session.setAttribute("inCelcius", chooseCelcius);
		return "redirect:/details?code=" + code;
	}

	@GetMapping("/survey")
	public String displaySurvey(ModelMap model) {
		model.addAttribute("parks", parkDao.getAllParks());
		if (!model.containsAttribute("survey"))
			model.addAttribute("survey", new Survey());

		return "survey";
	}

	@PostMapping("/submitSurvey")
	public String submitSurvey(@Valid @ModelAttribute Survey survey, BindingResult result, RedirectAttributes flash) {
		if (result.hasErrors()) {
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "survey", result);
			flash.addFlashAttribute("survey", survey);
			return "redirect:/survey";
		}

		surveyDao.saveSurvey(survey);

		return "redirect:/favorites";

	}

	@GetMapping
	public String displayFavoriteParks(ModelMap models) {
		models.addAttribute("favoriteParks", parkDao.getFavoriteParks());
		return "favorites";
	}
}
