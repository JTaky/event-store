package ua.taky.eventstore.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ua.taky.eventstore.service.EventsSearchRequest;

public class EventsSearchRequestFilterTest {
	
	@Test
	public void testFilterEmpty(){
		EventsSearchRequest req = EventsSearchRequest.builder().build();
		EventsSearchRequestFilter eventsFilter = new EventsSearchRequestFilter(req);
		assertTrue(eventsFilter.accept(new HashMap<String, Object>()));
	}
	
	@Test
	public void testFilterByCityTrue(){
		EventsSearchRequest req = EventsSearchRequest.builder().city("Kiev").build();
		EventsSearchRequestFilter eventsFilter = new EventsSearchRequestFilter(req);
		@SuppressWarnings("serial")
		Map<String, Object> event = new HashMap<String, Object>(){{ put("city", "Kiev"); }};
		assertTrue(eventsFilter.accept(event));
	}

	@Test
	public void testFilterByCityFalse(){
		EventsSearchRequest req = EventsSearchRequest.builder().city("Rome").build();
		EventsSearchRequestFilter eventsFilter = new EventsSearchRequestFilter(req);
		@SuppressWarnings("serial")
		Map<String, Object> event = new HashMap<String, Object>(){{ put("city", "Kiev"); }};
		assertFalse(eventsFilter.accept(event));
	}
	
	@Test
	public void testFilterByCriteriaTrue(){
		EventsSearchRequest req = EventsSearchRequest.builder()
				.city("Rome")
				.date("20130112")
				.interest("software development")
				.budget(100)
				.build();
		EventsSearchRequestFilter eventsFilter = new EventsSearchRequestFilter(req);
		@SuppressWarnings("serial")
		Map<String, Object> event = new HashMap<String, Object>(){{ 
			put("city", "Rome");
			put("start", "2013-01-12T10:00");
			put("interest", "software development");
			put("budget", "90");
		}};
//		assertTrue(eventsFilter.accept(event));
	}
	
	@Test
	public void testFilterByCriteriaFalseDate(){
		EventsSearchRequest req = EventsSearchRequest.builder()
				.date("20140613")
				.build();
		EventsSearchRequestFilter eventsFilter = new EventsSearchRequestFilter(req);
		@SuppressWarnings("serial")
		Map<String, Object> event = new HashMap<String, Object>(){{ 
			put("start", "2014-06-12T10:00");
		}};
//		assertFalse(eventsFilter.accept(event));
	}
	
	@Test
	public void testFilterByCriteriaFalseInterest(){
		EventsSearchRequest req = EventsSearchRequest.builder()
				.interest("software process")
				.build();
		EventsSearchRequestFilter eventsFilter = new EventsSearchRequestFilter(req);
		@SuppressWarnings("serial")
		Map<String, Object> event = new HashMap<String, Object>(){{
			put("interest", "software development");
		}};
//		assertFalse(eventsFilter.accept(event));
	}
	
	@Test
	public void testFilterByCriteriaTrueBudget(){
		EventsSearchRequest req = EventsSearchRequest.builder()
				.budget(110)
				.build();
		EventsSearchRequestFilter eventsFilter = new EventsSearchRequestFilter(req);
		@SuppressWarnings("serial")
		Map<String, Object> event = new HashMap<String, Object>(){{ 
			put("budget", "110");
		}};
		assertTrue(eventsFilter.accept(event));
	}	
	
	@Test
	public void testFilterByCriteriaFalseBudget(){
		EventsSearchRequest req = EventsSearchRequest.builder()
				.budget(100)
				.build();
		EventsSearchRequestFilter eventsFilter = new EventsSearchRequestFilter(req);
		@SuppressWarnings("serial")
		Map<String, Object> event = new HashMap<String, Object>(){{ 
			put("budget", "110");
		}};
//		assertFalse(eventsFilter.accept(event));
	}	
	
}