package com.lking.world;

import java.awt.image.BufferedImage;

public class TileDoor extends Tile {

	protected TileDoor(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		
		collision = true;
	}

}
