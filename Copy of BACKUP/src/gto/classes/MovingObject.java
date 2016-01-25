package gto.classes;

import java.awt.Graphics;

/**
 * 
 * @author 322211632
 *Adapted from Christina Kemp
 */

public abstract class MovingObject implements Runnable {
	
	private double x;
	private double y;
	
	private double xSpeed;
	private double ySpeed;
	
	private int PauseDuration;
	
	private boolean moving;
	private boolean offscreen;
	
	private int right;
	private int bottom;

	public MovingObject(double x, double y, double xSpeed, double ySpeed, int right, int bottom) {
		this.PauseDuration = 40;
		
		this.setxSpeed(xSpeed);
		this.setySpeed(ySpeed);
		
		this.x=x;
		this.y=y;
		
		this.right=right;
		this.bottom=bottom;
		StartThread();
		
	}
	
	
	public void StartThread(){
		moving=true;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void StopThread(){
		moving=false;
	}
	
	public void run() {
		while (moving) {
			animateOneStep();
			x += xSpeed;
			y += ySpeed;
			
			if(x <= 0 | x >= right)
				xSpeed *= -1;

			if (y >= bottom)
				offscreen = true;
			
			try {
				Thread.sleep(PauseDuration);
			} catch (InterruptedException e) {
			}
		}
	}

	abstract public void animateOneStep();
	
	abstract public void draw(Graphics g);
	
	public boolean isOffScreen(){
		return offscreen;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y){
		this.y=y;
	}
	
	public double getxSpeed() {
		return xSpeed;
	}


	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}


	public double getySpeed() {
		return ySpeed;
	}


	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}

}
