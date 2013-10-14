package ua.taky.eventstore.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import ua.taky.eventstore.domain.Event;

@Singleton
public class EventsService {

	public List<Event> searchEvents(EventsSearchRequest req) {
		return new ArrayList<>();
	}

}
