package ua.taky.eventstore.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ua.taky.eventstore.dao.IEventsRepository;
import ua.taky.eventstore.domain.Event;

public class EventsServiceTest {

	private EventsService eventsService;
	
	private IEventsRepository eventsRepository;
	
	@Before
	public void setUp(){
		eventsService = new EventsService();
		eventsRepository = mock(IEventsRepository.class);
		eventsService.setEventsRepository(eventsRepository);
	}
	
	@Test
	public void serarchEventsTest(){
		List<Event> someEvents 
			= eventsService.searchEvents(mock(EventsSearchRequest.class));
		assertNotNull(someEvents);
	}
	
	@Test
	public void serarchEventsPassRequestToDao() throws IOException {
		EventsSearchRequest mockRequest = mock(EventsSearchRequest.class);
		eventsService.searchEvents(mockRequest);
		verify(eventsRepository, Mockito.atLeastOnce()).searchEvents(mockRequest);
	}
	
	@Test
	public void selectTopByBudget() {
		List<Event> events = new ArrayList<>();
		events.add(new Event("title1", "city1", new DateTime(2013, 1, 1, 8, 0), new DateTime(2013, 1, 1, 9, 0), 20));
		events.add(new Event("title2", "city2", new DateTime(2013, 1, 1, 9, 20), new DateTime(2013, 1, 1, 9, 40), 90));
		events.add(new Event("title3", "city3", new DateTime(2013, 1, 1, 11, 0), new DateTime(2013, 1, 1, 11, 50), 30));
		events.add(new Event("title4", "city4", new DateTime(2013, 1, 1, 12, 0), new DateTime(2013, 1, 1, 14, 0), 80));
		List<Event> actual = eventsService.selectTopByBudget(EventsSearchRequest.builder().budget(100).build(), events);
		
		List<Event> expected = new ArrayList<>();
		expected.add(new Event("title1", "city1", new DateTime(2013, 1, 1, 8, 0), new DateTime(2013, 1, 1, 9, 0), 20));
		expected.add(new Event("title3", "city3", new DateTime(2013, 1, 1, 11, 0), new DateTime(2013, 1, 1, 11, 50), 30));
		assertEquals(expected, actual);
	}
	
	@Test
	public void selectTopByBudgetEmpty() {
		List<Event> events = new ArrayList<>();
		events.add(new Event("title1", "city1", new DateTime(), new DateTime(), 120));
		events.add(new Event("title2", "city2", new DateTime(), new DateTime(), 110));
		List<Event> actual = eventsService.selectTopByBudget(EventsSearchRequest.builder().budget(100).build(), events);
		
		List<Event> expected = new ArrayList<>();
		assertEquals(expected, actual);
	}
	
	@Test
	public void selectTopByIntersetionEmpty() {
		List<Event> events = new ArrayList<>();
		events.add(new Event("title1", "city1", new DateTime(2013, 1, 1, 8, 0), new DateTime(2013, 1, 1, 9, 0), 40));
		events.add(new Event("title2", "city2", new DateTime(2013, 1, 1, 9, 0), new DateTime(2013, 1, 1, 9, 50), 20));
		events.add(new Event("title2", "city2", new DateTime(2013, 1, 1, 9, 30), new DateTime(2013, 1, 1, 10, 50), 20));
		List<Event> actual = eventsService.selectTopByBudget(EventsSearchRequest.builder().budget(100).build(), events);
		
		List<Event> expected = new ArrayList<>();
		expected.add(new Event("title2", "city2", new DateTime(2013, 1, 1, 9, 0), new DateTime(2013, 1, 1, 9, 50), 20));
		expected.add(new Event("title1", "city1", new DateTime(2013, 1, 1, 8, 0), new DateTime(2013, 1, 1, 9, 0), 40));
		assertEquals(expected, actual);
	}	
	
}
