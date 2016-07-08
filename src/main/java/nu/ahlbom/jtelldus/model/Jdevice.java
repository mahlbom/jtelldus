package nu.ahlbom.jtelldus.model;

import nu.ahlbom.jtelldus.ErrorCode;
import nu.ahlbom.jtelldus.Library;
import nu.ahlbom.jtelldus.exception.TellstickException;

import org.apache.log4j.Logger;

public class Jdevice extends TelldusDevice {
    private static Logger logger = Logger.getLogger(TelldusDevice.class);

	public Jdevice(int deviceId) {
		this.deviceId = deviceId;
		this.init(deviceId);
	}
	
	public static void on(int deviceId) throws TellstickException {
    	ErrorCode status = ErrorCode.parse(library.tdTurnOn(deviceId));
    	logger.debug("Turning on " + deviceId + ". Status " + status);
    	if (status != ErrorCode.TELLSTICK_SUCCESS) throw new TellstickException(null, status);
    }
	
    public void on() throws TellstickException {
    	on(getDeviceId());
    }

	public static void off(int deviceId) throws TellstickException {
    	ErrorCode status = ErrorCode.parse(library.tdTurnOff(deviceId));
    	logger.debug("Turning off " + deviceId + ". Status " + status);
    	if (status != ErrorCode.TELLSTICK_SUCCESS) throw new TellstickException(null, status);
    }
    
    public void off() throws TellstickException {
    	off(deviceId);
    }
    
    /**
     * Returns true if latest command was turn on signal.
     * This is a virtual 2-way communication, it does not really know if it's on. But it knows the latest command sent, so we can determine it this way.
     * @return true if device is on.
     */
    public boolean isOn(){
            if ((Library.TELLSTICK_TURNON & status) > 0) return true;
            else return false;
    }
}
