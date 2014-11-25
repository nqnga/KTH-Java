package kth.ag2311.mapalgebra;

import java.util.LinkedList;

/**
 * Storing a Node
 * 
 * @author Nga Nguyen
 *
 */
public class Node {
	
	/**
	 * Name of Node
	 */
	public String name;
	
	/**
	 * Value of Node
	 */
	public double value;
	
	/**
	 * Set of arcs connecting with this note
	 */
	public LinkedList<Arc> outArcs;
	
	/**
	 * Construction method of Node
	 * 
	 * @param name Node name
	 */
	public Node(String name) {
		this.name = name;
		outArcs = new LinkedList<Arc>();
	}
}
