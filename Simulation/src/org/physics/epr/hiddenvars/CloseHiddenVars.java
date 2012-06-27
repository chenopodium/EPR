package org.physics.epr.hiddenvars;

import java.util.Random;
import org.physics.epr.Particle;
import org.physics.epr.Setup;

/**
 *
 * @author Chantal Roth
 */
public class CloseHiddenVars implements HiddenVariablesIF {

    static int count = 0;

    @Override
    public void shareHiddenVariables(Particle pA, Particle pB) {

        double theta = Math.PI/2.0 * (double)(count++ )/ (double)Setup.rotation_sampling;
        
        if (count > (double)Setup.rotation_sampling) count = 0;
        pA.setTheta(theta);
        pB.setTheta(theta);
    }

    @Override
    public String getFormula() {
        String s = "public void shareHiddenVariables(Particle pA, Particle pB) {\n";
        s += "double theta = Math.PI/2.0 * (double)(count )/ (double)Setup.rotation_sampling;\n";       
        s += "if (count > (double)Setup.rotation_sampling) count = 0;\n";
        s += "pA.setTheta(theta);\n";
        s += "pB.setTheta(theta);\n";
        s += "return s;\n";
        s += "}\n";
        return s;
    }
}
