package ua.taky.eventstore.dao;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.minidev.json.JSONObject;

import org.joda.time.DateTime;
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
	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd mm:ss";
	private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);	
	
	private Provider<Set<InputStream>> resourceStreamsProvider;
	
	@Override
	public List<Event> searchEvents(EventsSearchRequest req) throws IOException {
		LOGGER.debug("Start search for request {}", req);
		Filter<Map<String, Object>> filter = new EventsSearchRequestFilter(req);
		List<JSONObject> eventJsonList = new ArrayList<>();
		for(InputStream jsonStream : resourceStreamsProvider.get()){
			//TODO add paging
			List<JSONObject> curEventList = JsonPath.read(jsonStream, "$.[?]", filter);
			eventJsonList.addAll(curEventList);
		}
		LOGGER.debug("Was found {} recods: {}", eventJsonList.size(), eventJsonList);
		return convertToEvents(eventJsonList);
	}

	private List<Event> convertToEvents(List<JSONObject> eventJsonList) {
		List<Event> eventsList = new ArrayList<>();
		for(JSONObject curObj : eventJsonList){
			try {
				eventsList.add(convertToEvent(curObj));
			} catch (ParseException | NumberFormatException e) {
				LOGGER.warn("Corrupted data - " + curObj, e);
			}
		}
		return eventsList;
	}

	private Event convertToEvent(JSONObject curObj) throws NumberFormatException, ParseException {
		return new Event(curObj.get("title").toString(),
				curObj.get("city").toString(),
				toDateTime(curObj.get("start").toString()),
				toDateTime(curObj.get("end").toString()),
				Integer.parseInt(curObj.get("price").toString()));
	}

	private DateTime toDateTime(String string) throws ParseException {
		return new DateTime(dateFormat.parse(string.replace("T", " ")).getTime());
	}

	@Inject
	public void setJsonSource(@JsonSource Provider<Set<InputStream>> resourceStreamsProvider) {
		this.resourceStreamsProvider = resourceStreamsProvider;
	}

}