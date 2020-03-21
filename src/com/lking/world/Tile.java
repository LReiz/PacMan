package com.lking.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.lking.main.Game;

public class Tile {

	
	public static BufferedImage FLOOR_TILE = Game.spritesheet.getSprite(1*World.TS, 5*World.TS, World.TS, World.TS);
	public static BufferedImage DOOR_TILE = Game.spritesheet.getSprite(3*World.TS, 4*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_TILE = Game.spritesheet.getSprite(4*World.TS, 7*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_H_TILE = Game.spritesheet.getSprite(1*World.TS, 4*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_V_TILE = Game.spritesheet.getSprite(0, 5*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_CORNER_L_TILE = Game.spritesheet.getSprite(3*World.TS, 5*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_CORNER_R_TILE = Game.spritesheet.getSprite(5*World.TS, 5*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_CORNER_U_TILE = Game.spritesheet.getSprite(4*World.TS, 4*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_CORNER_D_TILE = Game.spritesheet.getSprite(4*World.TS, 6*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_CORNER_TL_TILE = Game.spritesheet.getSprite(0, 4*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_CORNER_TR_TILE = Game.spritesheet.getSprite(2*World.TS, 4*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_CORNER_BL_TILE = Game.spritesheet.getSprite(0, 6*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_CORNER_BR_TILE = Game.spritesheet.getSprite(2*World.TS, 6*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_3_CORNERS_L_TILE = Game.spritesheet.getSprite(2*World.TS, 5*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_3_CORNERS_R_TILE = Game.spritesheet.getSprite(0, 7*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_3_CORNERS_U_TILE = Game.spritesheet.getSprite(1*World.TS, 6*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_3_CORNERS_D_TILE = Game.spritesheet.getSprite(1*World.TS, 7*World.TS, World.TS, World.TS);
	public static BufferedImage WALL_4_CORNERS_TILE = Game.spritesheet.getSprite(4*World.TS, 5*World.TS, World.TS, World.TS);

	protected int x;
	protected int y;
	protected BufferedImage sprite;
	protected boolean collision;
	
	protected Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x, y, World.TS, World.TS, null);
	}
}
