package ua.taky.eventstore.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encapsulate search parameters into request object.
 * 
 * @author taky
 */
public class EventsSearchRequest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EventsSearchRequest.class);
	private static final String DATE_FORMAT_PATTERN = "yyyyMMdd";
	private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
	
	public static EventsSearchRequest buildRequest(String city, String dateStr, String interest, Integer budget){
		try {
			return new EventsSearchRequest(city, toDate(dateStr), interest, budget);
		} catch (ParseException e) {
			String errMsg = String.format("Cannot parse date %s, expected %s format", 
					dateStr, DATE_FORMAT_PATTERN);
			LOGGER.error(errMsg, e);
			throw new RuntimeException(errMsg, e);
		}
	}
	
	private static Date toDate(String dateStr) throws ParseException {
		if(StringUtils.isBlank(dateStr)){
			return null;
		} else {
			return dateFormat.parse(dateStr);
		}
	}

	public final String city;
	
	public final Date date;
	
	public final String interest;
	
	public final Integer budget;
	
	private EventsSearchRequest(String city, Date date, String interest, Integer budget){
		this.city = city;
		this.date = date;
		this.interest = interest;
		this.budget = budget;
	}
	
	@Override
	public String toString() {
		return "EventsSearchRequest [city=" + city + ", date=" + date
				+ ", interest=" + interest + ", budget=" + budget + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(budget)
				.append(city)
				.append(date)
				.append(interest)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventsSearchRequest other = (EventsSearchRequest) obj;
		if (budget == null) {
			if (other.budget != null)
				return false;
		} else if (!budget.equals(other.budget))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (interest == null) {
			if (other.interest != null)
				return false;
		} else if (!interest.equals(other.interest))
			return false;
		return true;
	}

}
