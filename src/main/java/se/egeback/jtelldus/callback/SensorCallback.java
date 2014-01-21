package se.egeback.jtelldus.callback;

import java.util.Calendar;

import se.egeback.jtelldus.SensorValueType;

public interface SensorCallback {
	public void event(String protocol, String model, int id, SensorValueType dataType, double value, Calendar timestamp) throws NumberFormatException;
}
