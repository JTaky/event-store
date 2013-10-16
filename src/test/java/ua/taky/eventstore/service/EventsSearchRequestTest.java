package ua.taky.eventstore.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class EventsSearchRequestTest {
	
	private static final String city = "Kiev";
	private static final String day = "20131220";
	private static final String[] interests = new String[]{"Ajax FC"};
	private static final Integer budget = 500;	
	
	@Test
	public void createBuilderTest(){
		assertNotNull(EventsSearchRequest.builder());
	}
	
	@Test
	public void buildEmptyObjectTest(){
		EventsSearchRequest req = EventsSearchRequest.builder().build();
		assertNull(req.date);
	}
	
	@Test
	public void builderWithCityTest(){
		EventsSearchRequest req = EventsSearchRequest.builder().city(city)
				.date(day)
				.interests(interests)
				.budget(budget)
				.build();
		assertEquals(city, req.city);
		assertArrayEquals(interests, req.interests);
		assertEquals(budget, req.budget);
	}

}