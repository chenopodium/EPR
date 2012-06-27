package org.physics.epr.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.physics.epr.Measurements;

/** simple class that draws the correlation result from the experiment */
public class ResultsPanel extends JPanel{

	private Measurements measure;
	
	public ResultsPanel(Measurements measure) {
		this.measure = measure;
		setLayout(new BorderLayout());
		add("Center", new RabPanel(measure));		
	}
	
	public static void show(Measurements measure) {
		
		JFrame f = new JFrame();
		ResultsPanel p = new ResultsPanel(measure);
		
		f.getContentPane().add(p);
		f.pack();
		p.setSize(600, 600);
		f.setSize(600, 600);
		
		f.show();
	}
	
}
