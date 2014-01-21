package se.egeback.jtelldus;
public enum SensorValueType {
	TELLSTICK_TEMPERATURE(1),
	TELLSTICK_HUMIDITY(2),
	TELLSTICK_RAINRATE(4),
	TELLSTICK_RAINTOTAL(8),
	TELLSTICK_WINDDIRECTION(16),
	TELLSTICK_WINDAVERAGE(32),
	TELLSTICK_WINDGUST(64);
	
	private int value;
	private SensorValueType(int value) {
		this.value = value;
	}
	
	public static SensorValueType parse(int value) {
		for(SensorValueType type:SensorValueType.values()) {
			if(value == type.value)
				return type;
		}
		return null;
	}
	
	public int getValue() {
		return value;
	}
	
	public String toString() {
		return value + "";
	}
}