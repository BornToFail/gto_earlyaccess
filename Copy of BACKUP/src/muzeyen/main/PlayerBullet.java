/**
 * 
 */
package muzeyen.main;

/**
 * @author jam
 *
 */
public class PlayerBullet extends Bullet {

	/**
	 * @param x
	 * @param y
	 * @param game
	 */
	public PlayerBullet(double x, double y, Game game) {
		super(x, y, game);
//		if (Game.konami == false){
			setSprite(5, Player.selectedCharacter, 32, 32);
//			}
//			else if (Game.konami == true){
//				image = ssk.grabImage(2, 1, 32, 32);
//			}
		// TODO Auto-generated constructor stub
	}
	public void tick(){
		setY(getY()-10);
	}

}
