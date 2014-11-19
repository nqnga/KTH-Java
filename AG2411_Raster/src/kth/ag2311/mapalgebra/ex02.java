package kth.ag2311.mapalgebra;

/**
 * <h1> Exercise 2 <h1>
 * Test view Layer on the screen
 * 
 * @author Nga Nguyen
 *
 */
public class ex02 {
	public static void main (String[] args) {
		if (args.length >= 2) {
			//Instantiate a layer
			Layer layer = new Layer(args[0], args[1]);
			//Show it on the screen with gray scale level
			layer.map();
			//Show it on the screen with color
			double[] color = {2.0,3.0};
			layer.map(color);
		} else {
			System.out.println("Too few arguments ...");
			System.out.println("Ex: ex02 Layer_Development c:/DataJava/dev_ascii.txt ...");
		}
	}
}
