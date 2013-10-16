package ua.taky.eventstore.service;

import java.io.IOException;
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
			return eventsRepository.searchEvents(request);
		} catch (IOException e) {
			String msg = "Cannot access to data source";
			LOGGER.error(msg, e);
			throw new RuntimeException(msg, e);
		}
	}

	@Inject
	public void setEventsRepository(IEventsRepository eventsRepository) {
		this.eventsRepository = eventsRepository;
	}

}
