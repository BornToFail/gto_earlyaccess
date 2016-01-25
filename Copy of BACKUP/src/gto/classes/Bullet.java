package gto.classes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Bullet {
	private double x;
	private double y;
	private BufferedImage bullet;
	
	public Bullet(double x, double y, Game game){
		this.x = x;
		this.y = y;	
		
		
	}
	/**
	 *Sets the sprite of the bullet, based on the given X and Y values from it's subclasses.
	 */
	public void setSprite(int x, int y, int sizex, int sizey){
		SpriteSheet ss = new SpriteSheet(Game.getSpriteSheet());
		bullet = ss.grabImage(x, y, sizex, sizey);

	}
	/**
	 * Draws the bullet's image on screen
	 */
	public void render(Graphics g){
		g.drawImage(bullet, (int)x, (int)y-17, null);

		
	}
	/**
	 * Allows other classes to get the Y value of a bullet
	 */
	public double getY(){
		return y;
	}
	/**
	 * Allows other classes to get the X value of a bullet
	 */
	public double getX(){
		return x;
	}
	/**
	 * Allows other classes to set the Y value of a bullet
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * Allows other classes to set the X value of a bullet
	 */
	public void setX(double x) {
		this.x = x;
	}
	public abstract void tick();
		
	
}
