package org.physics.epr.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import org.physics.epr.Measurements;
import org.physics.epr.Setup;

/** simple class that draws the correlation result from the experiment */
public class RabPanel extends JPanel {

    private Measurements measure;
    double xy[][];

    public RabPanel(Measurements measure) {
        this.measure = measure;
        xy = measure.getCorrelations();
        
    }

    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(getGraphics());
        // clear
        int w = this.getWidth();
        int h = this.getWidth()/2;
        g.setColor(Color.white);
        g.fillRect(0, 0, w, h);

        int BORDER = 40;
        // x from 0 to 360
        double dx = (double) (w - 2 * BORDER) / 360.0;
        double dy = (double) (h - 2 * BORDER) / 2.0;
        int my = BORDER + (int) ((h - 2 * BORDER) / 2.0);
        g.setColor(Color.black);
        g.drawLine(BORDER, my, w - BORDER, my);
        g.drawLine(BORDER, BORDER, BORDER, h - BORDER);

        g.drawString("Black: cos(alpha)", BORDER, 12);
        g.drawString("Blue:  Rab(alpha)", BORDER, 25);
        //	p("w,h:"+w+", "+h);
        //	p("dx, dy:"+dx+", "+dy);

        DecimalFormat f = new DecimalFormat("0.0");
        // draw angles
        for (double x = 0; x <= 360.0; x += 22.5) {
            int guix = (int) (x * dx) + BORDER;
            int guiy = (int) (my + 15);
            g.drawLine(guix, my - 3, guix, my + 3);
            g.drawString("" + f.format(x), guix - 10, guiy);
        }
        // draw y values from -1 to 1
        for (double y = -1.0; y <= 1.0; y += 0.1) {
            int guix = BORDER - 20;
            int guiy = (int) (my - y * dy);
            g.drawLine(guix + 10, guiy, BORDER, guiy);
            g.drawString("" + f.format(y), guix - 10, guiy + 5);

        }
        g.setColor(Color.blue);
        int iold = 0;
        for (int i = 0; i < xy.length; i++) {

            double x = xy[i][0];
            double y = xy[i][1];
            double xold = xy[iold][0];
            double yold = xy[iold][1];
            // scale x from 0 - width
            // scale y from 0 to height
            int guix = (int) (x * dx) + BORDER;
            int guiy = (int) (my - y * dy);
            int guixold = (int) (xold * dx) + BORDER;
            int guiyold = (int) (my - yold * dy);
            g.drawOval(guix, guiy, 5, 5);
            g.drawLine(guix, guiy, guixold, guiyold);
            iold = i;

            //		p("Plotting x/y "+y+"/"+y);
        }
        // now draw cosine!
        
        int xold = 0;
        double a1b1  = Math.abs(Setup.A1 - Setup.B1);        
        double a1b2  = Math.abs(Setup.A1 - Setup.B2);
        double a2b1  = Math.abs(Setup.A2 - Setup.B1);
        double a2b2  = Math.abs(Setup.A2 - Setup.B2);
        for (int x = 0; x <= 360; x += 1) {
            g.setColor(Color.black);
            double y = Math.cos(Math.toRadians(x));
            double yold = Math.cos(Math.toRadians(xold));
            // scale x from 0 - width
            // scale y from 0 to height
            int guix = (int) (x * dx) + BORDER;
            int guiy = (int) (my - y * dy);
            int guixold = (int) (xold * dx) + BORDER;
            int guiyold = (int) (my - yold * dy);
            g.drawOval(guix, guiy, 1, 1);
            g.drawLine(guix, guiy, guixold, guiyold);
            if (x == a1b2 ) {
                g.setColor(Color.red);
                g.drawLine(guix, my, guix, guiy);
            }
            else if (x == a1b1 || x == a2b1 || x == a2b2) {
                g.setColor(Color.green);
                g.drawLine(guix, my, guix, guiy);
            }
            xold = x;
            //		p("Plotting x/y "+y+"/"+y);
        }
    }

    private void p(String string) {
        System.out.println("RabPanel:" + string);

    }
}
