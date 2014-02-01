package se.egeback.jtelldus;

import java.util.ArrayList;
import java.util.List;

import se.egeback.jtelldus.callback.SensorCallback;
import se.egeback.jtelldus.model.BellDevice;
import se.egeback.jtelldus.model.Device;
import se.egeback.jtelldus.model.DimmableDevice;
import se.egeback.jtelldus.model.GroupDevice;
import se.egeback.jtelldus.model.SceneDevice;
import se.egeback.jtelldus.model.ScreenDevice;
import se.egeback.jtelldus.model.Sensor;
import se.egeback.jtelldus.model.TelldusDevice;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class JTelldus {
	private Library instance;
	
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
	
	public void close() {
		instance.tdClose();
	}
	
	public List<Sensor> getSensors() {
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
	
	public TelldusDevice getDevice(int deviceId) {
		return getDevice(deviceId, TelldusDevice.allMethods());
	}
	
	public TelldusDevice getDevice(int deviceId, int supportedMethods) {
        int methods = instance.tdMethods(deviceId, supportedMethods);

        // Is this device a group or scene ? In this case, we must return either GroupDevice or SceneDevice.
        int type = instance.tdGetDeviceType(deviceId);
        if (type == Library.TELLSTICK_TYPE_GROUP){
        	return new GroupDevice(deviceId);
        }
        
        // Now single action based.
        if(type == Library.TELLSTICK_TYPE_SCENE){
        	return new SceneDevice(deviceId);
        } else if ((methods & Library.TELLSTICK_BELL) > 0) {
        	return new BellDevice(deviceId);
        } else if((methods & Library.TELLSTICK_DIM) > 0){
        	return new DimmableDevice(deviceId);
        } else if((methods & Library.TELLSTICK_UP) > 0 && (methods & Library.TELLSTICK_DOWN) > 0 && (methods & Library.TELLSTICK_STOP) > 0){
        	return new ScreenDevice(deviceId);
        } else if ((methods & Library.TELLSTICK_TURNON) > 0 && (methods & Library.TELLSTICK_TURNOFF) > 0){
        	return new Device(deviceId);
        } else if((methods & Library.TELLSTICK_EXECUTE) > 0){
        	return new SceneDevice(deviceId);
        }
        return null;
	}
	
	public ArrayList<TelldusDevice> getDevices() {
        int nbrDevices = instance.tdGetNumberOfDevices();
        ArrayList<TelldusDevice> devices = new ArrayList<TelldusDevice>();
        for (int i = 0; i < nbrDevices; i++) {
	    	TelldusDevice dev = getDevice(instance.tdGetDeviceId(i));
	    	if(dev!=null)
	    		devices.add(dev);
        }
        return devices;
	}
	
	private Library createLibraryInstance() {
		Library instance = (Library) Native.loadLibrary("libtelldus-core.so.2", Library.class);
		instance.tdInit();
		TelldusDevice.setLibrary(instance);
		return instance;
	}
	
	public int registerSensorEvent(SensorCallback callback) {
		return Sensor.startListening(callback, null, instance);
	}
	
	public void removeSensorEvent(int callbackId) {
		Sensor.stopListening(callbackId, instance);
	}
	
	public static String getString(Pointer pointer, Library instance) {
		String string = pointer.getString(0);
		instance.tdReleaseString(pointer);
		return string;
	}
}
