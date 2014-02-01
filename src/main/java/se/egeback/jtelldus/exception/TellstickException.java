package se.egeback.jtelldus.exception;

import se.egeback.jtelldus.ErrorCode;
import se.egeback.jtelldus.model.TelldusDevice;

public class TellstickException extends Exception {

	private static final long serialVersionUID = 8986492607924729970L;
	private ErrorCode errorCode;
	private TelldusDevice device;
	
	public TellstickException(ErrorCode errorCode) {
		this(null, errorCode);
	}
	
	public TellstickException(TelldusDevice device, ErrorCode errorCode) {
		super(errorCode.toString());
		this.device = device;
		this.errorCode  = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public TelldusDevice getDevice() {
		return device;
	}
}
