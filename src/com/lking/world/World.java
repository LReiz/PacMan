package com.lking.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.lking.entities.Coin;
import com.lking.entities.Entity;
import com.lking.entities.Ghost;
import com.lking.entities.Player;
import com.lking.main.Game;

public class World {

	public static int TS = 16;		// tile size
	public static int HEIGHT, WIDTH;
	
	private static Tile[] tiles;
	
	public World(String path) {
		BufferedImage pixelMap = null;
		try {
			pixelMap = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		WIDTH = pixelMap.getWidth();
		HEIGHT = pixelMap.getHeight();
		
		int[] pixels = new int[WIDTH*HEIGHT];
		pixelMap.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
		
		tiles = new Tile[HEIGHT*WIDTH];
		
		int pixelAtual;
		
		for(int yy = 0; yy < HEIGHT; yy++) {
			for(int xx = 0; xx < WIDTH; xx++) {
				pixelAtual = xx + (yy * WIDTH);
				
				if(pixels[pixelAtual] == 0xFFF9FFFF) {
					tiles[pixelAtual] = new TileFloor(xx*16, yy*16, Tile.FLOOR_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFFFFFFFF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFFE8E5FF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_CORNER_U_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFFD1CCFF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_CORNER_R_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFFBAB2FF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_CORNER_D_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFFA399FF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_CORNER_L_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFF8C7FFF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_H_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFF7566FF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_V_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFF5E4CFF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_CORNER_TL_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFF4732FF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_CORNER_TR_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFF3019FF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_CORNER_BR_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFF1900FF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_CORNER_BL_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFFFB99FF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_3_CORNERS_U_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFFF87FFF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_3_CORNERS_R_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFFF766FF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_3_CORNERS_D_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFFF64CFF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_3_CORNERS_L_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFFF432FF) {
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.WALL_4_CORNERS_TILE);
					continue;
				} else if(pixels[pixelAtual] == 0xFFFF924F){
					tiles[pixelAtual] = new TileWall(xx*16, yy*16, Tile.DOOR_TILE);
					continue;
				} else {
					tiles[pixelAtual] = new TileFloor(xx*16, yy*16, Tile.FLOOR_TILE);
				}
				//-8552682
				
				if(pixels[pixelAtual] == 0xFF7D7F16) {			// Coins
					Game.entities.add(new Coin(xx*16, yy*16, World.TS, World.TS, Entity.COIN));		
					Game.maxCoins++;
					continue;
				} else if(pixels[pixelAtual] == 0xFF00FF15) {			// Player
					Game.player = new Player(xx*16, yy*16, World.TS, World.TS, Entity.PLAYER);
					Game.entities.add(Game.player);		
					continue;
				} else if(pixels[pixelAtual] == 0xFFFF0800) {			// Ghost 1
					System.out.println("boa");
					Game.entities.add(new Ghost(xx*16, yy*16, World.TS, World.TS, Entity.RED_GHOST));		
					continue;
				} else if(pixels[pixelAtual] == 0xFF72FAFF) {			// Ghost 2
					Game.entities.add(new Ghost(xx*16, yy*16, World.TS, World.TS, Entity.BLUE_GHOST));	
					continue;
				} else if(pixels[pixelAtual] == 0xFFFF00BB) {			// Ghost 3
					Game.entities.add(new Ghost(xx*16, yy*16, World.TS, World.TS, Entity.PURPLE_GHOST));		
					continue;
				} else if(pixels[pixelAtual] == 0xFFFF6600) {			// Ghost 4
					Game.entities.add(new Ghost(xx*16, yy*16, World.TS, World.TS, Entity.ORANGE_GHOST));		
					continue;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		for(int yy = 0; yy < HEIGHT; yy++) {
			for(int xx = 0; xx < WIDTH; xx++) {
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
	public static boolean isFree(int x, int y, int width, int height, double speed, int dir) {
		// dir --> 0 (up), 1 (right), 2 (down), 3 (left)
		
		double x1 = x, y1 = y;							// top left
		double x2 = x + width, y2 = y;					// top right
		double x3 = x, y3 = y + height;					// bottom left
		double x4 = x + width, y4 = y + height;			// bottom right
		
		if(dir == 0) {		
			if(tiles[(int) ((int)x1/World.TS + ((int)(y1/World.TS - speed/World.TS) * WIDTH))].collision || tiles[(int) ((int)x2/World.TS + ((int)(y2/World.TS - speed/World.TS)* WIDTH))].collision)
				return false;
		}
		if(dir == 1) {		
			if(tiles[(int) (((int)(x2/World.TS + speed/World.TS) + ((int)(y2/World.TS) * WIDTH)))].collision || tiles[(int) ((int)(x4/World.TS  + speed/World.TS) + ((int)(y4/World.TS)* WIDTH))].collision)
				return false;
		}
		if(dir == 2) {		
			if(tiles[(int) ((int)x3/World.TS + ((int)(y3/World.TS + speed/World.TS) * WIDTH))].collision || tiles[(int) ((int)x4/World.TS + ((int)(y4/World.TS + speed/World.TS)* WIDTH))].collision)
				return false;
		}
		if(dir == 3) {		
			if(tiles[(int) ((int)(x1/World.TS - speed/World.TS) + ((int)(y1/World.TS) * WIDTH))].collision || tiles[(int) ((int)(x3/World.TS - speed/World.TS) + ((int)(y3/World.TS)* WIDTH))].collision)
				return false;
		}
		
		
		return true;
	}
}
