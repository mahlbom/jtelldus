package se.egeback.jtelldus.callback;

public interface RawCallback {
	public void event(String data, int controllerId, int callbackId);
}
