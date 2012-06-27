package epr;

public class BiParticle {
	
	private Particle pleft;
	private Particle pright;	
	
	private double microframe_angle_deg;
	
	/** construct a biparticle based on two particles */
	public BiParticle(Particle pleft, Particle pright) {
		this.pleft = pleft;
		this.pright = pright;
	}

	public double getMicroframeAngleDeg() {
		return microframe_angle_deg;
	}
	/** create the "hidden" variables and pass them on to the 2 particles */ 
	public void shareHiddenVariables(double angle_deg) {
		// this angle orients the microframe to the lab
		microframe_angle_deg = angle_deg;
		
		int nx = 1;
		int nz = 1;
	 
		// we usually want to use a random value for nx and nz (-1 or +1)
		if (!Setup.useJustOneQuadrant) {
			nx = randomDirection();
			nz = randomDirection();
		}		
		
		// use the same hidden variables for second particle 
		pleft.setHiddenVariables(microframe_angle_deg, nx, nz);	
		pright.setHiddenVariables(microframe_angle_deg, nx, nz);
	}

	public String toString() {
		return "The biparticle composed of: "+pleft+" and "+pright+" with variables: ";
	}
	
	private int randomDirection() {
		// between 0 and 1
		double r =  Math.random();
		// convert to + or - 1
		if ( r > 0.5) return 1;
		else return -1;
	}
	
}
