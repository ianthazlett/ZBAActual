package team16.models;

public class Zone {
	
	private int zone_id;
	private String location;
	private double tornado;
	private double hurricane;
	private double flash_Flooding;
	private double winter_Storm;
	private double thunderstorm;
	
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

	public double getTornado() {
		return tornado;
	}

	public void setTornado(double tornado) {
		this.tornado = tornado;
	}

	public double getHurricane() {
		return hurricane;
	}

	public void setHurricane(double hurricane) {
		this.hurricane = hurricane;
	}

	public double getFlash_Flooding() {
		return flash_Flooding;
	}

	public void setFlash_Flooding(double flash_Flooding) {
		this.flash_Flooding = flash_Flooding;
	}

	public double getWinter_Storm() {
		return winter_Storm;
	}

	public void setWinter_Storm(double winter_Storm) {
		this.winter_Storm = winter_Storm;
	}

	public double getThunderstorm() {
		return thunderstorm;
	}

	public void setThunderstorm(double thunderstorm) {
		this.thunderstorm = thunderstorm;
	}
}
