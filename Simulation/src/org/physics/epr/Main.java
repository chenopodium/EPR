package org.physics.epr;


import tools.FileUtils;
import org.physics.epr.hiddenvars.HiddenVariablesIF;
import org.physics.epr.hiddenvars.MeasurementFormulaIF;

public class Main {

    private Filter filterA;
    private Filter filterB;
    private Measurements measurements;
    private HiddenVariablesIF hiddenVars;
    private MeasurementFormulaIF formula;
    private int times;
    // private Evolver evolver;

    public Main() {
        setupExperiment();
    }

    private void setupExperiment() {
        filterA = new Filter("A");
        filterB = new Filter("B");
        setMeasurements(new Measurements());

    }

    public Measurements runExperiment(int times, MeasurementFormulaIF formula, HiddenVariablesIF hiddenVars) {
        this.hiddenVars = hiddenVars;
        this.formula = formula;
        this.times = times;
    
        for (int t = 0; t < times; t++) {
            runOnce();
        }
        // now compute and print stats
        String res = getMeasurements().toString();
        p(res);

        String filename = "correlations";

        if (Setup.usePredifinedAnglesAB) {
            filename += "_ABsubset";
        } else {
            filename += "_allAB";
        }

        FileUtils.writeStringToFile(filename + ".csv", res);
        return getMeasurements();
    }

    private void runOnce() {
        // create two particles, one going to fildter A later on, one going to filter B
        Particle pA = new Particle("A");
        Particle pB = new Particle("B");
        pA.setMeasurementFormula(formula);
        pB.setMeasurementFormula(formula);
        hiddenVars.shareHiddenVariables(pA, pB);

        // set the angles of Filters A and B
        double angleA_deg = filterA.selectFilterSetting(Setup.A_ANGLES);
        double angleB_deg = filterB.selectFilterSetting(Setup.B_ANGLES);

        int spinA = filterA.measure(pA);
        int spinB = filterB.measure(pB);

        boolean detect = spinA > Integer.MIN_VALUE && spinB > Integer.MIN_VALUE;
        if (detect) {
            // and we record the angle between A and B, the probabilities and the microframe angle in case we want to know        
            DataPoint result = new DataPoint(angleB_deg - angleA_deg, spinA, spinB, pA.getTheta());
            getMeasurements().addMeasurement(result);
        } else {
            getMeasurements().addNoCoincidence();
        }
        // }

    }

    private void p(String s) {
        System.out.println("Experiment: " + s);
    }

    /**
     * @return the measurements
     */
    public Measurements getMeasurements() {
        return measurements;
    }

    /**
     * @param measurements the measurements to set
     */
    public void setMeasurements(Measurements measurements) {
        this.measurements = measurements;
    }
}
