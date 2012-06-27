package org.physics.epr;

/** result of one exeriment */
public class DataPoint {

	/** relative angle between filters */
	private double angle_deg;
	
	private int microframe_angle_deg;
	
	/** result of measurement 1 and 2, allow fractional values */
	private double spinA;	
	private double spinB;
	
	public DataPoint(double angle_deg, double spinA, double spinB, double microframe_angle_deg){
		this.angle_deg = angle_deg;
		this.spinA = spinA;
		this.microframe_angle_deg = (int)microframe_angle_deg;
		this.spinB = spinB;
	//	validate();
	}
	public int getMicroFrameAngleDeg() {
		return microframe_angle_deg;
	}
	public boolean same() {
		return spinA == spinB;
	}
	public double getSpinA() {
		return spinA;
	}
	public double getSpinB() {
		return spinB;
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
		return "Datapoint angleAB="+angle_deg+",  left="+spinA+", right="+spinB+", theta:"+this.microframe_angle_deg;
	}
}
