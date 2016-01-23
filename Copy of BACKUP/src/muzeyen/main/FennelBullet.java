/**
 * 
 */
package muzeyen.main;

/**
 * @author jam
 *
 */
public class FennelBullet extends Bullet {

	/**
	 * @param x
	 * @param y
	 * @param game
	 */
	public FennelBullet(double x, double y, Game game) {
		super(x, y, game);

	setSprite(5, Player.selectedCharacter, 32, 32);

	}
	public void tick(){
		setY(getY()-10);
	}

}
