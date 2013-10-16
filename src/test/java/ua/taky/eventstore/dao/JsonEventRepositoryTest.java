package ua.taky.eventstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.IOException;
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
				getClass().getResourceAsStream("SportEvents.json"));
	}

	@Test
	public void searchEventsTest() throws IOException{
		List<Event> actual = eventRepository.searchEvents(mock(EventsSearchRequest.class));
		assertNotNull(actual);
	}
	
	@Test
	public void searchEventTestByCity() throws IOException{
		EventsSearchRequest req = EventsSearchRequest.builder()
				.city("Amsterdam")
				.build();
		List<Event> eventList = eventRepository.searchEvents(req);
		assertEquals(8, eventList.size());
	}
	
	@Test
	public void searchEventTestByInterests() throws IOException{
		EventsSearchRequest req = EventsSearchRequest.builder()
				.interests("agile", "volleyball")
				.build();
		List<Event> eventList = eventRepository.searchEvents(req);
		assertEquals(6, eventList.size());
	}	

}