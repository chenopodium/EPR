package org.physics.epr;

import java.util.Random;

public class Utils {
	
	public static final Random random = new Random();
	static {
		random.setSeed(System.currentTimeMillis());
	}
	
	public static double getRandomValue() {
		return random.nextDouble();
	}
	/** return one value of the array randomly */
	public static double randomElement(double[] values) {
		int i = (int)(getRandomValue()*values.length);
		//System.out.println("Got "+i);
		return values[i];
	}
	
	public static double randomAngleInDeg180() {
		// between 0 and 180
		double deg =(int)( Math.random()*180);	
		// between 0 and 2PI
		return deg;
	}
	
	public static double randomAngleInDeg() {
		// between 0 and 360, but we use 22.5 degrees steps		
		double deg =(int)( getRandomValue()*Setup.NR_ANGLES)*Setup.ANGLE_DELTA;
		return deg;
	}
	
	
	private static void p(String string) {
		System.out.println(string);
		
	}
}
