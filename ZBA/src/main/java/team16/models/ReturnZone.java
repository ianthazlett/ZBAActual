package team16.models;

import java.util.List;

public class ReturnZone {

	private Zone zone;
	private List<Alert> alerts;
	private int zoneAmount;
	private boolean admin;
	
	public Zone getZone() {
		return zone;
	}
	
	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	public List<Alert> getAlerts() {
		return alerts;
	}
	
	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}
	
	public int getZoneAmount() {
		return zoneAmount;
	}
	
	public void setZoneAmount(int zoneAmount) {
		this.zoneAmount = zoneAmount;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
