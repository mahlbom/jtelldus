package se.egeback.jtelldus.model;

import se.egeback.jtelldus.ErrorCode;
import se.egeback.jtelldus.Library;
import se.egeback.jtelldus.exception.TellstickException;

public class Device extends TelldusDevice {
	
	public Device(int deviceId) {
		this.deviceId = deviceId;
		this.init(deviceId);
	}
	
    public void on() throws TellstickException {
    	ErrorCode status = ErrorCode.parse(library.tdTurnOn(getId()));
    	if (status != ErrorCode.TELLSTICK_SUCCESS) throw new TellstickException(this, status);
    }

    public void off() throws TellstickException {
    	ErrorCode status = ErrorCode.parse(library.tdTurnOff(getId()));
    	if (status != ErrorCode.TELLSTICK_SUCCESS) throw new TellstickException(this, status);
    }
    
    /**
     * Returns true if latest command was turn on signal.
     * This is a virtual 2-way communication, it does not really know if it's on. But it knows the latest command sent, so we can determine it this way.
     * @return true if device is on.
     */
    public boolean isOn(){
            if ((Library.TELLSTICK_TURNON & this.getStatus()) > 0) return true;
            else return false;
    }
}
