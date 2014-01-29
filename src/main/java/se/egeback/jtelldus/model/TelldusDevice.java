package se.egeback.jtelldus.model;

import com.sun.jna.Pointer;

import se.egeback.jtelldus.Library;
import se.egeback.jtelldus.Method;
import se.egeback.jtelldus.callback.DeviceCallback;

public abstract class TelldusDevice {
	protected static Library library;
	
    protected int deviceId;
    protected String name;
    protected String model;
    protected String protocol;
    protected int status;
    
    protected int supportedMethods = -1;
    		
    public void setSupportedMethods(int supportedMethods){
        this.supportedMethods = supportedMethods;
    }

	public int getSupportedMethods() {
		return supportedMethods; 
	}
	
	public int getId() {
		return getDeviceId();
	}
    
    public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public static Library getLibrary() {
		return library;
	}

	private int deviceType;

	public static void setLibrary(Library library) {
		Device.library = library;
	}
	
	protected void init(int deviceId) {
        this.deviceId = deviceId;
        
        // Get name
        this.name = library.tdGetName(deviceId);
        library.tdReleaseString(name);

        // Get model
        this.model = library.tdGetModel(deviceId);
        library.tdReleaseString(model);
        
        // Get protocol
        this.protocol = library.tdGetProtocol(deviceId);
        library.tdReleaseString(protocol);
        
        // Get last status ( EMULATED 2 way communication ) Works with TS DUO
        this.status = library.tdLastSentCommand(deviceId, getSupportedMethods());
        
        // Get the device type.
        this.deviceType = library.tdGetDeviceType(deviceId);
        
	}
	
	static public int allMethods() {
		return Library.TELLSTICK_BELL   | Library.TELLSTICK_TURNOFF | 
			   Library.TELLSTICK_TURNON | Library.TELLSTICK_DIM     |
			   Library.TELLSTICK_LEARN  | Library.TELLSTICK_EXECUTE | 
			   Library.TELLSTICK_STOP;
	}
	
	public int startListening(final DeviceCallback callback) {
		return startListening(callback, this, Device.library);
	}
	
	public static int startListening(final DeviceCallback callback, Object object, Library library) {
		//register callback function for sensor events
		
		Library.DeviceCallback cb = new Library.DeviceCallback() {
			@Override
			public void invoke(int deviceId, int m, Pointer data,
					int callbackId, Pointer context) {
				Method method = Method.parse(m);
				callback.event(deviceId, method, data.getString(0), callbackId);
			}
		};
		
		if(object!=null) {
			return library.tdRegisterDeviceEvent(cb, (Pointer) object);
		} else {
			return library.tdRegisterDeviceEvent(cb, (Pointer) null);
		}
	}
	
	public void stopListening(int callbackId) {
		library.tdUnregisterCallback(callbackId);
	}
	
	public static void stopListening(int callbackId, Library library){
		library.tdUnregisterCallback(callbackId);
	}
}
