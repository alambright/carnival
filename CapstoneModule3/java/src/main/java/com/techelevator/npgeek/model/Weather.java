package com.techelevator.npgeek.model;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Weather {

	private static final String ABOVE_SEVENTYFIVE = "Bring an extra gallon of water.";
	private static final String TEMPERATURE_DIFFERENCE = "Wear breathable layers.";
	private static final String BELOW_TWENTY = "Be aware of the dangers of exposure to frigid temperatures.";

	private static Map<String, String> messages;

	static {
		messages = new HashMap<>();
		messages.put("snow", "Pack snowshoes.");
		messages.put("rain", "Pack rain gear and wear waterproof shoes");
		messages.put("thunderstorms", "Seek shelter and avoid hiking on exposed ridges.");
		messages.put("sunny", "Pack sunblock.");
	}

	private String parkCode; // VARCHAR(10) NOT NULL,
	private Integer fiveDayForecastValue; // INTEGER NOT NULL,
	private Integer low; // INTEGER NOT NULL,
	private Integer high; // INTEGER NOT NULL,
	private String forecast; // VARCHAR(100) NOT NULL,

	/**
	 * @return the parkCode
	 */
	public String getParkCode() {
		return this.parkCode;
	}

	/**
	 * @return the fiveDayForecastValue
	 */
	public Integer getFiveDayForecastValue() {
		return this.fiveDayForecastValue;
	}

	/**
	 * @return the low
	 */
	public Integer getLow() {
		return this.low;
	}

	/**
	 * @return the high
	 */
	public Integer getHigh() {
		return this.high;
	}

	/**
	 * @return the forecast
	 */
	public String getForecast() {
		return this.forecast;
	}

	/**
	 * @param parkCode the parkCode to set
	 */
	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}

	/**
	 * @param fiveDayForecastValue the fiveDayForecastValue to set
	 */
	public void setFiveDayForecastValue(Integer fiveDayForecastValue) {
		this.fiveDayForecastValue = fiveDayForecastValue;
	}

	/**
	 * @param low the low to set
	 */
	public void setLow(Integer low) {
		this.low = low;
	}

	/**
	 * @param high the high to set
	 */
	public void setHigh(Integer high) {
		this.high = high;
	}

	/**
	 * @param forecast the forecast to set
	 */
	public void setForecast(String forecast) {
		this.forecast = forecast;
	}

	public Integer getLowInC() {
		int fahr = this.low.intValue();
		return Integer.valueOf((fahr - 32) * 5 / 9);
	}

	public Integer getHighInC() {
		int fahr = this.high.intValue();
		return Integer.valueOf((fahr - 32) * 5 / 9);
	}

	public String getForecastMessage() {
		return messages.getOrDefault(this.forecast, "");
	}

	public String getTemperatureMesage() {
		String message = "";
		if (this.high.intValue() > 75)
			message = ABOVE_SEVENTYFIVE;
		if (this.high.intValue() - this.low.intValue() > 20)
			message += " " + TEMPERATURE_DIFFERENCE;
		if (this.low.intValue() < 20)
			message += " " + BELOW_TWENTY;
		return message;

	}

	public String getDayOfWeek() {
		String day = "Today";
		if (this.fiveDayForecastValue.intValue() > 1)
			day = LocalDateTime.now().plusDays(this.fiveDayForecastValue.longValue() - 1).getDayOfWeek()
					.getDisplayName(TextStyle.FULL, new Locale("en", "US"));
		return day;
	}

	public String getWeatherImgName() {
		String delimiter = " ";
		int counter = 0;
		StringBuffer sb = new StringBuffer();
		for (String s : this.forecast.split(delimiter)) {
			if (counter == 0)
				sb.append(Character.toLowerCase(s.charAt(0)));
			else
				sb.append(Character.toUpperCase(s.charAt(0)));
			if (s.length() > 1)
				sb.append(s.substring(1, s.length()).toLowerCase());

			counter++;
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Weather))
			return false;
		Weather w = (Weather) obj;

		return this.parkCode.equals(w.getParkCode()) && this.fiveDayForecastValue.equals(w.getFiveDayForecastValue())
				&& this.low.equals(w.getLow()) && this.high.equals(w.getHigh())
				&& this.forecast.equals(w.getForecast());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.parkCode, this.fiveDayForecastValue, this.low, this.high, this.forecast);
	}

}
