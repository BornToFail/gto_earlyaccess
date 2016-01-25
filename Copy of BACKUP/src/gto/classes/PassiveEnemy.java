/**
 * 
 */
package gto.classes;

import java.awt.image.BufferedImage;

/**
 * @author jam
 *
 */
public class PassiveEnemy extends Enemy {

	/**
	 * @param x
	 * @param y
	 * @param xSpeed
	 * @param ySpeed
	 * @param right
	 * @param bottom
	 */
	public PassiveEnemy(double x, double y, double xSpeed, double ySpeed, int right, int bottom) {
		super(x, y, xSpeed, ySpeed, right, bottom);
		this.setSprite(4, 1, 32, 32);
	}

}
