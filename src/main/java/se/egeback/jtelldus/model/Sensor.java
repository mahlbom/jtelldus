package se.egeback.jtelldus.model;

import java.util.Calendar;

import se.egeback.jtelldus.Library;
import se.egeback.jtelldus.SensorValueType;
import se.egeback.jtelldus.callback.SensorCallback;

import com.sun.jna.Pointer;

public class Sensor {
	private String protocol;
	private String model;
	private int id;
	private int dataTypes;
	private Library library;
	
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

	public Sensor setLibrary(Library library) {
		this.library = library;
		return this;
	}

	public int startListening(final SensorCallback callback) {
		return startListening(callback, this, this.library);
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
}
