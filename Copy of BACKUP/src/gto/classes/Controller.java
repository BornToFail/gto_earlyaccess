package gto.classes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

public class Controller {
	
	static ArrayList<EnemyBullet> eprojectiles = new ArrayList<EnemyBullet>();
	static ArrayList<PlayerBullet> pprojectiles = new ArrayList<PlayerBullet>();

	Bullet TempBullet;
	
	Game game;
	
	public Controller(Game game){
		this.game = game;
		
		
	}
	
	public void tick(){
		for (int i = 0; i<eprojectiles.size(); i++){
			TempBullet = eprojectiles.get(i);

			//destroys bullet once its off the screen
			if(TempBullet.getY() <0){
				removeBullet(TempBullet);
			}

			TempBullet.tick();
		}
		for (int i = 0; i<pprojectiles.size(); i++){
			TempBullet = pprojectiles.get(i);

			//destroys bullet once its off the screen
			if(TempBullet.getY() <0){
				removeBullet(TempBullet);
			}

			TempBullet.tick();
		}
	}

	public void render (Graphics g){
		for (int i = 0; i<eprojectiles.size(); i++){
			TempBullet = eprojectiles.get(i);
			
			TempBullet.render(g);
		}
		for (int i = 0; i<pprojectiles.size(); i++){
			TempBullet = pprojectiles.get(i);
			
			TempBullet.render(g);
		}
	}
	
	public void addBullet (Bullet block){
		eprojectiles.add((EnemyBullet) block);
		pprojectiles.add((PlayerBullet) block);
	}
	public void removeBullet(Bullet block){
		eprojectiles.remove(block);
		pprojectiles.remove(block);

	}
	
	
	
}
