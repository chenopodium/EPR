package org.physics.epr.hiddenvars;

import org.physics.epr.Particle;

/**
 *
 * @author Chantal Roth
 */
public class CloseFormula implements MeasurementFormulaIF {

    /** returns the probability of measuring +1*/
    @Override
    public int measure(double filter_angle, Particle particle) {
        double theta = particle.getTheta();
        int spin = (int) Math.signum(Math.sin(theta+ filter_angle));                
        double pdetect = 2.3*Math.abs(Math.sin(theta + filter_angle));
        
        if (Math.random()<=pdetect) return spin;
        else return Integer.MIN_VALUE;
    }

    @Override
    public String getFormula() {
        String s = "    double theta = particle.getTheta();\n"+
        "   int spin = (int) Math.signum(Math.sin(theta+ filter_angle));\n"+
        "   double pdetect = 2.3*Math.abs(Math.sin(theta + filter_angle));\n"+        
        "   if (Math.random()<=pdetect) return spin;\n"+
        "   else return Integer.MIN_VALUE;\n"+
         "}\n";
        return s;
    }
}
