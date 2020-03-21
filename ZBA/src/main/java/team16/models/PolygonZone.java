package team16.models;

public class PolygonZone extends Zone {

	public PolygonZone(int zoneID, String location) {
		super(zoneID, location);
		// TODO Auto-generated constructor stub
	}

	private double[][] points;

	public double[][] getPoints() {
		return points;
	}

	public void setPoints(double[][] points) {
		this.points = points;
	}
	
}
