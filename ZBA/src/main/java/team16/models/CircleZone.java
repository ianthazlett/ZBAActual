package team16.models;

public class CircleZone extends Zone {
	
	public CircleZone(int zoneID, String location) {
		super(zoneID, location);
		// TODO Auto-generated constructor stub
	}

	private double center;
	private int diameter;
	
	public int getDiameter() {
		return diameter;
	}
	
	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

	public double getCenter() {
		return center;
	}

	public void setCenter(double center) {
		this.center = center;
	}

}
