package se.egeback.jtelldus.model;

import se.egeback.jtelldus.ErrorCode;
import se.egeback.jtelldus.JTelldus;
import se.egeback.jtelldus.Library;
import se.egeback.jtelldus.exception.TellstickException;

public class DimmableDevice extends Device {
	
	public DimmableDevice(int deviceId) {
		super(deviceId);
	}
	
    public void dim(int level) throws TellstickException {
        if (level < 0 || level > 255)  {
        	throw new IllegalArgumentException("Dim levels must be between 0 and 255.");
        }
        
        ErrorCode status = ErrorCode.parse(library.tdDim(getId(), level));
       
        if (status != ErrorCode.TELLSTICK_SUCCESS) {
        	throw new TellstickException(this, status);
        }
    }
    
    public int getDimValue() {
    	String value = JTelldus.getString(library.tdLastSentValue(getId()), library);
    	try {
    		return Integer.parseInt(value);
    	} catch (Exception e) {
    		return -1;
    	}
    }

	public boolean isOn(){
        if (super.isOn() || (Library.TELLSTICK_DIM & this.getStatus()) > 0) { 
        	return true;
        } else {
        	return false;
        }
	}
}
