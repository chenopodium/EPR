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
public interface MeasurementFormulaIF {
    
    public int measure (double filter_angle_degrees, Particle partice);

    public String getFormula();
}
