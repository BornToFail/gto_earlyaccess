package gto.classes;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gto.classes.Game.STATE;


/**
 * 
 */

/**
 * @author 342643103
 * Class incomplete
 */


public class Boss extends MovingObject implements Runnable {
	/**
	 * 
	 */
	private double x;
	private double y;
	private static double r;
	
	private static double xSpeed;
	private static double ySpeed;
	
	private int left;
	private int right;
	private int top;
	private int bottom;
	private static int bossHit;
	
	private boolean move;
	
	public long timeOfShot;
	
	
	private static BufferedImage boss;
	
	public static boolean seeBoss = false;
	public static boolean shooting = false;
	
	/*public Boss() {
			
	}
*/	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param xSpeed
	 * @param ySpeed
	 * @param right
	 * @param top
	 */
	public Boss (double x, double y, double xSpeed, double ySpeed, int right, int top) {
		super (x,y,xSpeed,ySpeed,right,top);
	}
	
	
	/**
	 * Primary method for the Boss levels
	 */
	public static void GoBoss(){
		seeBoss = false;
		bossHit = 0;
		Difficulty(); //calls method to determine difficulty
		setXSpeed(1, 0.5);
		setYSpeed(1, 0.5);
		setSprite(seeBoss);
		/*while (Game.State == STATE.GAME){
			FireAway(Game.hudTimer);
		}*/
		
	}
	
	
	/**
	 * Sets the difficulty with
	 * boss speeds
	 */
	public static void Difficulty(){
		/*The difficulty method is used to 
		 * determine the speeds of the boss
		 * Uses Intensity method to determine 
		 * the rate at which the speeds are
		 * multiplied.
		 */
		double level = (HUD.score)/1000; 
		for (int i=1; i<=100; i++){
			if (level==i){
				r = (i*(0.624));
				setXSpeed(1, r);
				setYSpeed(1, r);
			}
		}
	}
	
	/**
	 * 
	 * @param sB
	 */
	public static void setSprite(boolean sB){
		/*Uses a boolean to determine 
		 * whether boss is visible or not
		 * while the game is running.
		 * Also sets the image for 
		 * boss using spritesheets
		 */
		SpriteSheet ss = new SpriteSheet(Game.getSpriteSheet());
		if (sB = false){
			boss = ss.grabImage(10, 10, 32, 32);
		}
		else if (sB = true){
			boss = ss.grabImage(1, 4, 32, 32);
		}
		
		
	}
	
	/**
	 * 
	 */
	public void tick(){
		setX(getX() + getXSpeed());
		setY(getY() + getYSpeed());
	}
	
	/**
	 * 
	 * @param g
	 */
	public void render(Graphics g) {
		g.drawImage(boss,200, 200, null);
	}
	
	/**
	 * 
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * 
	 */
	public double getY(){
		return y;
	}
	
	/**
	 * 
	 */
	public void setX(double x){
		this.x = x;
	}
	
	/**
	 * 
	 */
	public void setY(double y){
		this.y = y;
	}
	
	/**
	 * 
	 * @return
	 */
	public static double getXSpeed(){
		return xSpeed;
	}
	
	/**
	 * 
	 * @return
	 */
	public static double getYSpeed(){
		return ySpeed;
	}
	
	/**
	 * 
	 * @param xSpeed
	 * @param rate
	 */
	public static void setXSpeed(double xSpeed, double rate){
		xSpeed = xSpeed*rate;
		/*Modified set speed method 
		 * takes a double as the rate
		 * for which the speed is
		 * multiplied when the 
		 * method is called
		 */		
	}
	
	/**
	 * 
	 * @param ySpeed
	 * @param rate
	 */
	public static void setYSpeed(double ySpeed, double rate){
		ySpeed = ySpeed*rate;
		/*Modified set speed method 
		 * takes a double as the rate
		 * for which the speed is
		 * multiplied when the 
		 * method is called
		 */		
	}
	
	
	/*private void FireAway(){
		if (shooting = true){ //slows down player when boss shoots
			timeOfShot = System.currentTimeMillis();
			Player.setVelX((Player.velX)*0.20);
			Player.setVelY((Player.velY)*0.20);
		}
		if (System.currentTimeMillis()- timeOfShot > 2000){ //returns player to normal speed 
			Player.setVelX(1);
			Player.setVelY(1);
			
		}
		
		
		
	}*/
	
	/**
	 * Detects boss hits by player 
	 * and adds points to high score
	 * @param Bullets
	 */
	public void BossHit(ArrayList <Bullet> Bullets){
		for(int j = 0; j< Controller.pprojectiles.size(); j++){
			double distance = Math.sqrt(Math.pow(Bullets.get(j).getX()- this.getX(),2) + Math.pow(this.getY()-Bullets.get(j).getY(),2));
			if (distance < 8){
				Controller.pprojectiles.remove(j);
				System.out.print("Boss hit");
				HUD.score += 10;
				bossHit++;
			}
			if (bossHit > 10){
				setXSpeed(xSpeed, 0);
				setYSpeed(ySpeed, 0);
				seeBoss = false;
				setSprite(seeBoss);
				
			}
		}
		//FireAway();
	}
	
	/**
	 * 	Detects walls for the boss
	 */
	public void wallCollision(){
		if (this.getX() >= 400 )
			setXSpeed(xSpeed, -1);
		if (this.getX() <= 0 )
			setXSpeed(xSpeed, -1);
		if (this.getY() <= 0){
			setYSpeed(ySpeed, -1);
		}
		
	}
	
	/**
	 * 	
	 */
	public void run() {
		while (move) {
			x += xSpeed;
			y += ySpeed;
			if (x >= right | x <= left){
				//XSpeed *= -1;
				x = left;
			}
			if (x >= left | x <= right){
				x = right;
			}
			if (y >= bottom | y <= top){
				//YSpeed *= -1;
				y = top;
			}
			if (y >= top | y <= bottom){
				y = bottom;
			}			
		}
	}

	@Override
	public void animateOneStep() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}

