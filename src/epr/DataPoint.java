package epr;

/** result of one exeriment */
public class DataPoint {

	/** relative angle between filters */
	private double angle_deg;
	
	private int microframe_angle_deg;
	
	/** result of measurement 1 and 2, allow fractional values */
	private double leftspin;	
	private double rightspin;
	
	public DataPoint(double angle_deg, double leftresult, double rightresult, double microframe_angle_deg){
		this.angle_deg = angle_deg;
		this.leftspin = leftresult;
		this.microframe_angle_deg = (int)microframe_angle_deg;
		this.rightspin = rightresult;
	//	validate();
	}
	public int getMicroFrameAngleDeg() {
		return microframe_angle_deg;
	}
	public boolean same() {
		return leftspin == rightspin;
	}
	public double getLeftspin() {
		return leftspin;
	}
	public double getRightspin() {
		return rightspin;
	}
	public double getAngleInDegrees() {
		return angle_deg;
	}
	
	private void err(String s) {
		System.err.println("Result not as expected! "+toString());
		//System.exit(-1);
	}
	private void p(String s) {
		System.out.println("DataPoint: "+s);		
	}
	public String toString() {
		return "Datapoint angleAB="+angle_deg+",  left="+leftspin+", right="+rightspin+", theta:"+this.microframe_angle_deg;
	}
}
