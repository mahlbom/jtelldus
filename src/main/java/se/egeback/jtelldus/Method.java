package se.egeback.jtelldus;
   public enum Method {
	   TELLSTICK_TURNON(1),
	   TELLSTICK_TURNOFF(2),
	   TELLSTICK_BELL(4),
	   TELLSTICK_TOGGLE(8),
	   TELLSTICK_DIM(16),
	   TELLSTICK_LEARN(32),
	   TELLSTICK_EXECUTE(64),
	   TELLSTICK_UP(128),
	   TELLSTICK_DOWN(256),
	   TELLSTICK_STOP(512);
	   
   	private int value;
	
   	public int getValue() {
   		return value;
   	}
   	
   	private Method(int value) {
   		this.value = value;
   	}
   	
   	public static Method parse(int value) {
   		for(Method method:Method.values()) {
   			if(method.value == value) {
   				return method;
   			}
   		}
			return null;
   	}
}