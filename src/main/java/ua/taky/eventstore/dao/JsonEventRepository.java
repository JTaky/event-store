package ua.taky.eventstore.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import ua.taky.eventstore.domain.Event;
import ua.taky.eventstore.service.EventsSearchRequest;

@Singleton
public class JsonEventRepository implements IEventsRepository {
	
	@Override
	public List<Event> searchEvents(EventsSearchRequest req) {
		return new ArrayList<>();
	}

	public void setJsonSource(InputStream... resourceStreams) {
		
	}

}
