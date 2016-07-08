package nu.ahlbom.jtelldus.callback;

public interface RawCallback {
	public void event(String data, int controllerId, int callbackId);
}
