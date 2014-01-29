package se.egeback.jtelldus;
   public enum ErrorCode {
    	TELLSTICK_SUCCESS(0),
        TELLSTICK_ERROR_NOT_FOUND(-1),
        TELLSTICK_ERROR_PERMISSION_DENIED(-2),
        TELLSTICK_ERROR_DEVICE_NOT_FOUND(-3),
        TELLSTICK_ERROR_METHOD_NOT_SUPPORTED(-4),
        TELLSTICK_ERROR_COMMUNICATION(-5),
        TELLSTICK_ERROR_CONNECTING_SERVICE(-6),
        TELLSTICK_ERROR_UNKNOWN_RESPONSE(-7),
        TELLSTICK_ERROR_SYNTAX(-8),
        TELLSTICK_ERROR_BROKEN_PIPE(-9),
        TELLSTICK_ERROR_COMMUNICATING_SERVICE(-10),
        TELLSTICK_ERROR_UNKNOWN(-99);
    	
    	private int value;
    	
    	public int getValue() {
    		return value;
    	}
    	
    	private ErrorCode(int value) {
    		this.value = value;
    	}
    	
    	public static ErrorCode parse(int value) {
    		for(ErrorCode code:ErrorCode.values()) {
    			if(code.value == value) {
    				return code;
    			}
    		}
			return null;
    	}
    }