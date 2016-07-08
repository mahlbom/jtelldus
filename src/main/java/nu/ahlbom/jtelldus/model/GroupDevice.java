package nu.ahlbom.jtelldus.model;

public class GroupDevice extends TelldusDevice {
	
	public GroupDevice(int deviceId) {
		this.setDeviceId(deviceId);
		this.init(deviceId);
	}
}
