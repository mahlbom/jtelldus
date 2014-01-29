package se.egeback.jtelldus.model;

import se.egeback.jtelldus.ErrorCode;
import se.egeback.jtelldus.exception.TellstickException;

public class ScreenDevice extends TelldusDevice {

	public ScreenDevice(int deviceId) {
		this.deviceId = deviceId;
		this.init(deviceId);
	}
	
    public void up() throws TellstickException{
    	ErrorCode status = ErrorCode.parse(library.tdUp(getId()));
        if (status != ErrorCode.TELLSTICK_SUCCESS) {
        	throw new TellstickException(this, status);                
        }
    }

    void down() throws TellstickException{
    	ErrorCode status = ErrorCode.parse(library.tdDown(getId()));
	    if (status != ErrorCode.TELLSTICK_SUCCESS) {
	    	throw new TellstickException(this, status);                                
	    }
	}
	
	public void stop() throws TellstickException{
		ErrorCode status = ErrorCode.parse(library.tdStop(getId()));
	    if (status != ErrorCode.TELLSTICK_SUCCESS) {
	    	throw new TellstickException(this, status);                
	    }
	}
}
