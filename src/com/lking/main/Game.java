package com.lking.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;

import com.lking.entities.Entity;
import com.lking.entities.Player;
import com.lking.graficos.Spritesheet;
import com.lking.world.World;

public class Game extends Canvas implements KeyListener, Runnable {

	private static final long serialVersionUID = -3064797391161894854L;
	private Thread thread;
	
	private BufferedImage mainImage;
	
	private int WIDTH = World.TS*16 + 7;
	private int HEIGHT = World.TS*17 + 2;
	private int SCALE = 2;
	
	private World world;
	public static Player player;
	
	public static List<Entity> entities;
	
	public static Spritesheet spritesheet;
	
	public InputStream stream_DS = ClassLoader.getSystemClassLoader().getResourceAsStream("Deep Shadow.ttf");
	
	public Font deepshadow;
	
	private Comparator<Entity> entitySorter = new Comparator<Entity>() {

		@Override
		public int compare(Entity o1, Entity o2) {
			if(o1.depth < o2.depth)
				return 1;
			if(o1.depth > o2.depth)
				return -1;
			return 0;
		}
		
	};
	
	public static int maxCoins;
	public static int curCoins;
	
	private Game() {
		mainImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		addKeyListener(this);
		initFrame();

		spritesheet = new Spritesheet("/spritesheet.png");
		entities = new ArrayList<Entity>();
		world = new World("/map1.png");
		
		try {
			deepshadow = Font.createFont(Font.TRUETYPE_FONT, stream_DS).deriveFont(9f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String args[]) {
		Game game = new Game();
		game.start();
		game.stop();
	}
	
	private void tick() {
		player.tick();
		
		Collections.sort(entities, entitySorter);
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = mainImage.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);

		g.setColor(new Color(0xFF000000));
		g.setFont(deepshadow);
		g.drawString("COINS " + curCoins + "-" + maxCoins, 10, 14);

		for(int i = 0; i < entities.size(); i++)
			entities.get(i).render(g);
		
		player.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(mainImage, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		bs.show();
	}
	
	private void initFrame() {
		JFrame frame = new JFrame("Pac-Man");
		frame.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	private void start() {
		thread = new Thread(this);
		thread.start();
	}
	
	private void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		requestFocus();
		// Game FPS control	
		int fps = 60;
		double ns = 1000000000/fps;
		double delta = 0;
		double pastNano = System.nanoTime();
		double currentNano;
		
		int numOfFrames = 0;
		double pastSec = System.currentTimeMillis();
		
		while(true) {
			currentNano = System.nanoTime();
			delta += (currentNano - pastNano);
			pastNano = currentNano;
			if((delta/ns) >= 1) {
				delta = 0;
				numOfFrames++;
				tick();
				render();
			}
			if(System.currentTimeMillis() - pastSec >= 1000) {
//				System.out.println("FPS: " + numOfFrames);
				numOfFrames = 0;
				pastSec = System.currentTimeMillis();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			if(!World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 0)) {
				player.pendingUp = true;
				player.pendingRight = false;
				player.pendingDown = false;
				player.pendingLeft = false;
			}
		} else if(e.getKeyCode() == KeyEvent.VK_D) {
			if(!World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 1)) {
				player.pendingRight = true;
				player.pendingUp = false;
				player.pendingDown = false;
				player.pendingLeft = false;
			}
		} else if(e.getKeyCode() == KeyEvent.VK_S) {
			if(!World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 2)) {
				player.pendingDown = true;
				player.pendingUp = false;
				player.pendingRight = false;
				player.pendingLeft = false;
			}
		} else if(e.getKeyCode() == KeyEvent.VK_A) {
			if(!World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 3)) {
				player.pendingLeft = true;
				player.pendingUp = false;
				player.pendingRight = false;
				player.pendingDown = false;				
			}
		}
		
		
		if(player.up) {
			if(e.getKeyCode() == KeyEvent.VK_D
					&& World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 1)) {
				player.right = true;
				player.up = false;
				player.down = false;
				player.left = false;
			} else if(e.getKeyCode() == KeyEvent.VK_S
					&& World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 2)) {
				player.down = true;
				player.up = false;
				player.right = false;
				player.left = false;
			} else if(e.getKeyCode() == KeyEvent.VK_A
					&& World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 3)) {
				player.left = true;
				player.up = false;
				player.right = false;
				player.down = false;
			}
		} else if(player.right) {
			if(e.getKeyCode() == KeyEvent.VK_W 
					&& World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 0)) {
				player.up = true;
				player.right = false;
				player.down = false;
				player.left = false;
			} else if(e.getKeyCode() == KeyEvent.VK_S
					&& World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 2)) {
				player.down = true;
				player.up = false;
				player.right = false;
				player.left = false;
			} else if(e.getKeyCode() == KeyEvent.VK_A
					&& World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 3)) {
				player.left = true;
				player.up = false;
				player.right = false;
				player.down = false;
			}
		} else if(player.down) {
			if(e.getKeyCode() == KeyEvent.VK_W
					&& World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 0)) {
				player.up = true;
				player.right = false;
				player.down = false;
				player.left = false;
			} else if(e.getKeyCode() == KeyEvent.VK_D
					&& World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 1)) {
				player.right = true;
				player.up = false;
				player.down = false;
				player.left = false;
			} else if(e.getKeyCode() == KeyEvent.VK_A
					&& World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 3)) {
				player.left = true;
				player.up = false;
				player.right = false;
				player.down = false;
			}
		} else if(player.left) {
			if(e.getKeyCode() == KeyEvent.VK_W
					&& World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 0)) {
				player.up = true;
				player.right = false;
				player.down = false;
				player.left = false;
			} else if(e.getKeyCode() == KeyEvent.VK_D
					&& World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 1)) {
				player.right = true;
				player.up = false;
				player.down = false;
				player.left = false;
			} else if(e.getKeyCode() == KeyEvent.VK_S
					&& World.isFree((int) (player.getX() + player.maskx), (int) (player.getY() + player.masky), player.maskw, player.maskh, player.speed, 2)) {
				player.down = true;
				player.up = false;
				player.right = false;
				player.left = false;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}


}
