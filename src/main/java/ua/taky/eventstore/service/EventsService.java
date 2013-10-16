package ua.taky.eventstore.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ua.taky.eventstore.dao.IEventsRepository;
import ua.taky.eventstore.domain.Event;

@Singleton
public class EventsService {
	
	private IEventsRepository eventsRepository;

	public List<Event> searchEvents(EventsSearchRequest request) {
		return eventsRepository.searchEvents(request);
	}

	@Inject
	public void setEventsRepository(IEventsRepository eventsRepository) {
		this.eventsRepository = eventsRepository;
	}

}
