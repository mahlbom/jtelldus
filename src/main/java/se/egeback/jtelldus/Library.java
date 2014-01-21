package se.egeback.jtelldus;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public interface Library extends com.sun.jna.Library {
	
	void tdInit();
	void tdClose();
	
	//Sensors
	int tdSensorValue(byte[] protocol, byte[] model, int id, int dataType, byte[] value, int valueLength, IntByReference timestamp);
	int tdSensor(byte[] protocol, int protocolLength, byte[] model, int modelLength, IntByReference id,IntByReference dataTypes);
	
	//Callbacks
	// TELLSTICK_API int WINAPI tdRegisterDeviceEvent( TDDeviceEvent eventFunction, void *context );
    public int tdRegisterDeviceEvent( DeviceCallback eventFunction, Pointer context );
    // TELLSTICK_API int WINAPI tdRegisterDeviceChangeEvent( TDDeviceChangeEvent eventFunction, void *context);
    public int tdRegisterDeviceChangeEvent( DeviceChangeCallback eventFunction, Pointer context);
    // TELLSTICK_API int WINAPI tdRegisterRawDeviceEvent( TDRawDeviceEvent eventFunction, void *context );
    public int tdRegisterRawDeviceEvent( TDRawDeviceEvent eventFunction, Pointer context );

    
	int tdRegisterSensorEvent(SensorCallback function, Pointer context);
	int tdUnregisterCallback(int callbackID);
	
	public interface SensorCallback extends Callback {
		public void callbackfunction(Pointer protocol, Pointer model, int id, int dataType, Pointer value, int timestamp, int callbackId, Pointer context);
	}
	
	// typedef void (WINAPI *TDDeviceEvent)(int deviceId, int method, const char *data, int callbackId, void *context);
    public interface DeviceCallback extends Callback {
        void invoke(int deviceId, int method, Pointer data, int callbackId, Pointer context);
    }
    
    // typedef void (WINAPI *TDDeviceChangeEvent)(int deviceId, int changeEvent, int changeType, int callbackId, void *context); 
    interface DeviceChangeCallback extends Callback {
    	void invoke(int deviceId, int changeEvent, int changeType, int callbackId, Pointer context);        
    }
    
    // typedef void (WINAPI *TDRawDeviceEvent)(const char *data, int controllerId, int callbackId, void *context);
    interface TDRawDeviceEvent extends Callback {
    	void invoke(String data, int controllerId, int callbackId, Pointer context);        
    }
}