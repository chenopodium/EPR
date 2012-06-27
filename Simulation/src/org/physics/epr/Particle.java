package org.physics.epr;

import java.util.Arrays;
import org.physics.epr.hiddenvars.MeasurementFormulaIF;

public class Particle {

    private String name;
    private double theta;
    private double phi;
    private double[] hiddenVars;
    
    private MeasurementFormulaIF formula;
    
    public Particle(String name) {
        this.name = name;        
    }

    /** set by the biparticle during entanglement */
    public void setMeasurementFormula(MeasurementFormulaIF formula) {
        this.formula = formula;
    }
    public boolean isA() {
        return name.equalsIgnoreCase("A");
    }
    public boolean isB() {
        return !(isA());
    }
    @Override
    public String toString() {
        return "Particle " + name+", theta: "+theta+", hiddenvars: "+Arrays.toString(getHiddenVars());
    }

    /** returns the probability of measureing +1*/
    public int measure(double filter_angle_rad) {        
       return formula.measure(filter_angle_rad, this);

    }
    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    /**
     * @return the hiddenVars
     */
    public double[] getHiddenVars() {
        return hiddenVars;
    }

    /**
     * @param hiddenVars the hiddenVars to set
     */
    public void setHiddenVars(double[] hiddenVars) {
        this.hiddenVars = hiddenVars;
    }

    /**
     * @return the phi
     */
    public double getPhi() {
        return phi;
    }

    /**
     * @param phi the phi to set
     */
    public void setPhi(double phi) {
        this.phi = phi;
    }

    
}
