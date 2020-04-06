package team16.models;

public class Alert {
	
	private int id;
	private String name;
	private double latitute;
	private double longitude;
	private String severity;
	private String bearing;
	private int speed;
	private String action;
	
	public Alert()
	{
		
	}
	
	public Alert(int id, String name, double lat, double lon)
	{
		this.id = id;
		this.name = name;
		this.latitute = lat;
		this.longitude = lon;
	}
	
	public Alert(int id, String name, double lat, double lon, String severity, String action)
	{
		this.id = id;
		this.name = name;
		this.latitute = lat;
		this.longitude = lon;
		this.severity = severity;
		this.action = action;
	}
	
	public Alert(int id, String name, double lat, double lon, String severity, String bearing, int speed)
	{
		this.id = id;
		this.name = name;
		this.latitute = lat;
		this.longitude = lon;
		this.severity = severity;
		this.bearing = bearing;
		this.speed = speed;
	}
	
	public Alert(int id, String name, double lat, double lon, String severity, String bearing, int speed, String action)
	{
		this.id = id;
		this.name = name;
		this.latitute = lat;
		this.longitude = lon;
		this.severity = severity;
		this.bearing = bearing;
		this.speed = speed;
		this.action = action;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getLatitute() {
		return latitute;
	}
	public void setLatitute(double latitute) {
		this.latitute = latitute;
	}
	
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public String getSeverity() {
		return severity;
	}


	public void setSeverity(String severity) {
		this.severity = severity;
	}


	public String getBearing() {
		return bearing;
	}


	public void setBearing(String bearing) {
		this.bearing = bearing;
	}


	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}
	

}
