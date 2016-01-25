package gto.classes;

public class PhasersOfDeath extends EnemyBullet {

	public PhasersOfDeath(double x, double y, Game game) {
		super(x, y, game);
		setSprite(6, 1, 32, 32);
	}
	
	public void tick(){
		setY(getY()+1);
		setX(getX()+ 100*Math.sin(Math.toDegrees(getY())/4));
	}

}
