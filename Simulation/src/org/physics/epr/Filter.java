package org.physics.epr;

public class Filter {

	double angle_deg;
	String name;
	
	public Filter(String name) {
		this.name = name;
	}
	
	public double selectFilterSetting(double[] values) {
		// select filter settings
		if (Setup.usePredifinedAnglesAB) {
			double deg = Utils.randomElement(values);
			setAngleInDegrees(deg);			
		}
		else {
			double deg = Utils.randomAngleInDeg();
			setAngleInDegrees(deg);	
		}
		return getAngleInDegrees();
	}
	
	public double getAngleInDegrees() {
		return angle_deg;
	}

	public void setAngleInDegrees(double angle_in_degrees) {
		this.angle_deg = angle_in_degrees;
	}
	
	public String toString() {
		return "Filter "+name+" has angle: "+angle_deg;
	}

	/* Compute if the result is + or - 1 based on the particle and the angle of the filter */
	public int  measure(Particle particle) {
		return particle.measure(Math.toRadians(angle_deg));
		
	}
	
}
