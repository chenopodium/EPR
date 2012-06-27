A Java simulation of a typical EPR experiment by creating "entangled" particles that are sent to two detectors A and B. 
After many such experiments the statistics are computed, including the CHSH value and also the correlation for each angle.

You can easily to add your own model if you want to attempt to break the CHSH inequality, by adding a class to:
Simulation\src\org\physics\epr\hiddenvars

And by changing the class used in the main class:
Simulation\src\org\physics\epr\gui\SimulationTopComponent

The current version is able to break it - but only by allowing non-detection:

See class CloseFormula:
 if (Math.random()<=pdetect) return spin;
 else return Integer.MIN_VALUE;

By allowing the non-detection of certain particles, the CHSH inequality can be broken.

I would claim that this is the only way to break it...  ;-) 
