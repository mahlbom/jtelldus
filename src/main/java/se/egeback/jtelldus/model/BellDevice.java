package se.egeback.jtelldus.model;

import se.egeback.jtelldus.ErrorCode;
import se.egeback.jtelldus.exception.TellstickException;

public class BellDevice extends TelldusDevice {

	public BellDevice(int deviceId) {
		this.deviceId = deviceId;
		this.init(deviceId);
	}
	
	 public void bell() throws TellstickException {
		 ErrorCode status = ErrorCode.parse(library.tdBell(getId()));
         if (status != ErrorCode.TELLSTICK_SUCCESS) {
        	 throw new TellstickException(this, status);
         }
	 }
}
