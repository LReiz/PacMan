package com.lking.astar;

public class Vector2i {
	
	public int x, y;
	
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean equals(Vector2i B) {
		if(this.x == B.x && this.y == B.y)
			return true;
		return false;

	}
	
}

