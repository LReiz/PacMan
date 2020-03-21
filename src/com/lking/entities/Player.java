package com.lking.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.lking.main.Game;
import com.lking.world.World;

public class Player extends Entity {

	private int framesCounter = 0;
	private int animationFramesSpeed = 3;
	private int curAnimation = 0;
	private int maxAnimation = 4;
	
	public double speed = 2;
	
	public boolean right = true, left, up, down;
	public boolean pendingRight, pendingLeft, pendingUp, pendingDown;
	
	private BufferedImage[] upPlayer;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage[] leftPlayer;
	
//	public static double maskx = +1, masky = +1;
//	public static int maskw = 14, maskh = 14;
	
	public Player(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		this.maskx = +1;
		this.masky = +1;
		this.maskw = 14;
		this.maskh = 14;
		
		upPlayer = new BufferedImage[4];
		rightPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		
		for(int i = 0; i < 4; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(i*World.TS, 0, World.TS, World.TS);
		}
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(i*World.TS, 1*World.TS, World.TS, World.TS);
		}
		for(int i = 0; i < 4; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(i*World.TS, 2*World.TS, World.TS, World.TS);
		}
		for(int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(i*World.TS, 3*World.TS, World.TS, World.TS);
		}
		
		depth = 0;
	}

	public void tick() {
		if(pendingUp && World.isFree((int) (this.getX() + maskx), (int) (this.getY() + masky), maskw, maskh, speed, 0)) {
				this.y -= speed;
				pendingUp = false;
				up = true;
			
		} else if(pendingRight && World.isFree((int) (this.getX() + maskx), (int) (this.getY() + masky), maskw, maskh, speed, 1)) {
				this.x += speed;
				pendingRight = false;
				right = true;
				
		} else if(pendingDown && World.isFree((int) (this.getX() + maskx), (int) (this.getY() + masky), maskw, maskh, speed, 2)) {
				this.y += speed;
				pendingDown = false;
				down = true;

		} else if(pendingLeft && World.isFree((int) (this.getX() + maskx), (int) (this.getY() + masky), maskw, maskh, speed, 3)) {
				this.x -= speed;
				pendingLeft = false;
				left = true;
				
		} else {
			if(up && World.isFree((int) (this.getX() + maskx), (int) (this.getY() + masky), maskw, maskh, speed, 0)) {
				this.y -= speed;
				right = false;
				down = false;
				left = false;
			}
			if(right && World.isFree((int) (this.getX() + maskx), (int) (this.getY() + masky), maskw, maskh, speed, 1)) {
				this.x += speed;
				up = false;
				down = false;
				left = false;
			}
			if(down && World.isFree((int) (this.getX() + maskx), (int) (this.getY() + masky), maskw, maskh, speed, 2)) {
				this.y += speed;
				up = false;
				right = false;
				left = false;
			}
			if(left && World.isFree((int) (this.getX() + maskx), (int) (this.getY() + masky), maskw, maskh, speed, 3)) {
				this.x -= speed;
				up = false;
				right = false;
				down = false;
			}
		}
		
		framesCounter++;
		if(framesCounter >= animationFramesSpeed) {
			framesCounter = 0;
			curAnimation++;
			
			if(curAnimation >= maxAnimation) {
				curAnimation = 0;
			}
		}
		
		for(int i = 0; i < Game.entities.size(); i++) {
			if(isCollidingWithPlayer(Game.entities.get(i))) {
				if(Game.entities.get(i) instanceof Coin){
					Game.entities.remove(i);
					Game.curCoins++;
				}	
			}
		}
		
		if(Game.curCoins == Game.maxCoins)
			System.out.println("you win");
	}
	
	public void render(Graphics g) {
		if(up) {
			g.drawImage(upPlayer[curAnimation], (int) this.getX(), (int) this.getY(), World.TS, World.TS, null);
		} else if(right) {
			g.drawImage(rightPlayer[curAnimation], (int) this.getX(), (int) this.getY(), World.TS, World.TS, null);
		} else if(down) {
			g.drawImage(downPlayer[curAnimation], (int) this.getX(), (int) this.getY(), World.TS, World.TS, null);
		} else if(left) {
			g.drawImage(leftPlayer[curAnimation], (int) this.getX(), (int) this.getY(), World.TS, World.TS, null);
		}
	}
	
	protected static boolean isCollidingWithPlayer(Entity e2) {
		Rectangle playerRect = new Rectangle((int)(Game.player.getX() + Game.player.maskx), (int)(Game.player.getY() + Game.player.masky), Game.player.maskw, Game.player.maskh);
		Rectangle e2Rect = new Rectangle((int) (e2.getX() + e2.maskx), (int) (e2.getY() + e2.masky), e2.maskw, e2.maskh);

		if(playerRect.intersects(e2Rect))
			return true;
		
		return false;
	}
}
