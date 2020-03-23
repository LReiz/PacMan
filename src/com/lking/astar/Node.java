package com.lking.astar;

public class Node {

	public Vector2i vector;
	public Node parent;
	protected double gCost;
	protected double hCost;
	protected double fCost;
	
	public Node(Vector2i vector, Node parent, double gCost, double hCost) {
		this.vector = vector;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = gCost + hCost;
	}
	
}
