package com.lking.entities;

import java.awt.image.BufferedImage;

public class Ghost extends Entity {

	public Ghost(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		depth = 0;
	}

}
