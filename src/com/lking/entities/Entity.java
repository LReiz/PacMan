package com.lking.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.lking.main.Game;
import com.lking.world.World;

public class Entity {

	public int depth;
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected BufferedImage sprite;
	
	public double maskx = 0, masky = 0;
	public int maskw = World.TS, maskh = World.TS;
	
	public static BufferedImage COIN = Game.spritesheet.getSprite(0, 8*World.TS, World.TS, World.TS);
	public static BufferedImage PLAYER = Game.spritesheet.getSprite(0, 0, World.TS, World.TS);
	public static BufferedImage RED_GHOST = Game.spritesheet.getSprite(4*World.TS, 0, World.TS, World.TS);
	public static BufferedImage BLUE_GHOST = Game.spritesheet.getSprite(4*World.TS, 1*World.TS, World.TS, World.TS);
	public static BufferedImage PURPLE_GHOST = Game.spritesheet.getSprite(4*World.TS, 2*World.TS, World.TS, World.TS);
	public static BufferedImage ORANGE_GHOST = Game.spritesheet.getSprite(4*World.TS, 3*World.TS, World.TS, World.TS);
	
	
	public Entity(double x, double y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, (int) x, (int) y, World.TS, World.TS, null);
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
}
