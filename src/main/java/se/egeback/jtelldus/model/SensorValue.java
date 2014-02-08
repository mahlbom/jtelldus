package se.egeback.jtelldus.model;

import java.util.Calendar;

public class SensorValue {
	private Calendar timestamp;
	private Object value;
	
	public SensorValue(double value, Calendar timestamp) {
		this.value = value;
		this.timestamp = timestamp;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}

	public Object getValue() {
		return value;
	}
}
