package ua.taky.eventstore.dao;

import java.io.IOException;
import java.util.List;

import ua.taky.eventstore.domain.Event;
import ua.taky.eventstore.service.EventsSearchRequest;

public interface IEventsRepository {

	List<Event> searchEvents(EventsSearchRequest req) throws IOException;

}
