package nu.ahlbom.jtelldus;
   public enum ErrorCode {
    	TELLSTICK_SUCCESS(0, "Success"),
        TELLSTICK_ERROR_NOT_FOUND(-1, "Not found"),
        TELLSTICK_ERROR_PERMISSION_DENIED(-2, "Permission denied"),
        TELLSTICK_ERROR_DEVICE_NOT_FOUND(-3, "Not found"),
        TELLSTICK_ERROR_METHOD_NOT_SUPPORTED(-4, "Method not supported"),
        TELLSTICK_ERROR_COMMUNICATION(-5, "Communication error"),
        TELLSTICK_ERROR_CONNECTING_SERVICE(-6, "Error connecting to service"),
        TELLSTICK_ERROR_UNKNOWN_RESPONSE(-7, "Unknown response"),
        TELLSTICK_ERROR_SYNTAX(-8, "Syntax Error"),
        TELLSTICK_ERROR_BROKEN_PIPE(-9, "Broken pipe"),
        TELLSTICK_ERROR_COMMUNICATING_SERVICE(-10, "Error communicating to service"),
        TELLSTICK_ERROR_UNKNOWN(-99, "Unknown error");
    	
    	private int value;
		private String description;
    	
    	public int getValue() {
    		return value;
    	}
    	
    	private ErrorCode(int value, String description) {
    		this.value = value;
    		this.setDescription(description);
    	}
    	
    	public static ErrorCode parse(int value) {
    		for(ErrorCode code:ErrorCode.values()) {
    			if(code.value == value) {
    				return code;
    			}
    		}
			return null;
    	}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
		public String toString() {
			return description;
		}
    }