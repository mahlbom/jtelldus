package nu.ahlbom.jtelldus.callback;

import nu.ahlbom.jtelldus.Method;

public interface DeviceCallback {
	public void event(int deviceId, Method method, String data, int callbackId);
}
