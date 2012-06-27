package epr;

public class Playground {

			
		public static void main(String[] args) {
			integrateCounts();
		}

		private static void integrateWithSimpsonsRule() {
			double tot = 0;
			int count  = 0;
			double dx = 0.01;
			double da = Math.PI/4;
			for (double x = 0.0; x < 360.0; x+=dx ) {
				double rad1 = Math.toRadians(x);
				double dr = Math.toRadians(dx);
				
				double fa = Math.sin(rad1)*Math.sin(rad1+da)/(Math.PI);
				double fm = 4.0*Math.sin(rad1+dr/2.0)*Math.sin(rad1+da+dr/2.0)/(Math.PI);
				double fb = Math.sin(rad1+dr)*Math.sin(rad1+da+dr)/(Math.PI);
				
				double y = dr/6.0* (fa + fm + fb); 
				count++;
				tot+= y;
			}
			p("simpson: corr: "+tot);
		}
		private static void integrateSimple() {
			double tot = 0;
			double rtot = 0;
			
			int count  = 0;
			double dx = 1.0;
			double da = Math.PI/4;
			for (double x = 0.0; x < 360.0; x+=dx ) {
				double rad1 = Math.toRadians(x);
				
				// both probs from both particles
				double p1 = Math.sin(rad1)*Math.sqrt(2);
				double p2 = Math.sin(rad1+da)*Math.sqrt(2);
							
				// nr equal incidences
				double y =  p1*p2; 
				
				double r  = 2*p1*p2 -1;
				// in reality, we subtract 1- p1*p2
				count++;
				tot+= y;
				rtot += r;
			}
			p("simple: tot/count:"+tot/count+", rtot/count="+rtot/count);
		}
		private static void integrateCounts() {
			double totsame = 0;
			double totdiff = 0;
			
			Particle part = new Particle("test");
			int count  = 0;
			double dx = 1.0;
			double da = 22.5;
			double exp = 1000.0;
			for (double x = 0.0; x < 360.0; x+=dx ) {
				
				// both probs from both particles
				double p1 = part.computeProbability(x);
				double p2 = part.computeProbability(x+da);
				
				double psame = p1*p2 + (1.0-p1)*(1.0-p2);
				double pdiff =  (1.0 - p1)* p2 + p1* (1-p2);							
				
				p("psame="+psame+", pdiff:"+pdiff+", sum:"+(psame+pdiff));
				int same = (int)(exp*psame);
				int diff = (int)(exp*pdiff);
				// nr equal incidences
				// in reality, we subtract 1- p1*p2
				count+=same+diff;
				totsame+= same;
				totdiff+=diff;
				
			}
			p("simple: (same-diff)/total="+(totsame-totdiff)/count);
			
			double e = 0.5+Math.sqrt(2)/4.0;
			double n = 0.5-Math.sqrt(2)/4.0;
			p("e, n ="+e+", "+n+", (e-n)(e+n)="+((e-n)/(e+n)));
			
		}
		private static void p(String string) {
			System.out.println(string);
			
		}
	

}
