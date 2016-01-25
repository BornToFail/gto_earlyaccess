package gto.classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gto.classes.Game.STATE;
public class MouseInput implements MouseListener {
    static int mode = 0;
	@Override
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub commit
		int mx = e.getX();
		int my = e.getY();
		if (Game.State == STATE.SELECT){
			CharacterSelection.preLoad();
			if(mx >= 30 && mx <= 230){
				if(my >= 400 && my <= 450){
					//Pressed Back button
					Game.State = Game.STATE.MENU;
				}
			}
			if(mx >= 10 && mx <= 210){
				if(my >= 50 && my <= 350){
					Player.selectedCharacter = 1;
					Player.setSprite();
					Game.State = Game.STATE.GMSELECT;
				}
			}
			if(mx >= 225 && mx <= 425){//Tests if the user selects fennel
				if(my >= 50 && my <= 350){
					Player.selectedCharacter = 2;
					Player.setSprite();
					Game.State = Game.STATE.GMSELECT;
				}
			}
			if(mx >= 445 && mx <= 500){//Tests if the user selects Perditus 
				if(my >= 50 && my <= 350){
					Player.selectedCharacter = 3;
					Player.setSprite();
					Game.State = Game.STATE.GMSELECT;
				}
			}
		}
		if (Game.State == STATE.GMSELECT){
			if(mx >= 30 && mx <= 230){
				if(my >= 400 && my <= 450){
					//Pressed Back button
					Game.State = Game.STATE.SELECT;
				}
			}
			/**
			 * Checks if the player selects the medium survival button and sets the difficulty 
			 * accordingly
			 */
			if(mx >= 400 && mx <= 551){
				if(my >= 125 && my <= 146){
					//Pressed "MEDIUM SURVIVAL" button
					System.out.println("Iwork");
					Game.difficulty = 0;
					Game.setDifficulty();
					Game.State = Game.STATE.GAME;
					Music.playMenuMusic();
					HUD.preload();
					Music.playBackgroundMusic();
					Game.genericEnemyBehaviour();
				

				}
			}
			/**
			 * Checks if the player selects the medium story button and starts up the unfinished
			 * cutscene
			 */
			if(mx >= 100 && mx <= 151){
				if(my >= 125 && my <= 146){
					//Pressed "MEDIUM STORY" button
					mode = 1;
					HUD.preload();
					Game.State = Game.STATE.CUTSCENE_1;

				}
			}
			/**
			 * Checks if the player selects the hard survival button and sets the difficulty 
			 * accordingly
			 */
			if(mx >= 400 && mx <= 551){
				if(my >= 225 && my <= 246){
					System.out.println("Iwork");
					Game.difficulty = 1;
					Game.setDifficulty();
					Game.State = Game.STATE.GAME;
					Music.playMenuMusic();
					HUD.preload();
					Music.playBackgroundMusic();
					Game.genericEnemyBehaviour();
				}
			}
			if(mx >= 400 && mx <= 551){
				if(my >= 325 && my <= 346){
					//Pressed "Extreme SURVIVAL" button
					System.out.println("Iwork");
					Game.difficulty = 3;
					Game.setDifficulty();
					Game.State = Game.STATE.GAME;
					Music.playMenuMusic();
					HUD.preload();
					Music.playBackgroundMusic();
					Game.genericEnemyBehaviour();
				}
			}
		}

			
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
			
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	/**
	 *Checks if the mouse has been pressed over a variety of different situations
	 */
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		// TODO Auto-generated method stub

		if (Game.State == STATE.MENU){
			if(mx >= 460 && mx <= 700){
				if(my >= 200 && my <= 250){
					//Pressed Play button
					Game.State = Game.STATE.SELECT;
				}
			}
			if (Game.konami == false){
				if(mx >= 460 && mx <= 700){
					if(my >= 300 && my <= 350){
						//Pressed settings button
						Game.State = Game.STATE.SETTINGS;
					}
				}
				if(mx >= 460 && mx <= 700){
					if(my >= 400 && my <= 450){
						//Pressed quit button
						System.exit(1);
					}
				}
			}
		}
		if (Game.State == STATE.GAMEOVER){//If the game's state is "GAMEOVER"
			if(mx >= 460 && mx <= 700){//And if the cursor is in the correct x and y input
				if(my >= 200 && my <= 250){
					//Pressed Play button
					Game.State = Game.STATE.GAME;//it will allow you to play the game again.
				}
			}
			if (Game.konami == false){
				if(mx >= 460 && mx <= 700){
					if(my >= 300 && my <= 350){
						//Pressed settings button
						Game.State = Game.STATE.MENU;
					}
				}
				if(mx >= 460 && mx <= 700){
					if(my >= 400 && my <= 450){
						//Pressed quit button
						System.exit(1);
					}
				}
			}
		}
		
		if (Game.State == STATE.SETTINGS){
			if(mx >= 30 && mx <= 230){
				if(my >= 400 && my <= 450){
					//Pressed Play button
					Game.State = Game.STATE.MENU;
				}
			}
			if(mx >= 25 && mx <= 230){
				if(my >= 130 && my <= 175){
					//Toggled sound button
					if (Game.sounds == true){
						Game.sounds = false;
						Settings.update();
					}
					else{
						Game.sounds = true;
						Settings.update();
					}

				}
			}
			if(mx >= 25 && mx <= 230){
				if(my >= 90 && my <= 125){
					//Toggled music button
					if (Game.music == true){
						Game.music = false;
						Settings.update();
					}
					else{
						Game.music = true;
						Settings.update();
					}
			
				}
			}
		}
	
		if (Game.State == STATE.PAUSE){
				if(mx >= 250 && mx <= 400){
					if(my >= 300 && my <= 350){
						//Pressed Resume button button
						Game.paused = false;
						Game.State = Game.STATE.GAME;
					
					}
				}
				if(mx >= 250 && mx <= 400){
					if(my >= 400 && my <= 450){
						//Pressed Exit button
						System.exit(1);
					}
				}
			}
		}
		
	
	
		
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		int mx = e.getX();
		int my = e.getY();

	}
	
}
