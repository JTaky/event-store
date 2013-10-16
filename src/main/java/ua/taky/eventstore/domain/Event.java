package ua.taky.eventstore.domain;

import org.joda.time.DateTime;

public class Event {
	
	public final String title;
	public final String city;
	public final DateTime start;
	public final DateTime end;
	public final int price;
	
	public Event(String title, String city, DateTime start, DateTime end, int price) {
		this.title = title;
		this.city = city;
		this.start = start;
		this.end = end;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Event [title=" + title + ", city=" + city + ", start=" + start
				+ ", end=" + end + ", price=" + price + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + price;
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (price != other.price)
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	public boolean isIntersect(Event anotherEvent){
		return this.end.isAfter(anotherEvent.start) 
				&& this.start.isBefore(anotherEvent.start) ||
				anotherEvent.end.isAfter(this.start) 
				&& anotherEvent.start.isBefore(this.start);
	}

	
}