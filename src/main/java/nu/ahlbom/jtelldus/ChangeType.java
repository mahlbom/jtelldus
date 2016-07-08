package nu.ahlbom.jtelldus;
public enum ChangeType {
    	TELLSTICK_CHANGE_NAME(1),
    	TELLSTICK_CHANGE_PROTOCOL(2),
    	TELLSTICK_CHANGE_MODEL(3),
    	TELLSTICK_CHANGE_METHOD(4);
    	
    	private int value;
    	
    	public int getValue() {
    		return value;
    	}
    	
    	private ChangeType(int value) {
    		this.value = value;
    	}
    	
    	public static ChangeType parse(int value) {
    		for(ChangeType type:ChangeType.values()) {
    			if(type.value == value) {
    				return type;
    			}
    		}
			return null;
    	}
    }