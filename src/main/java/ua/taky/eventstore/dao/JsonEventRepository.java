package ua.taky.eventstore.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.minidev.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.taky.eventstore.JsonSource;
import ua.taky.eventstore.domain.Event;
import ua.taky.eventstore.service.EventsSearchRequest;

import com.google.inject.Provider;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;

@Singleton
public class JsonEventRepository implements IEventsRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonEventRepository.class);
	
	private Provider<Set<InputStream>> resourceStreamsProvider;
	
	@Override
	public List<Event> searchEvents(EventsSearchRequest req) throws IOException {
		LOGGER.debug("Start search for request {}", req);
		Filter<Map<String, Object>> filter = new EventsSearchRequestFilter(req);
		List<Event> eventList = new ArrayList<>();
		for(InputStream jsonStream : resourceStreamsProvider.get()){
			List<Event> curEventList = JsonPath.read(jsonStream, "$.[?]", filter);
			eventList.addAll(curEventList);
		}
		LOGGER.debug("Was found {} recods: {}", eventList.size(), eventList);
		return eventList;
	}

	@Inject
	public void setJsonSource(@JsonSource Provider<Set<InputStream>> resourceStreamsProvider) {
		this.resourceStreamsProvider = resourceStreamsProvider;
	}

}