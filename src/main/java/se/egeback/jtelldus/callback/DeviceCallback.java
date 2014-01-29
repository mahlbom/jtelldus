package se.egeback.jtelldus.callback;

import se.egeback.jtelldus.Method;

public interface DeviceCallback {
	public void event(int deviceId, Method method, String data, int callbackId);
}
