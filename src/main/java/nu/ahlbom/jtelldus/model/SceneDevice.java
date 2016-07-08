package nu.ahlbom.jtelldus.model;

import nu.ahlbom.jtelldus.ErrorCode;
import nu.ahlbom.jtelldus.exception.TellstickException;

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
