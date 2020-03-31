package team16.models;

public class Zone {
	
	private int zone_id;
	private String location;
	
	public Zone(int zone_id, String location)
	{
		this.zone_id = zone_id;
		this.location = location;
	}
	
	public Zone()
	{
		
	}
	
	public int getZone_ID() {
		return zone_id;
	}
	public void setZone_ID(int zone_ID) {
		this.zone_id = zone_ID;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
