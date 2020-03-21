package team16.models;

public class Trigger {
	
	private TimePeriod time_period = new TimePeriod();
	private Conditions[] conditions = {new Conditions()};
	private Area[] area = {new Area()};
	
	public TimePeriod getTime_period() {
		return time_period;
	}
	public void setTime_period(TimePeriod time_period) {
		this.time_period = time_period;
	}
	
	public Conditions[] getConditions() {
		return conditions;
	}
	public void setConditions(Conditions[] conditions) {
		this.conditions = conditions;
	}
	public Area[] getArea() {
		return area;
	}
	public void setArea(Area[] area) {
		this.area = area;
	}
	

}
