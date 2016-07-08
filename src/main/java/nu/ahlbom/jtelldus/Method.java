package nu.ahlbom.jtelldus;

import java.util.ArrayList;
import java.util.List;
   public enum Method {
	   TELLSTICK_TURNON(1, "Turn on"),
	   TELLSTICK_TURNOFF(2, "Turn off"),
	   TELLSTICK_BELL(4, "Bell"),
	   TELLSTICK_TOGGLE(8, "Toggle"),
	   TELLSTICK_DIM(16, "Dim"),
	   TELLSTICK_LEARN(32, "Learn"),
	   TELLSTICK_EXECUTE(64, "Execute"),
	   TELLSTICK_UP(128, "Up"),
	   TELLSTICK_DOWN(256, "Down"),
	   TELLSTICK_STOP(512, "Stop");
	   
   	private int value;
   	private String description;
	
   	public int getValue() {
   		return value;
   	}
   	
   	public String toString() {
   		return description;
   	}
   	
   	private Method(int value, String description) {
   		this.value = value;
   		this.description = description;
   	}
   	
   	public static Method parse(int value) {
   		for(Method method:Method.values()) {
   			if(method.value == value) {
   				return method;
   			}
   		}
		return null;
   	}
   	
   	public static Method[] parseMultiple(int value) {
   		List<Method> methods = new ArrayList<Method>();
   		
   		for(Method method:Method.values()) {
   			if((value & method.value)>0) {
   				methods.add(method);
   			}
   		}
   		return methods.toArray(new Method[0]);
   	}
}