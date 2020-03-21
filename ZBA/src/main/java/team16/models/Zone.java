package team16.models;

public class Zone {
	
	private int zoneID;
	private String location;
	
	public Zone(int zoneID, String location)
	{
		this.zoneID = zoneID;
		this.location = location;
	}
	
	public Zone()
	{
		
	}
	
	public int getZoneID() {
		return zoneID;
	}
	public void setZoneID(int zoneID) {
		this.zoneID = zoneID;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
