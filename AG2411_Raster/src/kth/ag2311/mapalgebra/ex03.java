package kth.ag2311.mapalgebra;

/**
 * <h1> Exercise 3 <h1>
 * Test operation on Layer
 * - localSum
 * - focalVariety
 * - zonalMinimum
 * 
 * @author Nga Nguyen
 *
 */
public class ex03 {
	public static void main (String[] args) {
		Layer test = new Layer("test", "c:/DataJava/test.txt");
		Layer focal = test.focalVariety(1, false, "focal");
		
		focal.print(true);
		
		if (args.length >= 2) {
			
		} else {
			System.out.println("Too few arguments or wrong action ...");
			System.out.println("Ex: ex03 ...");
		}
	}
}
