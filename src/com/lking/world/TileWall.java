package com.lking.world;

import java.awt.image.BufferedImage;

public class TileWall extends Tile {

	protected TileWall(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
		collision = true;
	}

}
