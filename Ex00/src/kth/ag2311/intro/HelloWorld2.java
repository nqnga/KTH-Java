package kth.ag2311.intro;

public class HelloWorld2 {

	public static void main(String[] args) {
		// Application call: HelloWorld2 PointA 5 5
		
		String locName;	// name of a location of interest
		double xCrd;	// its x-coordinate
		double yCrd;	// its y-coordinate
		Point aPoint;	// Khai bao: point object that represents the location of interest
		
		locName = args[0]; // locName = "PointA"
		xCrd = Double.parseDouble(args[1]); // convert String to Double 
		yCrd = Double.parseDouble(args[2]); // convert String to Double
		aPoint = new Point(locName, xCrd, yCrd); // Create a new object 'aPoint' with type Point
		
		System.out.println(locName + "’s distance from origin " + aPoint.getDistanceFromOrigin());
		System.out.println(locName + "’s distance to origin " + aPoint.getDistanceToOrigin());
		System.out.println(locName + "’s direction from origin " + aPoint.getDirectionFromOrigin());
		System.out.println(locName + "’s direction to origin " + aPoint.getDirectionToOrigin());
	}
}
