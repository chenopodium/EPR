package epr;

/** Class containing static settings for the experiment, such as what angles to use */
public class Setup {
	
	// use just nx=nz=1 or else use random quadrants
	
	public static final boolean useJustOneQuadrant = false;
	
	// use just 2 angles each or use random angles in 22.5 degree steps (that is more than sufficient)
	
	public static final boolean usePredifinedAnglesAB = false;
	
	// do we use just ONE micro frame angle or many values between 0 .. 180 degrees?	
	public static final boolean useAllMicroframes = true;
	
	// do we change nx and nz for the second particle just prior to measurement?
	public static final boolean useResonance = false;
	
	/** Angle 1 for A */
	public static final double A1 = 0;

	/** Angle 2 for A */
	public static final double A2 = 90;
	
	/** Our test angles for A */
	public static final double A_ANGLES[] = { A1 };
	
	/** Angle 1 for B */
	public static final double B1 = 45;

	/** Angle 2 for B */
	public static final double B2 = 135;
		
	/** Our test angles for B */
	public static final double B_ANGLES[] = { B1, B2};
	
	/** we compute statistics for every 22.5 degrees */
	static final double ANGLE_DELTA = 22.5;
	
	/** The number of angles given our angle step size above*/
	static final int NR_ANGLES = (int)(360.0 / Setup.ANGLE_DELTA);
	
	
}
