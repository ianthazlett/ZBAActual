package team16.models;

public class TimePeriod {
	private Start start = new Start();
	private End end = new End();
	
	public Start getStart() {
		return start;
	}
	public void setStart(Start start) {
		this.start = start;
	}
	
	public End getEnd() {
		return end;
	}
	public void setEnd(End end) {
		this.end = end;
	}
}
