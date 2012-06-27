package epr;

import tools.FileUtils;
import epr.gui.ResultsPanel;

public class Main {
	
	private Filter leftFilter;
	private Filter rightFilter;
	private Measurements measurements;
	
	public Main() {
		setupExperiment();		
	}
	
	private void setupExperiment() {
		leftFilter = new Filter("A");
		rightFilter = new Filter("B");
		measurements = new Measurements();		                         
	}
	
	private Measurements runExperiment(int times) {
		for (int i = 0; i < times; i++) {
			if ( i % 10000 < 0) p("Running experiment "+i);
			runOnce();			
		}
		// now compute and print stats
		String res  = measurements.toString();
		p(res);
		
		String filename ="correlations";
		if (Setup.useJustOneQuadrant) filename += "_onequad";
		else filename += "_allquads";
		
		if (Setup.usePredifinedAnglesAB) filename += "_ABsubset";
		else filename += "_allAB";
		
		FileUtils.writeStringToFile(filename+".csv", res);
		return measurements ;
	}
	private void runOnce() {
		// create two particles, one going left later on, one going right
		Particle pleft = new Particle("A");
		Particle pright = new Particle("B");		
		// now set the common hidden variables 
		BiParticle biparticle = new BiParticle(pleft, pright);		
		
		// set the angles of Filters A and B
		double leftangle_deg = leftFilter.selectFilterSetting(Setup.A_ANGLES);
		double rightangle_deg = rightFilter.selectFilterSetting(Setup.B_ANGLES);	
		
		// the difference between A and B
		double angleAB = rightangle_deg - leftangle_deg;
		
		double endangle = 1;
		if (Setup.useAllMicroframes) endangle = 360.0;
		
		// perform the experiment for all microframe angles between 0 and 180 degrees
		for (double theta = 0.0; theta < endangle; theta+=1 ) {
			// both particles will have the same microframe angle
			biparticle.shareHiddenVariables(theta);
			// now we send them to the detector and measure the probability
			double leftspin = leftFilter.measure(pleft);
			double rightspin = rightFilter.measure(pright);
			// and we record the angle between A and B, the probabilities and the microframe angle in case we want to know
			DataPoint result = new DataPoint(angleAB, leftspin, rightspin, (int)biparticle.getMicroframeAngleDeg());
			measurements.addMeasurement(result);
			
		}
	}
	private void p(String s) {
		System.out.println("Experiment: "+s);
	}
	
	
	public static void main(String[] args) {
		Main exp = new Main();
		Measurements m = exp.runExperiment(10000);
		ResultsPanel.show(m);
		
	}
	
}
