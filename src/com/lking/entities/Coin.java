package com.lking.entities;

import java.awt.image.BufferedImage;

public class Coin extends Entity {
	
	public Coin(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		this.maskx = +6;
		this.masky = +6;
		this.maskw = 4;
		this.maskh = 4;
		
		depth = 1;
	}
	
}
