package ua.taky.eventstore.dao;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import ua.taky.eventstore.service.EventsSearchRequest;

import com.jayway.jsonpath.Filter;

public class EventsSearchRequestFilter extends Filter.FilterAdapter<Map<String, Object>> {
	
	private EventsSearchRequest req;

	public EventsSearchRequestFilter(EventsSearchRequest req){
		this.req = req;
	}
	
	@Override
	public boolean accept(Map<String, Object> rhs){
		if(StringUtils.isNotBlank(req.city)){
			if(!req.city.equals(rhs.get("city"))){
				return false;
			}
		}
		if(StringUtils.isNotBlank(req.interest)){
			if(!req.city.equals(rhs.get("interest"))){
				return false;
			}			
		}
		return true;
	}
}