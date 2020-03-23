package com.lking.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.lking.world.TileDoor;
import com.lking.world.World;

public class AStar {

	public List<Node> path;

	private static Comparator<Node> nodeSorter = new Comparator<Node>() {

		@Override
		public int compare(Node o1, Node o2) {
			if(o1.fCost < o2.fCost)
				return -1;
			if(o1.fCost > o2.fCost)
				return +1;
			
			return 0;
		}
		
	};
	
	
	public static List<Node> findpath(Vector2i start, Vector2i end) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		
		Node current = new Node(start, null, 0, getDistance(start, end));
		openList.add(current);
		while(openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			
			if(current.vector.equals(end)) {
				List<Node> path = new ArrayList<Node>();
				
				while(current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				
				openList.clear();
				closedList.clear();
				return path;
				
			}
			
			openList.remove(current);
			closedList.add(current);
			
			for(int i = 0; i < 4; i++) {
				int x = 0;
				int y = 0;
				if(i == 0) {
					x = current.vector.x;
					y = current.vector.y - 1;
				} else if(i == 1) {
					x = current.vector.x - 1;
					y = current.vector.y;
				} else if(i == 2) {
					x = current.vector.x + 1;
					y = current.vector.y;
				} else if(i == 3) {
					x = current.vector.x;
					y = current.vector.y + 1;
				}
				
				for(int gg = 0; gg < openList.size(); gg++) {
//					System.out.println(openList.get(gg).vector.x + (openList.get(gg).vector.y * World.WIDTH));
				}
				
				// verify if analyzed tile is a wall
				if(!(World.tiles[x + (y * World.WIDTH)] instanceof TileDoor) && World.tiles[x + (y * World.WIDTH)].collision) continue;
				
				Vector2i vector = new Vector2i(x, y);
				double gCost = current.gCost + getDistance(current.vector, vector);
				double hCost = getDistance(vector, end);
				
				Node node = new Node(vector, current, gCost, hCost);
				
				if(vecInList(vector, closedList) && gCost >= current.gCost) continue;
				
				if(!vecInList(vector, openList)) {		// if node is neither on closedList or openList, it still needs to be analyzed
					openList.add(node);
				} else {							// if node is already on openList, we check if its new gCost are worth to replace
					int index = findNode(vector, openList);
					if(index >= 0) {
						openList.remove(index);
					}
					openList.add(node);
						
				}
				
			}
		}
		
		closedList.clear();
		return null;
	}
	
	public static double getDistance(Vector2i start, Vector2i end) {
		return (Math.sqrt((start.x - end.x)*(start.x - end.x) + (start.y - end.y)*(start.y - end.y)));
	}
	
	private static boolean vecInList(Vector2i vector, List<Node> list) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).vector.equals(vector))
				return true;
		}
		
		return false;
	}
	
	private static int findNode(Vector2i vector, List<Node> list) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).vector.x == vector.x && list.get(i).vector.y == vector.y)
				return i;
		}
		return -1;
	}
}
