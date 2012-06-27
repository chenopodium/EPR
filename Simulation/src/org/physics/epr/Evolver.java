/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.physics.epr;

import tools.FileUtils;

/**
 *
 * @author chantal
 */
public class Evolver {

    /** the ultimate hidden variable table for each [angleA][angleB][1..max]*/
    private int[][] HA;
    private int[][] HB;
    private double[] pA;
    private double[] pB;
    int maxpoints;
    int nra;
    int nrb;
    int A = 0;
    int B = 1;

    public Evolver(int nra, int nrb, int maxpoints) {
        HA = new int[nra][maxpoints+1];
        HB = new int[nrb][maxpoints+1];
        this.maxpoints = maxpoints;
        this.nra = nra;
        this.nrb = nrb;
    }

    public void addB(int anglenrb, int spinb) {
        // 0 is to keep track of index       
        int pos = HB[anglenrb][0] + 1;
        if (pos <= maxpoints) {
            HB[anglenrb][pos] = spinb;
            HB[anglenrb][0] = pos;     
          //  p("added B "+spinb+" for angle "+anglenrb);
        }
        else p("Already collected max nr of  B points");
    }
    public void addA(int anglenra,  int spina) {
        // 0 is to keep track of index
        int pos = HA[anglenra][0] + 1;
        if (pos <= maxpoints) {
            HA[anglenra][pos] = spina;
            HA[anglenra][0] = pos;
         //   p("added A "+spina+" for angle "+anglenra);
        }       
     //  else p("Already collected max nr of A points");
    }

    public void add(double angleA_deg, double angleB_deg, int spinA, int spinB) {
        // only save data if it is one of the angles
        for (int i = 0; i < Setup.A_ANGLES.length; i++) {
            if ((int)(angleA_deg) == (int)(Setup.A_ANGLES[i])) {
                addA(i, spinA);                
            }
        }
         for (int i = 0; i < Setup.B_ANGLES.length; i++) {
             if ((int)(angleB_deg) == (int)(Setup.B_ANGLES[i])) {
                addB(i, spinB);
            }
        }
    }

    private class Mutation {
        int a;        
        int pos;
        int[][] H;
        
        public Mutation(int[][] H) {
            this.H = H;
            a = (int) (Math.random() * H.length);           
            pos = (int) (Math.random() * H[a][0])+1;
        }

        public void flip() {
            int val = H[a][pos];
            if (val == 0) {
                H[a][pos] = 1;
            } else {
                H[a][pos] = 0;
            }
        }
    }
    public Measurements evaluate(int times) {
        Measurements meas = new Measurements(); 
        pA = new double[nra];
        pB = new double[nrb];
        
        for (int a = 0; a < nra; a++) {
            double tot=0;
            int nr = HA[a][0];
            for (int i = 0; i < nr; i++) {
                tot += HA[a][i+1];
            }
            pA[a] = tot/nr;
        }
        for (int a = 0; a < nrb; a++) {
            double tot=0;
            int nr = HB[a][0];
            for (int i = 0; i < nr; i++) {
                tot += HB[a][i+1];
            }
            pB[a] = tot/nr;
        }
        
        for (int i = 0; i < times; i++) {
            int a = (int) (Math.random() * nra);
            int b = (int) (Math.random() * nrb);
            double deg = Math.abs(Setup.A_ANGLES[a] - Setup.B_ANGLES[b]);
            
//            int spina = Math.random()> pA[a] ? 1: 0;
//            int spinb = Math.random()> pB[a] ? 1: 0;
           
            int maxa = HA[a][0];
            int maxb = HB[b][0];
            int spina = HA[a][(int)Math.random()*maxa+1];
            int spinb = HB[b][(int)Math.random()*maxb+1];
            //spina = Math.random()> pA[a] ? 1: 0;
//            if (i < 50){
//                p("A: "+a+", "+Setup.A_ANGLES[a]);
//                p("B: "+b+", "+Setup.B_ANGLES[b]);
//                p("Adding meas: "+deg);
//            }
            meas.addMeasurement(deg, spina, spinb);
        }
        double chsh = meas.getCHSH();
        p("Got chsh: "+chsh);
        return meas;
    }
    
    public int evolve(int maxmuts, int times) {
        int improvements = 0;
        saveData();
        for (int m = 0; m < maxmuts; m++) {
            boolean better = mutateAndTestOnce(times);
            if (better) improvements++;
        }
        p("Got "+improvements+" improvements for "+maxmuts+" mutations");
        if (improvements > 0) saveData();
        return improvements;
    }
    public boolean mutateAndTestOnce(int times) {
        
        Measurements meas = evaluate(times);
        double oldchsh =  meas.getCHSH();
        Mutation mut = null;
        if (Math.random() > 0.5)  mut = new Mutation(HA);
        else mut = new Mutation(HB);
        mut.flip();
        meas = evaluate(times);
        double newchsh =  meas.getCHSH();
        if (newchsh <= oldchsh) {
            // undo mutation
            mut.flip();
            return false;
        }
        else {
            p("Got better value: "+newchsh+", keeping change");
            
            return true;
        }
    }
    public void saveData() {
       String file = "h.csv";
       String s = "A angles, ";
       for (int i = 0; i < Setup.A_ANGLES.length; i++) {
             s += Setup.A_ANGLES[i]+", ";
       }
       s+= "\nB angles, ";
        for (int i = 0; i < Setup.B_ANGLES.length; i++) {
             s += Setup.B_ANGLES[i]+", ";
       }
       s+= "\n\nHA data table\n";
       s += toString(HA);
       s+= "\n\nHB data table\n";
       s += toString(HB);
       FileUtils.writeStringToFile(file, s);
    }
    private String toString(int[][] H) {
        String s = "Nr angles, "+H.length+"\n\n";
        s+= "Angle nr, nr of values, all values\n";
        for (int i = 0; i < H.length; i++) {
             s += i+", ";
             for (int j = 0; j <= H[i][0]; j++) {
                 s+= (int)H[i][j]+", ";
             }
             s += "\n";
        }
        s += "\n";
        return s;
    }
    private void p(String s){
        System.out.println("Evolver: "+s);
    }
}
