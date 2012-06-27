/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.physics.epr.hiddenvars;

import org.physics.epr.Particle;

/**
 *
 * @author Chantal Roth
 */
public interface HiddenVariablesIF {
    
    public void shareHiddenVariables(Particle pA, Particle pB);

    public String getFormula();
}
