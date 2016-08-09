package nu.ahlbom.jtelldus.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nu.ahlbom.jtelldus.JTelldus;
import nu.ahlbom.jtelldus.Library;
import nu.ahlbom.jtelldus.Method;
import nu.ahlbom.jtelldus.callback.DeviceCallback;

import com.sun.jna.Pointer;

public abstract class TelldusDevice {
    private static Logger logger = LoggerFactory.getLogger(TelldusDevice.class);
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

	public Method[] getSupportedMethods() {
		return Method.parseMultiple(supportedMethods); 
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

	public Method getStatus() {
		return Method.parse(status);
	}

	public void setStatus(Method status) {
		this.status = status.getValue();
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
		Jdevice.library = library;
	}
	
	protected void init(int deviceId) {
		logger.debug("Init device " + deviceId);
        this.deviceId = deviceId;
        
        // Get name
        logger.trace("Get name for " + deviceId);
        this.name = JTelldus.getString(library.tdGetName(deviceId), library);

        // Get model
        logger.trace("Get model for " + deviceId);
        this.model = JTelldus.getString(library.tdGetModel(deviceId), library);
        
        // Get protocol
        logger.trace("Get protocol for " + deviceId);
        this.protocol = JTelldus.getString(library.tdGetProtocol(deviceId), library);
        
        // Get last status ( EMULATED 2 way communication ) Works with TS DUO
        logger.trace("Get last sent command for " + deviceId);
        this.status = library.tdLastSentCommand(deviceId, supportedMethods);
        
        // Get the device type.
        logger.trace("Get device type " + deviceId);
        this.deviceType = library.tdGetDeviceType(deviceId);
        
	}
	
	static public int allMethods() {
		return Library.TELLSTICK_BELL   | Library.TELLSTICK_TURNOFF | 
			   Library.TELLSTICK_TURNON | Library.TELLSTICK_DIM     |
			   Library.TELLSTICK_LEARN  | Library.TELLSTICK_EXECUTE | 
			   Library.TELLSTICK_STOP;
	}
	
	public int startListening(final DeviceCallback callback) {
		return startListening(callback, this, Jdevice.library);
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
