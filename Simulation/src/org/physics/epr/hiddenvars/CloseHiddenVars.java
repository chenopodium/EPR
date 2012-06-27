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
        String s = " double theta = Math.PI/2.0 * (double)(count++ )/ (double)Setup.rotation_sampling;\n"+        
        " if (count > (double)Setup.rotation_sampling) count = 0;\n"+
        " pA.setTheta(theta);\n"+
        " pB.setTheta(theta);\n";
        s += "}\n";
        return s;
    }
}
