package ua.taky.eventstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ua.taky.eventstore.domain.Event;
import ua.taky.eventstore.service.EventsSearchRequest;

public class JsonEventRepositoryTest {
	
	private JsonEventRepository eventRepository;
	
	@Before
	public void setUp(){
		eventRepository = new JsonEventRepository();
		eventRepository.setJsonSource(getClass().getResourceAsStream("Conferences.json"), 
				getClass().getResourceAsStream("Conferences.json"));
	}

	@Test
	public void searchEventsTest(){
		List<Event> actual = eventRepository.searchEvents(mock(EventsSearchRequest.class));
		assertNotNull(actual);
	}
	
	@Test
	public void searchEventTestByCity(){
		EventsSearchRequest req = EventsSearchRequest.builder()
				.city("Amsterdam")
				.build();
		List<Event> eventList = eventRepository.searchEvents(req);
		assertEquals(8, eventList.size());
	}

}