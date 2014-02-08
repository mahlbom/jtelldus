package se.egeback.jtelldus.model;

import java.util.Calendar;

import org.apache.log4j.Logger;

import se.egeback.jtelldus.Library;
import se.egeback.jtelldus.SensorValueType;
import se.egeback.jtelldus.callback.SensorCallback;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class Sensor {
	private static Logger logger = Logger.getLogger(Sensor.class);

	private String protocol;
	private String model;
	private int id;
	private int dataTypes;
	private static Library library;
	private SensorValue temperature;
	private SensorValue humidity;
	
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(int dataTypes) {
		this.dataTypes = dataTypes;
	}

	public static void setLibrary(Library library) {
		Sensor.library = library;
	}

	public int startListening(final SensorCallback callback) {
		return startListening(callback, this, Sensor.library);
	}
	
	public static int startListening(final SensorCallback callback, Object object, Library library) {
		//register callback function for sensor events
		
		Library.SensorCallback cb = new Library.SensorCallback() {
			public void callbackfunction(Pointer protocol, Pointer model, int id, int dataType, Pointer value, int timestamp, int callbackId, Pointer context)  throws NumberFormatException {
				long timestampvalue = (long)timestamp * 1000;
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(timestampvalue);
				
				double val = Double.parseDouble(value.getString(0));

				callback.event(protocol.getString(0),  model.getString(0), id, SensorValueType.parse(dataType), val, cal);
			}
		};
		
		if(object!=null) {
			return library.tdRegisterSensorEvent(cb, (Pointer) object);
		} else {
			return library.tdRegisterSensorEvent(cb, (Pointer) null);
		}
	}
	
	public void stopListening(int callbackId) {
		library.tdUnregisterCallback(callbackId);
	}
	
	public static void stopListening(int callbackId, Library library){
		library.tdUnregisterCallback(callbackId);
	}
	
	public SensorValue getTemperature() {
		if(this.temperature==null) {
			temperature = getSensorValue(protocol, model, id, Library.TELLSTICK_TEMPERATURE);
		}
		
		return temperature;
	}
	
	public static SensorValue getSensorValue(String protocol, String model, int id, int dataType) {
		byte[] value = new byte[20]; 
		IntByReference timestamp = new IntByReference();
		
		if(library.tdSensorValue(protocol.getBytes(), model.getBytes(), id, dataType, value, 20, timestamp) == Library.TELLSTICK_SUCCESS) {
			long timestampvalue = (long)timestamp.getValue() * 1000;
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(timestampvalue);
			
			try {
				if(dataType==Library.TELLSTICK_HUMIDITY)
					return new SensorValue(Integer.parseInt(Native.toString(value)), cal);
				else
					return new SensorValue(Double.parseDouble(Native.toString(value)), cal);
			} catch (Exception e) {
				logger.error("Could not parse sensor value" + Native.toString(value));
			}
		}
		return null;
	}
	
	public SensorValue getHumidity() {
		if(this.humidity==null) {
			humidity = getSensorValue(protocol, model, id, Library.TELLSTICK_HUMIDITY);
		}
		return humidity;
	}
}
