package team16.models;

public class Area {
	private String type = "Polygon";
	private double[][][] coordinates = {{{42.31337,-81.13905}, {39.32494,-81.255}, {39.42163,-72.92079}, {42.46941,-73.15992}, {42.31337,-81.13905}}};
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public double[][][] getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(double[][][] coordinates) {
		this.coordinates = coordinates;
	}
}
