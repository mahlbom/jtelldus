package nu.ahlbom.jtelldus.model;

import nu.ahlbom.jtelldus.ErrorCode;
import nu.ahlbom.jtelldus.exception.TellstickException;

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
