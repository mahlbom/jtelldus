package se.egeback.jtelldus.model;

import se.egeback.jtelldus.ErrorCode;
import se.egeback.jtelldus.exception.TellstickException;

public class SceneDevice extends TelldusDevice {
	public SceneDevice(int deviceId) {
		this.deviceId = deviceId;
		this.init(deviceId);
	}
	
	public void execute() throws TellstickException{
		ErrorCode status = ErrorCode.parse(library.tdExecute(getId()));
        if (status != ErrorCode.TELLSTICK_SUCCESS){
        	throw new TellstickException(this, status);
        }
	}

}
