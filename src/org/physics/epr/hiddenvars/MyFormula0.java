package org.physics.epr.hiddenvars;

import org.physics.epr.Particle;
import org.physics.epr.hiddenvars.MeasurementFormulaIF;

public class MyFormula0 implements MeasurementFormulaIF {
public int measure(double angle_deg, Particle particle) {
 double rad = Math.toRadians(angle_deg);
 double vars[] = particle.getHiddenVars();
 double sign = vars[2]; 
 double part = Math.cos(rad) + Math.sin(rad); 
 double prob = 0.5d * ( 1.0 + sign / (Math.sqrt(2.0) *part));
 if ((int)angle_deg % 180 == 0) return Math.random() < prob ? 1: 0;
 else return (int) Math.round(prob);
}


	public String getFormula() {
		return null;
	}
}
