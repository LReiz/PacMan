package com.lking.entities;

import java.awt.image.BufferedImage;
import java.util.List;

import com.lking.astar.AStar;
import com.lking.astar.Node;
import com.lking.astar.Vector2i;
import com.lking.main.Game;
import com.lking.world.World;

public class Ghost extends Entity {
	
	private boolean locked = false;		// variable that prevents ghost path to update in the wrong moment
	
	private double speed = 1;
	
	private List<Node> path;

	public Ghost(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		depth = 0;
	}
	
	public void tick() {
		Vector2i start = new Vector2i((int)((this.getX()+8)/World.TS), (int)((this.getY()+8)/World.TS));
		Vector2i end = new Vector2i((int)((Game.player.getX()+8)/World.TS), (int)((Game.player.getY()+8)/World.TS));
		
//		System.out.println("start " + start.x + " " + start.y);
//		System.out.println("end " + end.x + " " + end.y);
		
		if(path == null)
			path = AStar.findpath(start, end);
		if(!locked && Game.rand.nextInt(100) < 10)
			path = AStar.findpath(start, end);
		
		followPath(path, this.speed);
		
	}

	public void followPath(List<Node> path, double speed) {
		if(path != null && path.size() > 0) {
			Node nextNode = path.get(path.size() - 1);
			
			if(x > nextNode.vector.x * World.TS) {
				x -= speed;
				locked = true;
			}
			else if(x < nextNode.vector.x * World.TS) {
				x += speed;	
				locked = true;
			}
			else if(y > nextNode.vector.y * World.TS) {
				y -= speed;
				locked = true;				
			}
			else if(y < nextNode.vector.y * World.TS) {
				y += speed;
				locked = true;				
			}
			
			if(x == nextNode.vector.x * World.TS && y == nextNode.vector.y * World.TS) {
				path.remove(path.size() - 1);
				locked = false;
			}

		}
	}
	
}
