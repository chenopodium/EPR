package epr;

import java.text.DecimalFormat;

public class Particle {

	// name of particle
	String name;
	
	/** orientation of 2D spin in laboratory in rad*/
	double angle_of_microframe_deg;
	
	/** the quadrant */
	double nx;
	double nz;
	
	/** to make the computation faster, precompte this :-) */
	static final double SQRT2 = Math.sqrt(2);
	// we don't need ny
	
	public Particle(String name) {
		this.name = name;
	}
	/** set by the biparticle during entanglement */
	public void setHiddenVariables(double angle_of_microframe_deg, int nx, int nz) {
		this.angle_of_microframe_deg = angle_of_microframe_deg;
		this.nx = nx;
		this.nz = nz;
	
	}
	
	public String toString() {
		return "Particle "+name;
	}
	/** Create -1 or +1 randomly */
	private int randomDirection() {
		// between 0 and 1
		double r =  Math.random();
		// convert to + or - 1
		if ( r > 0.5) return 1;
		else return -1;
	}

	/** based on the particles "hidden variables", measure the spin. This returns
	 * not +1 or -1, but the probability of +1 (P+)*/
	public double measure(double filter_angle_deg) {
		// deg is the relative angle of the particle relative to the filter
		double deg = filter_angle_deg - angle_of_microframe_deg;		

		
		// compute probability P+
		double prob = computeProbability((int)deg);
		// and return it - no longer converting to +1 or -1!!!
		// this is part of the change that made the curve flat ;-)
		return prob;
		
		
	}

	
	/** based on the particles "hidden variables" */
	public double computeProbability(double deltaangle_deg) {
		
		// the angle between microframe and filter in radiance
		double rad = Math.toRadians(deltaangle_deg);		
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		//breaks it we use just +1 and -1 and also allow 0 for small values!
		//double prob = 2*sin*sin-0.5;		
		// also breaks it with no resonance!!!
		//breaks it we use just +1 and -1 and also allow 0 for small values!
		//double prob = 1.0/SQRT2 * ( nz * cos + nx * sin )  ;
		
		// the original formula... creates a flat line if we use fractional probabilities
		// creates a cos like curve but quite a flat one if we round to +1 or -1
		//double prob = 0.5 * ( 1.0 + 1.0/SQRT2 * ( nz * cos + nx * sin ))  ;
		
		//double prob = 1.0/(2.0+4.0*SQRT2)* ( 3.0  +(1+ 1.0/SQRT2) * ( nz * cos + nx * sin ))  ;
		//p("computeProbability: deg="+deltaangle_deg+", prob= "+prob);
		int a = (int)(deltaangle_deg);
		final int DELTA = 10;
		if (a> 180) a = 360 - a;
		
		if (a < 90+DELTA) return 1;
		else if (a > 135-DELTA) return 0;
		else return 0;
		//return prob;
	}
	
	/** Just for testing! */
	private void showValues(double deg) {
		DecimalFormat f = new DecimalFormat("0.00");		
		double prob = computeProbability(deg);
		double rad = Math.toRadians(deg);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);	
		p("Deg="+deg+", cos="+f.format(cos)+", sin="+f.format(sin)+", prob:"+ f.format(prob));
	}
	
	/** Just for testing! */
	public void testValues() {
		DecimalFormat f = new DecimalFormat("0.00");
		// use 1 quadrant and angle 0 compaed to lab
		setHiddenVariables(0, 1, 1);
		
		for (double deg = 0; deg <=180; deg+= Setup.ANGLE_DELTA) {
			showValues(deg);
		}
		
		double deg = 22.5;
		Particle pa = new Particle("pa");
		Particle pb = new Particle("pb");
		pa.setHiddenVariables(0, 1, 1);
		pb.setHiddenVariables(0, 1, 1);
		double proba = pa.computeProbability(deg);
		double probb = pb.computeProbability(deg);
		double eq = proba*probb;
		double neq = (1.0 - proba)*(1.0 - probb);
		double rab = (eq-neq)/(eq+neq);
		System.out.println("Values for "+deg+", "+deg);
		System.out.println("\npa:"+f.format(proba)+", "+", pb:"+f.format(probb)+", peq:"+f.format(eq)+", pneq:"+f.format(neq)+", rab:"+f.format(rab));
		
		double toteq = 0;
		double totneq = 0;
		for (int i = 0; i < 100; i++) {			
			double spina = pa.measure(deg);
			double spinb = pb.measure(deg);
			if (spina == spinb) toteq++;
			else totneq++;
			//System.out.println("spina:"+spina+", spinb:"+spinb);
		}
		double totrab = (toteq - totneq)/ (toteq + totneq);
		System.out.println("toteq:"+toteq+", totneq:"+totneq+", totrab:"+totrab+", tot:"+(toteq+totneq));
		
		//compute2Dcorr();
	}
	
	private void err(String string) {
		System.err.println("Particle: "+string);
		System.exit(-1);
	}
	public static void main(String[] args) {
		// test measurement
		Particle test = new Particle("test particle");
		test.testValues();
	}
	private void p(String s) {
		System.out.println("Particle:"+s);
	}
}
