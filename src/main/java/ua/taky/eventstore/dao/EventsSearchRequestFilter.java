package ua.taky.eventstore.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.taky.eventstore.service.EventsSearchRequest;

import com.jayway.jsonpath.Filter;

public class EventsSearchRequestFilter extends Filter.FilterAdapter<Map<String, Object>> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EventsSearchRequest.class);
	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
	private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
	
	private EventsSearchRequest req;

	public EventsSearchRequestFilter(EventsSearchRequest req){
		this.req = req;
	}
	
	@Override
	public boolean accept(Map<String, Object> rhs){
		//TODO must be refactored(in ideal should use predicates)
		if(StringUtils.isNotBlank(req.city) && rhs.containsKey("city")){
			if(!req.city.equals(rhs.get("city"))){
				return false;
			}
		}
		if(req.date != null && rhs.containsKey("start")){
			try {
				Date rhsDate = dateFormat.parse(rhs.get("start").toString().split("T")[0]);
				java.sql.Date eventDay = new java.sql.Date(rhsDate.getTime());
				java.sql.Date filterDay = new java.sql.Date(req.date.getTime());
				if (!filterDay.equals(eventDay)) {
					return false;
				}
			} catch(ParseException e) {
				LOGGER.warn("Illegal date format in source - " + rhs, e);
			}
		}
		if (req.interests != null && req.interests.length > 0 && isContainInterests(rhs)) {
			String eventInterests = getInterests(rhs);
			boolean isFoundIntersection = false;
			for(String curInterest : req.interests){
				if(eventInterests.toUpperCase().contains(curInterest.toUpperCase())){
					isFoundIntersection = true;
					break;
				}
			}
			if(!isFoundIntersection){
				return false;
			}
		}
		if (req.budget != null && req.budget != 0 && rhs.containsKey("budget")) {
			int eventBudget = 0;
			try {
				eventBudget = Integer.parseInt(rhs.get("budget").toString());
			} catch (NumberFormatException e) {
				LOGGER.warn("Illegal budget format in source - " + rhs, e);
			}
			if (eventBudget != 0 && req.budget < eventBudget) {
				return false;
			}
		}
		return true;
	}

	private String getInterests(Map<String, Object> rhs) {
		return rhs.containsKey("topics") ? rhs.get("topics").toString() :
			rhs.containsKey("sport") ? rhs.get("sport").toString() :
			rhs.containsKey("genres") ? rhs.get("genres").toString() : "";
	}

	private boolean isContainInterests(Map<String, Object> rhs) {
		return rhs.containsKey("topics") || rhs.containsKey("sport") || rhs.containsKey("genres");
	}
}