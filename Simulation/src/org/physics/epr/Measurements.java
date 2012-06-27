package org.physics.epr;

import java.text.DecimalFormat;

public class Measurements {

    /** stores the neq and eq results. There is abucket for each of the angles such as  0, 45, 90, 135, 180, 225, 270, 360 
     * */
    double correl[][];
    static final int EQ = 1;
    static final int NEQ = 2;
    static final int TOTALNR = 0;
    static final int NR_BUCKETS = Setup.NR_ANGLES + 1;

    private int total;
    private int nocoincidence;
    private int detected;
    public Measurements() {
        correl = new double[NR_BUCKETS][6];
     
    }

    /** compute raw product moment correlations */
    public double computeRawCorrelations(double angle_in_degrees) {
        if (angle_in_degrees == 360) {
            angle_in_degrees = 0;
        }
        int bucket = getBucket(angle_in_degrees);
        //well, eq are the number of ++ or -11... 
        double eq = getEq(bucket);
        double neq = getNeq(bucket);
        // the total (usually eq + neq)
        double tot = getTotal(bucket);
        double Rab = 0;
        // we don't want division by 0 :-)
        if (tot > 0) {
            Rab = (eq - neq) / (eq + neq);
        } else {
            err("Not enough data points for " + angle_in_degrees);
        }

        return Rab;
    }

    /** Get number of same, which is PP and MM */
    public double getEq(int bucket) {
        double eq = correl[bucket][EQ];
        return eq;
    }

    /** Get number of unequal spins, which is MP and PM */
    public double getNeq(int bucket) {
        double Neq = correl[bucket][NEQ];
        return Neq;
    }

    /** Get number of unequal spins, which is MP and PM */
    public double getTotal(int bucket) {
        double tot = correl[bucket][TOTALNR];
        return tot;
    }

    /** Add data point consisting of angle between A and B and both spins */
    public void addMeasurement(DataPoint point) {
        addMeasurement(point.getAngleInDegrees(), (int)point.getSpinA(), (int)point.getSpinB());
    }
    public void addMeasurement(double deg, int spina, int spinb) {
        //	p("Adding data point:"+point);
        this.total++;
        this.detected++;
        
        if (deg < 0) {
            deg = deg + 360;
        }

        // we store the result in buckets of 22.5 degrees - that is more than sufficient
        // but if desired we could of course store more (but typically the test angles are at least 22.5 or even 45
        // degrees apart
        int bucket = (int) (deg / Setup.ANGLE_DELTA);

        // one more measurement
        correl[bucket][TOTALNR]++;
      
        // we get a correlation if both results are the same, otherwise no correlation
        // AND WE MEASURE ALL DATA POINTS!
        double eq = spina == spinb ? 1 : 0;
        double neq = 1.0 - eq;

        // add the eq and neq to the table
        correl[bucket][EQ] += eq;
        correl[bucket][NEQ] += neq;
    }

    public double getCHSH() {
        double RA1B1 = computeRawCorrelations(Math.abs(Setup.A1 - Setup.B1));
        double RA1B2 = computeRawCorrelations(Math.abs(Setup.A1 - Setup.B2));
        double RA2B1 = computeRawCorrelations(Math.abs(Setup.A2 - Setup.B1));
        double RA2B2 = computeRawCorrelations(Math.abs(Setup.A2 - Setup.B2));

        double chsh = Math.abs(RA1B1 - RA1B2 + RA2B1 + RA2B2);
        return chsh;
    }

    /** compute | R(A1, B1) - R(A1, B2) + R(A2, B1) + R(A2, B2) |
     * Using the angles given for the filters in the Setup class
     * @return | R(A1, B1) - R(A1, B2) + R(A2, B1) + R(A2, B2) |
     */
    public String getCHSHResult() {
        // the angles in the Setup class are in DEGREES
        double a1b1  = Math.abs(Setup.A1 - Setup.B1);        
        double a1b2  = Math.abs(Setup.A1 - Setup.B2);
        double a2b1  = Math.abs(Setup.A2 - Setup.B1);
        double a2b2  = Math.abs(Setup.A2 - Setup.B2);
        
        double RA1B1 = computeRawCorrelations(a1b1);
        double RA1B2 = computeRawCorrelations(a1b2);
        double RA2B1 = computeRawCorrelations(a2b1);
        double RA2B2 = computeRawCorrelations(a2b2);

        double qA1B1 = computeQmValue(a1b1);
        double qA1B2 = computeQmValue(a1b2);
        double qA2B1 = computeQmValue(a2b1);
        double qA2B2 = computeQmValue(a2b2);
        
        double chsh = Math.abs(RA1B1 - RA1B2 + RA2B1 + RA2B2);

        DecimalFormat f = new DecimalFormat("0.00");
        String res = "Combination, dAngle, QM, RA1B1 \n";
        res += "A1B1, " + a1b1 + ", "+f.format(qA1B1)+" , " + f.format(RA1B1) + "\n";
        res += "A1B2, " + a1b2 + ", "+f.format(qA1B2)+ ", " + f.format(RA1B2) + "\n";
        res += "A2B1, " + a2b1 + ", "+f.format(qA2B1)+", " + f.format(RA2B1) + "\n";
        res += "A2B2, " + a2b2 + ", "+f.format(qA2B2)+" , " + f.format(RA2B2) + "\n";

        
        res += "|R(A1B1) - R(A1B2) + R(A2B1) + R(A2B2)|,   " + f.format(chsh);        
        return res;
    }

    private void err(String msg) {
        System.err.println("Measurement: " + msg);
    }

    @Override
    public String toString() {
        String res = "";// toCoincidencesString();
    
        res += toRawCorrelationString();
        res += getCHSHResult();
        return res;
    }
    private String toRawCorrelationString() {
        String res = "\nRaw product moment correlation\n\n"
                + "AngleAB (deg), Rab, eq, neq, total count\n";
        for (double deg = 0; deg <= 360; deg += Setup.ANGLE_DELTA) {
            double rab = computeRawCorrelations(deg);
            int bucket = getBucket(deg);
            // nr of equal spins
            double eq = getEq(bucket);
            // nr of not equal spins
            double neq = getNeq(bucket);
            res += deg + ", " + rab + ", " + eq + ", " + neq + ", " + (neq + eq) + "\n";
        }
        return res;
    }

    /** returns x/y coordintaes for a plot to draw the correlations
     * [0] contains x (the angle in degrees)
     * [1] contains y (the rab value)
     * @return
     */
    public double[][] getCorrelations() {

        double xy[][] = new double[Setup.NR_ANGLES + 1][2];
        int i = 0;
        for (double deg = 0; deg <= 360; deg += Setup.ANGLE_DELTA) {
            double rab = computeRawCorrelations(deg);
            xy[i][0] = deg;
            xy[i][1] = rab;
            i++;
        }
        return xy;
    }

    /** Convert angle in degrees to bucket nr */
    private int getBucket(double angle_in_degrees) {
        // only positive buckets...
        if (angle_in_degrees < 0) {
            angle_in_degrees = angle_in_degrees + 360;
        }
        int bucket = (int) (angle_in_degrees / Setup.ANGLE_DELTA);
        return bucket;
    }

    private void p(String msg) {
        System.out.println("Measurements: " + msg);

    }

    public void addNoCoincidence() {
        this.nocoincidence++;
        total++;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @return the undetected
     */
    public int getNoCoincidences() {
        return nocoincidence;
    }

    /**
     * @return the detected
     */
    public int getCoincidences() {
        return detected;
    }

    private double computeQmValue(double angle) {
       return -Math.cos(Math.toRadians(angle));
    }
}
