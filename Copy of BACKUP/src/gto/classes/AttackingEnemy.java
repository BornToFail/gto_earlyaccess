/**
 * 
 */
package gto.classes;

import java.awt.image.BufferedImage;

/**
 * @author jam
 *
 */
public class AttackingEnemy extends Enemy {
	/**
	 * @param x
	 * @param y
	 * @param xSpeed
	 * @param ySpeed
	 * @param right
	 * @param bottom
	 */
	public AttackingEnemy(double x, double y, double xSpeed, double ySpeed, int right, int bottom) {
		super(x, y, xSpeed, ySpeed, right, bottom);
		this.setSprite(4, 2, 32, 32);
		// TODO Auto-generated constructor stub
	}
	public boolean checkPosition(Player p){
		if(this.getX()==p.getX()&&(this.getY()<p.getY())){
			return true;
		}
		else{
			return false;
		}

	}

}
