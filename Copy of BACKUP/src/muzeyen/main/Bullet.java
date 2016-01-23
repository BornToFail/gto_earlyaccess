package muzeyen.main;

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
	
	public void setSprite(int x, int y, int sizex, int sizey){
		SpriteSheet ss = new SpriteSheet(Game.getSpriteSheet());
		bullet = ss.grabImage(x, y, sizex, sizey);

	}

	public void render(Graphics g){
		g.drawImage(bullet, (int)x, (int)y-17, null);

		
	}
	
	public double getY(){
		return y;
	}
	
	public double getX(){
		return x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setX(double x) {
		this.x = x;
	}
	public abstract void tick();
		
	
}
