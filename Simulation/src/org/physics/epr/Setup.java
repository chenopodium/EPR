package org.physics.epr;

/** Class containing static settings for the experiment, such as what angles to use */
public class Setup {
	
	// use just 2 angles each or use random angles in 22.5 degree steps (that is more than sufficient)
	
	public static boolean usePredifinedAnglesAB = false;
	
	// do we use just ONE micro frame angle or many values between 0 .. 180 degrees?	
	public static boolean useAllMicroframes = true;
	
	public static int rotation_sampling = 100; // sampling of spin rotation angles
	/** Angle 1 for A */
	public static double A1 = 0;

	/** Angle 2 for A */
	public static double A2 = 90;
	
	/** Our test angles for A */
	public static double A_ANGLES[] = { A1, A2 };
	
	/** Angle 1 for B */
	public static double B1 = 45;

	/** Angle 2 for B */
	public static double B2 = 135;
		
	/** Our test angles for B */
	public static double B_ANGLES[] = { B1, B2};
	
	/** we compute statistics for every 22.5 degrees */
	static double ANGLE_DELTA = 22.5d;
	
	/** The number of angles given our angle step size above*/
	static int NR_ANGLES = (int)(360.0 / Setup.ANGLE_DELTA);
	
	
}
