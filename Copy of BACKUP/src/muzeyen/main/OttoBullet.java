/**
 * 
 */
package muzeyen.main;

/**
 * @author jam
 *
 */
public class OttoBullet extends PlayerBullet {

	/**
	 * @param x
	 * @param y
	 * @param game
	 */
	public OttoBullet(double x, double y, Game game) {
		super(x, y, game);
		setSprite(5, Player.selectedCharacter, 32, 32);

		}
		public void tick(){
			setY(getY()-10);
		}

}
