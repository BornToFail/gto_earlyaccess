/**
 * 
 */
package muzeyen.main;

/**
 * @author jam
 *
 */
public class PerditusBullet2 extends PlayerBullet {
	public PerditusBullet2(double x, double y, Game game) {
		super(x, y, game);
		// TODO Auto-generated constructor stub
	/**
	 * @param x
	 * @param y
	 * @param game
     */

	setSprite(5, Player.selectedCharacter, 32, 32);

	}
	public void tick(){
		setY(getY()-15);
		setX(getX()+2);
	}

}
