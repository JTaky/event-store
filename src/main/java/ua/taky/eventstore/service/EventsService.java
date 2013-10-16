package ua.taky.eventstore.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.taky.eventstore.dao.IEventsRepository;
import ua.taky.eventstore.domain.Event;

@Singleton
public class EventsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EventsService.class);
	
	private IEventsRepository eventsRepository;

	public List<Event> searchEvents(EventsSearchRequest request) {
		try {
			List<Event> events = eventsRepository.searchEvents(request);
			if(request.budget != null && request.budget > 0){
				events = selectTopByBudget(request, events);
			}
			return events;
		} catch (IOException e) {
			String msg = "Cannot access to data source";
			LOGGER.error(msg, e);
			throw new RuntimeException(msg, e);
		}
	}

	List<Event> selectTopByBudget(EventsSearchRequest request,
			List<Event> events) {
		//TODO research data size, research metaheuristic algorithm or choose optimal heuristic
		Collections.sort(events, new Comparator<Event>(){
			@Override
			public int compare(Event lhs, Event rhs) {
				return lhs.price - rhs.price;
			}
		});
		List<Event> topEvents = new ArrayList<>();
		int curBudget = 0;
		for(Event curEvent : events){
			if(curBudget + curEvent.price > request.budget){
				break;
			}
			curBudget += curEvent.price;
			if(!isIntersects(topEvents, curEvent)){
				topEvents.add(curEvent);
			}
		}
		return topEvents;
	}

	private boolean isIntersects(List<Event> topEvents, Event anotherEvent) {
		for(Event curEvent : topEvents){
			if(curEvent.isIntersect(anotherEvent)){
				return true;
			}
		}
		return false;
	}

	@Inject
	public void setEventsRepository(IEventsRepository eventsRepository) {
		this.eventsRepository = eventsRepository;
	}

}
