package epr.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import epr.Measurements;

/** simple class that draws the correlation result from the experiment */
public class RabPanel extends JPanel{

	private Measurements measure;
	
	double xy[][];
	
	public RabPanel(Measurements measure) {
		this.measure = measure;
		xy= measure.getCorrelations();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(getGraphics());
		// clear
		int w = this.getWidth();
		int h = this.getWidth();
		g.setColor(Color.white);
		g.fillRect(0, 0, w, h);
		
		int BORDER = 40;
		// x from 0 to 360
		double dx = (double)(w-2*BORDER)/360.0;
		double dy = (double)(h-2*BORDER)/2.0;
		int my = BORDER+(int)((h-2*BORDER)/2.0);
		g.setColor(Color.black);
		g.drawLine(BORDER, my, w-BORDER, my);
		g.drawLine(BORDER, BORDER, BORDER, h-BORDER);
		
		g.drawString("Green: cos(alpha)", BORDER, 12);
		g.drawString("Blue:  Rab(alpha)", BORDER, 25);
	//	p("w,h:"+w+", "+h);
	//	p("dx, dy:"+dx+", "+dy);
		
		DecimalFormat f = new DecimalFormat("0.0");
		// draw angles
		for (double x = 0; x <=360.0; x+=22.5) {
			int guix = (int)(x * dx)+BORDER;
			int guiy = (int)(my+15);
			g.drawLine(guix, my-3, guix, my+3);
			g.drawString(""+f.format(x), guix-10, guiy);
		}
		// draw y values from -1 to 1
		for (double y =-1.0;  y <= 1.0; y+= 0.1) {
			int guix = BORDER-20;
			int guiy = (int)(my - y * dy);
			g.drawLine(guix+10, guiy, BORDER, guiy);
			g.drawString(""+f.format(y), guix-10, guiy+5);
			
		}
		g.setColor(Color.blue);
		int iold = 0;
		for (int i = 0; i < xy.length; i++) {
			
			double x = xy[i][0];
			double y = xy[i][1];
			double xold =  xy[iold][0];
			double yold =  xy[iold][1];
			// scale x from 0 - width
			// scale y from 0 to height
			int guix = (int)(x * dx)+BORDER;
			int guiy = (int)(my - y * dy);
			int guixold = (int)(xold * dx)+BORDER;
			int guiyold = (int)(my - yold * dy);
			g.drawOval(guix, guiy, 5, 5);
			g.drawLine(guix, guiy, guixold, guiyold);
			iold = i;
			
	//		p("Plotting x/y "+y+"/"+y);
		}
		// now draw cosine!
		g.setColor(Color.green);
		int xold = 0;
		for (int x = 0; x <=360; x+=1) {
			double y = Math.cos(Math.toRadians(x));			
			double yold = Math.cos(Math.toRadians(xold));
			// scale x from 0 - width
			// scale y from 0 to height
			int guix = (int)(x * dx)+BORDER;
			int guiy = (int)(my - y * dy);
			int guixold = (int)(xold * dx)+BORDER;
			int guiyold = (int)(my - yold * dy);
			g.drawOval(guix, guiy, 1, 1);
			g.drawLine(guix, guiy, guixold, guiyold);
			xold = x;
	//		p("Plotting x/y "+y+"/"+y);
		}
	}

	private void p(String string) {
		System.out.println("RabPanel:"+string);
		
	}
}
