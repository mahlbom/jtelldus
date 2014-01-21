package se.egeback.jtelldus;

import java.util.ArrayList;
import java.util.List;

import se.egeback.jtelldus.callback.SensorCallback;
import se.egeback.jtelldus.model.Sensor;

import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

public class JTelldus {
	private static Library instance;
	
	public JTelldus() {
		init();
	}
	
	protected JTelldus(Library library) {
		init(library);
	}
	
	public void init() {
		instance = createLibraryInstance();
	}
	
	protected void init(Library library) {
		instance = createLibraryInstance();
	}
	
	public static void close() {
		instance.tdClose();
	}
	
	public static List<Sensor> getSensors() {
		List<Sensor> sensors = new ArrayList<Sensor>(); 
		
		IntByReference id = new IntByReference();
		IntByReference dataTypes = new IntByReference();
		
		byte protocol[] = new byte[20];
		byte model[] = new byte[20];
		
		//check every sensor
		while(instance.tdSensor(protocol, 20, model, 20, id, dataTypes) == 0){
			Sensor sensor = new Sensor();
			sensor.setId(id.getValue());
			sensor.setDataTypes(dataTypes.getValue());
			sensor.setModel(new String(model));
			sensor.setProtocol(new String(protocol));
			sensors.add(sensor);
		}
		return sensors;
	}
	
	private Library createLibraryInstance() {
		Library instance = (Library) Native.loadLibrary("libtelldus-core.so.2", Library.class);
		instance.tdInit();
		return instance;
	}
	
	public int registerSensorEvent(SensorCallback callback) {
		return Sensor.startListening(callback, null, instance);
	}
	
	public void removeSensorEvent(int callbackId) {
		Sensor.stopListening(callbackId, instance);
	}
}
