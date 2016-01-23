package muzeyen.main;


public class EnemyBullet extends Bullet{
	
	
	
	public EnemyBullet(double x, double y, Game game){
		super(x, y, game);
		setSprite(6, 1, 32, 32);
	}
	
	public void tick(){
		setY(getY()+1);
		setX(getX()+ 6*(Math.sin(Math.toDegrees(getY())/10)));
		//setX(getX()+ 100*Math.sin(Math.toDegrees(getY())/4));
	}

}
