package nu.ahlbom.jtelldus.callback;

import java.util.Calendar;

import nu.ahlbom.jtelldus.SensorValueType;

public interface SensorCallback {
	public void event(String protocol, String model, int id, SensorValueType dataType, double value, Calendar timestamp) throws NumberFormatException;
}
