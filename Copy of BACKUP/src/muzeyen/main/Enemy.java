package muzeyen.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Enemy extends MovingObject {

	protected BufferedImage enemy;
	//static ArrayList<Enemy> spawner = new ArrayList<Enemy>();
	public boolean aggressive;

	public Enemy (double x, double y, double xSpeed, double ySpeed, int right, int bottom) {
		super (x,y,xSpeed,ySpeed,right,bottom);
		
	}
	
	public void setSprite(int x, int y, int sizex, int sizey){
		SpriteSheet ss = new SpriteSheet(Game.getSpriteSheet());
		enemy = ss.grabImage(x, y, sizex, sizey);
	}
	
	public void render(Graphics g) {
		g.drawImage(enemy, (int)getX(), (int)getY(), null);
	}


	public boolean bulletcollisionTest(ArrayList <Bullet> bullets){

		for(int j = 0; j< bullets.size(); j++){
			double distance = Math.sqrt(Math.pow(bullets.get(j).getX()- this.getX(),2) + Math.pow(this.getY()-bullets.get(j).getY(),2));
			if (distance < 8){

				bullets.remove(j);
				System.out.print("Enemy hit");
				if (Game.sounds == true){
					Music.playEDeath();
				}
				HUD.score ++;
				return true;
			}
		}

		return false;

	}
	
	
	
	public void animateOneStep() {
	}

	@Override
	public void draw(Graphics g) {
	}
}