package epr;

public class Measurements {

	/** stores the neq and eq results. There is abucket for each of the angles such as  0, 45, 90, 135, 180, 225, 270, 360 
	 * */	
	double correl[][] ;
	static final int EQ = 1;
	static final int NEQ = 2;
				
	static final int TOTALNR = 0;		
	static final int NR_BUCKETS = Setup.NR_ANGLES+1;
	
	public Measurements() {
		correl= new double[NR_BUCKETS][6];
		//rawresults= new double[NR_BUCKETS][180][6];
		// correlations as function of theta
	
	}	
	/** compute raw product moment correlations */
	public double computeRawCorrelations(double angle_in_degrees) {
		if (angle_in_degrees == 360) angle_in_degrees = 0;
		int bucket = getBucket(angle_in_degrees);
		//well, eq are the number of ++ or -11... 
		double eq = getEq(bucket);
		double neq = getNeq(bucket);
		// the total (usually eq + neq)
		double tot = getTotal(bucket);
		double Rab = 0;
		// we don't want division by 0 :-)
		if (tot > 0) Rab = (eq - neq) / ( eq + neq);
		else err("Not enough data points for "+angle_in_degrees);
		
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
	//	p("Adding data point:"+point);
		double deg = point.getAngleInDegrees();
		if (deg < 0) deg = deg + 360;
		
		// we store the result in buckets of 22.5 degrees - that is more than sufficient
		// but if desired we could of course store more (but typically the test angles are at least 22.5 or even 45
		// degrees apart
		int bucket = (int) (deg/Setup.ANGLE_DELTA);

		// one more measurement
		correl[bucket][TOTALNR]++;
		
		// the probabilties! A la fractional spin
		double a = Math.round(point.getLeftspin())*2-1;
		double b = Math.round(point.getRightspin())*2-1;
		double eq = a==b? 1: 0;
		double neq =  1.0 - eq;
		// eq = either both + or both -		
		//double eq = a*b + (1.0-a) *(1.0-b);
		// neq = +- or -1 
		//double neq = a*(1.0-b) + b*(1.0-a);
		
		// add the eq and neq to the table
		correl[bucket][EQ] += eq;
		correl[bucket][NEQ] += neq;
	}
	/** compute | R(A1, B1) - R(A1, B2) + R(A2, B1) + R(A2, B2) |
	 * Using the angles given for the filters in the Setup class
	 * @return | R(A1, B1) - R(A1, B2) + R(A2, B1) + R(A2, B2) |
	 */
	public String getCHSHResult() {
		// the angles in the Setup class are in DEGREES
		double RA1B1 = computeRawCorrelations(Math.abs(Setup.A1 - Setup.B1));
		double RA1B2 = computeRawCorrelations(Math.abs(Setup.A1 - Setup.B2));
		double RA2B1 = computeRawCorrelations(Math.abs(Setup.A2 - Setup.B1));
		double RA2B2 = computeRawCorrelations(Math.abs(Setup.A2 - Setup.B2));
		
		double chsh = Math.abs(RA1B1 - RA1B2 + RA2B1 + RA2B2);
		
		String res = "\nCHSH Calculation\n\n"; 
		res += "Angle A1, "+Setup.A1+"\nAngle A2, "+Setup.A2+"\nAngle B1, "+Setup.B1+"\nAngle B2, "+Setup.B2+"\n";
		res += "A1B1, "+Math.abs(Setup.A1-Setup.B1)+", RA1B1, "+RA1B1+"\n";
		res += "A1B2, "+Math.abs(Setup.A1-Setup.B2)+", RA1B2, "+RA1B2+"\n";
		res += "A2B1, "+Math.abs(Setup.A2-Setup.B1)+", RA2B1, "+RA2B1+"\n";
		res += "A2B2, "+Math.abs(Setup.A2-Setup.B2)+", RA2B2, "+RA2B2+"\n";
		
		res += "|R(A1B1)-R(A1B2)+R(A2B1)+R(A2B2)|, "+chsh+"\n";
		if (chsh > 2) {
			err("WOOHOOO, we violated the inequality! chsh = "+chsh);
		}
		return res;
	}
	private void err(String msg) {
		System.err.println("Measurement: "+msg);
		
	}
	public String toString() {		
		String res = "";// toCoincidencesString();
		//res += toCorrelationPerThetaString();
		res += toRawCorrelationString();
		res += getCHSHResult();
		return res;
	}
//	private String toCoincidencesString() {
//		String res = "\nRaw Coincidence Counts\n\n" +
//				"AngleAB (deg), eq, neq,  total count\n";
//		for (int b = 0; b < correl.length; b++) {
//			res += b*Setup.ANGLE_DELTA+", ";
//			int bucket = b;
//			if (b*Setup.ANGLE_DELTA == 360) bucket = 0;
//			double eq = correl[bucket][EQ];
//			double neq = correl[bucket][NEQ];			
//			double nr = correl[bucket][TOTALNR];
//			res += eq+", "+neq+", "+nr+"\n";
//		}
//		return res;
//	}
	
	private String toRawCorrelationString() {
		String res = "\nRaw product moment correlation\n\n" +
				"AngleAB (deg), Rab, eq, neq, total count\n";
		for (double deg = 0; deg <= 360; deg += Setup.ANGLE_DELTA) {
			double rab = computeRawCorrelations(deg);
			int bucket = getBucket(deg);
			// nr of equal spins
			double eq = getEq(bucket);
			// nr of not equal spins
			double neq = getNeq(bucket);
			res += deg+", "+rab+", "+eq+", "+neq+", "+(neq+eq)+"\n";
		}
		return res;
	}
	/** returns x/y coordintaes for a plot to draw the correlations
	 * [0] contains x (the angle in degrees)
	 * [1] contains y (the rab value)
	 * @return
	 */
	public double[][] getCorrelations() {
		
		double xy[][] = new double[Setup.NR_ANGLES+1][2];
		int i= 0;
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
		if (angle_in_degrees < 0) angle_in_degrees = angle_in_degrees + 360;
		int bucket = (int)(angle_in_degrees / Setup.ANGLE_DELTA);
		return bucket;
	}
	private void p(String msg) {
		System.out.println("Measurements: "+msg);
		
	}
	// unused code
// 	we don't really need those... so commented out
//	int microframe = point.getMicroFrameAngleDeg();
//	rawresults[bucket][microframe][AP] += a;
//	rawresults[bucket][microframe][AM] += (1.0-a);
//	rawresults[bucket][microframe][BP] += b;
//	rawresults[bucket][microframe][BM] += (1.0-b);
	

}
