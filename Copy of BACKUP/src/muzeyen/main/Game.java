package muzeyen.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends Canvas implements Runnable {

	//standard JFrame dimensions

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH/12*9;
	public static final int SCALE = 2;
	public static final int HUDX = WIDTH * SCALE - 140;
	public static final int GAME_SIZE = HUDX-20;
	/**
	 * The title of the program which will display on the top bar.
	 */
	public final String TITLE = "Grand Theft Otto"; //title
	/**
	 * The difficulty: 0 is the default. 
	 * 0 = Medium
	 * 2 = Hard
	 * 3 = Insanity
	 */
	public static int difficulty = 0;
	/**
	 * A variable to keep the time updated in the HUD
	 */
	public static int hudTimer = 0;
	private boolean running = false;
	/**
	 * The spawnrate of the enemies that allows more enemies to be changed
	 * as time passes.
	 */
	public static int spawnRate = 5;
	private Thread thread;
	private Menu menu;
	/**
	 * A boolean to check whether the game is paused
	 */
	static boolean paused = false;
	/**
	 * A boolean to check if there is a cooldown in effect for the "X button attack"
	 */
	boolean stopSpray = true;
	/**
	 * The cooldown timer for the X attack
	 */
	long timeofX = 0;
	/**
	 * FPS allows the game to store the Frames Per Second
	 */
	public static int FPS;
	/**
	 * A boolean to keep track of whether the user toggled sounds on or off. They are on by default
	 */
	public static boolean sounds = true;
	/**
	 * A boolean to keep track of whether the user toggled music on or off. They are on by default
	 */
	public static boolean music = true;
	/**
	 * A random number generator to aid enemy spawning
	 */
	static Random generator = new Random();
	/**
	 * The starting X coordinate of the games background to allow scrolling.
	 */
	int bg_X = -1000; 
	/**
	 * Setting the random generator to allow enemy spawning
	 */
	static int randSpawn = generator.nextInt(200)+ 1;
	/**
	 * Each state corresponds with a different part of the game. The game often checks for
	 * states to see what it should be doing.
	 */
	public static enum STATE{
		/**
		 * The main menu state, allowing the main menu to be shown
		 */
		MENU,
		/**
		 * The character selection state
		 */
		SELECT, 
		/**
		 * The Gamemode selection state
		 */
		GMSELECT,
		/**
		 * A cutscene state
		 */
		CUTSCENE_1,
		/**
		 * The settings state
		 */
		SETTINGS,
		/**
		 * The actual and core game state
		 */
		GAME,
		/**
		 * The paused game state
		 */
		PAUSE, 
		/**
		 * The gameover state
		 */
		GAMEOVER,
		/**
		 * An error display state
		 */
		G_ERROR
	}

	public static STATE State = STATE.MENU;

	private static BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB); //buffers window

	private static BufferedImage spriteSheet = null;
	private static BufferedImage spriteSheetK = null;
	private static BufferedImage background = null;
	private static BufferedImage menuBG_1 = null;
	private static BufferedImage menuBG_2 = null;
	private static BufferedImage fennelSplash = null;
	private static BufferedImage blobSplash = null;
	private static BufferedImage pauseOverlay = null;
	private static BufferedImage selectionBG = null;
	private static BufferedImage menuButton = null;
	private static BufferedImage replayButton = null;
	private static BufferedImage quitButton = null;
	private static BufferedImage ottoSplash_2 = null;
	private static BufferedImage gameoverText = null;
	private static BufferedImage highScoresBG = null;
	private static BufferedImage highScoresTitle= null;
	private static BufferedImage clouds = null;
	
	public static Boss b;
	public int cloudsX = 10;
	public int cloudsY= 0;
	private String[] tempScore = new String[5];
	private int lastScore;
	private BufferedImage hudRight = null;
	boolean shooting = false;
	public static boolean konami = false;
	int[] sequence = {38, 38, 40, 40, 37, 39, 37, 39, 66, 65};
	int currentButton = 0;
	private ImageIcon ottoSplash;
	public static Player p;
	static Controller c;

	private Controller testC;
	/**
	 * Creates an arraylist of highscores
	 */
	ArrayList<HighScore> gtoHighScores = new ArrayList<HighScore>(5);

	/**
	 * Creates an ArrayList of enemies that won't spawn in bullets
	 */
	static ArrayList<Enemy> passiveSpawner = new ArrayList<Enemy>();
	/**
	 * Creates an ArrayList of enemies that will spawn in bullets
	 */
	static ArrayList<Enemy> aggressiveSpawner = new ArrayList<Enemy>();

	//initialize
	/**
	 * Initializes the program
	 */
	public void init(){
		requestFocus(); //makes it so user doesn't have to press game to begin playing when it launces
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			spriteSheet = loader.loadImage("/sprite_sheet_new.png");
			spriteSheetK = loader.loadImage("/sprite_sheet.png");
			background = loader.loadImage("/background.png");
			menuBG_1 = loader.loadImage("/menuBackground.png");
			menuBG_2 = loader.loadImage("/konamiBackground_1.png");
			selectionBG = loader.loadImage("/selectionBackground_1.png");
			fennelSplash = loader.loadImage("/fennel.png");
			blobSplash = loader.loadImage("/blobFullHealth.png");
			pauseOverlay = loader.loadImage("/pauseOverlay.png");
			hudRight = loader.loadImage("/HUD_Side_Display.png");
			ottoSplash_2 = loader.loadImage("/ottoSplash.png");
			clouds = loader.loadImage("/clouds.png");

			//gameover images
			menuButton = loader.loadImage("/menuButton.png");// The play button
			replayButton = loader.loadImage("/replayBtn.png");
			quitButton = loader.loadImage("/quitButton.png");
			gameoverText = loader.loadImage("/gameoverTitle.png");
			highScoresBG = loader.loadImage("/highScoresBG.png");
			highScoresTitle = loader.loadImage("/highScoresTitle.png");


		}catch(IOException e){
			Game.State = Game.STATE.G_ERROR;
			e.printStackTrace();	
		}
		try{
			BufferedReader reader = new BufferedReader(new FileReader("res/highscore.txt"));
			String text = null;
			while ((text = reader.readLine())!= null){
				tempScore = text.split(" ");
				gtoHighScores.add(new HighScore(Integer.parseInt(tempScore[0]),tempScore[1],Integer.parseInt(tempScore[2])));
			}
		}
		catch (Exception e){
			Game.State = Game.STATE.G_ERROR;
			System.out.println(e.getMessage());	
		}
		ottoSplash = new ImageIcon(getClass().getResource("/otto.gif"));

		addKeyListener(new KeyInput(this));

		p = new Player(300,400, 1 ,this); //initializes player with x-cord and y-cord 200 and the state of the player sprite\
		c = new Controller(this);
		b = new Boss(0, 0, 1, 1, 0, 0);
		testC = new Controller(this);
		menu = new Menu();
		Music.playMenuMusic();
		fennelSplash.getScaledInstance(fennelSplash.getWidth()/3, fennelSplash.getHeight()/3, Image.SCALE_DEFAULT);
		this.addMouseListener(new MouseInput());

	}


	/**
	 * Starts up an initilized thread
	 */
	private synchronized void start(){
		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private synchronized void stop(){  //sync deals with threads
		if(!running)
			return;

		running = false;
		try {
			thread.join(); //joins all threads together and waits for them to terminate
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	/**
	 * Constantly checks the players lives so that the game will end if the player loses
	 * all of their lives. 
	 * If the player does die, it checks if they got a highscore then prompts them to 
	 * enter a name to save their score under.
	 */
	public void checkLives(){
		if (Player.lives == 0){
			lastScore = HUD.score;
			for (int i = gtoHighScores.size()-1; i>=0 ;i--){
				if(HUD.score > gtoHighScores.get(i).getsavedScore()){
					addHighScore();
					break;
				}
			}
			for(int i = 0; i < Controller.eprojectiles.size(); i++){
				Controller.eprojectiles.remove(i);
			}
			for(int i = 0; i < Controller.pprojectiles.size(); i++){
				Controller.pprojectiles.remove(i);
			}

			Game.State = Game.STATE.GAMEOVER;
			setDifficulty();
			Player.blinking = false;
			Game.p.setX(300);
			Game.p.setY(400);
		}
	}
	/**
	 * Lets the game spawn in enemies when called.
	 * Adds the enemies to the passiveSpawner or aggressiveSpawner arraylists
	 */
	public static void genericEnemyBehaviour(){
		if(State == STATE.GAME ){
			for (int i=0;i<spawnRate;i++){	
				randSpawn = generator.nextInt(480);
				passiveSpawner.add(new PassiveEnemy(randSpawn,0,generator.nextInt(7)-3,generator.nextInt(3)+1,GAME_SIZE,HEIGHT));
				//				passiveSpawner.get(i).setxSpeed(Math.random()*16-8);
				//				passiveSpawner.get(i).setySpeed(2);
			}
			for (int j = 0; j < spawnRate;j++){
				aggressiveSpawner.add(new AttackingEnemy(randSpawn,0,generator.nextInt(7)-3,generator.nextInt(3)+1,GAME_SIZE,HEIGHT));

			}
		}

	}
	/**
	 * Spawns in the boss
	 */
	public void BossBehaviour(){
		Boss.GoBoss(); //let it rip
			
	}
	/**
	 * Allows the game to check the player-chosen difficulty and sets multiple values including:
	 * -The enemy spawnRate
	 * -The player's amount of bombs
	 * -The player's amount of lives
	 */
	public static void setDifficulty(){
		if (difficulty == 0){

			spawnRate =1;
		}
		else if (difficulty == 1){
			spawnRate = 5;
			Player.bombs = 1;
		}
		else if (difficulty == 2){
			spawnRate = 5;
			Player.lives = 1;
			Player.bombs = 0;
			for(int i = 0; i < passiveSpawner.size(); i++){
				passiveSpawner.get(i).setxSpeed(passiveSpawner.get(i).getxSpeed() + 5);
			}
		}
	}


	/**
	 * When called, this method will allow new highscores to be added to the text file "HighScore"
	 * It will prompt the player to enter their name
	 * If the player tries to cancel the prompt, it will automatically save as "John_Doe"
	 * Otherwise, it will not allow the player to save a name with whitespace (as it messes up the
	 * saving file)
	 * Once it gets all the needed information it will save to "highscore.txt" with the values of
	 * the player's score, the players name and the character they played as.
	 */
	public void addHighScore(){
		gtoHighScores.remove(0);
		String name = "Player";
		boolean whitespace = false;
		do{
			whitespace = false;
			name = JOptionPane.showInputDialog(null,"Please enter your name: ","You have gotten a HighScore!", JOptionPane.ERROR_MESSAGE);
			if (name == null)
				name = "John_Doe";
			for (int i = 0; i < name.length(); i ++){
				if(Character.isWhitespace(name.charAt(i))){
					whitespace = true;
				}
			}
		}while (name.equals("")||whitespace == true);
		gtoHighScores.add(new HighScore(HUD.score,name,Player.selectedCharacter));
		Collections.sort(gtoHighScores);
		try{
			FileOutputStream fos = new FileOutputStream("res/highscore.txt");
			PrintStream fps = new PrintStream(fos);
			for(int i = 0; i < gtoHighScores.size();i++){
				fps.println(gtoHighScores.get(i).toString());
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	//whenever a thread is called, runnable will call run
	public void run(){
		//game loop - handles updates until game is exited 
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0; //updates 60 times every time it goes through
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();	
		BossBehaviour();
		if (Player.selectedCharacter == 2){
			p.setVelX(2);
			p.setVelY(2);
		}
		while (running){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if (delta >=1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (State == STATE.GAME){
				increaseDifficulty();
				if (HUD.score == 120){ //Boss appears after score hits 120
					Boss.GoBoss();
				}				
				p.playercollisionTest(passiveSpawner, Controller.eprojectiles);
				p.playercollisionTest2(aggressiveSpawner);

				for(int i = 0; i < aggressiveSpawner.size(); i++){
					if (((AttackingEnemy) aggressiveSpawner.get(i)).checkPosition(p)){
						if (difficulty == 3){
							Controller.eprojectiles.add(new PhasersOfDeath(aggressiveSpawner.get(i).getX(),aggressiveSpawner.get(i).getY(),this));
						}
						else{
							Controller.eprojectiles.add(new EnemyBullet(aggressiveSpawner.get(i).getX(),aggressiveSpawner.get(i).getY(),this));
							System.out.println("enemy is sentinent");
						}
					}
				}
				checkLives();
				for(int i = 0; i < passiveSpawner.size();i++){
					if(passiveSpawner.get(i).bulletcollisionTest(Controller.pprojectiles)){
						passiveSpawner.remove(i);
					}
				}
				for(int i = 0; i < aggressiveSpawner.size();i++){
					if(aggressiveSpawner.get(i).bulletcollisionTest(Controller.pprojectiles)){
						aggressiveSpawner.remove(i);
					}
				}
				//Problematic
				if (konami == true){
					System.out.println("enemies" + passiveSpawner.size());
				}
			}
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println(updates + " Ticks, FPS " + frames);
				FPS = frames;
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	/**
	 * Tick allows everything to update
	 */	
	private void tick(){
		if (State == STATE.GAME){
			p.tick();
			c.tick();
			hudTimer++;
			if ((hudTimer %300)==0){
				//	if (passiveSpawner.size() 10){
				genericEnemyBehaviour();
				//	}
			}

		}else if(State == STATE.PAUSE){
			//paused
		}

	}
	/**
	 *Checks if the time is an interval of 5 seconds, if it is, the amount of enemies spawned in increases
	 */
	private void increaseDifficulty(){
		if ((hudTimer%500)  == 0){
			if(difficulty == 0){
				spawnRate += 3;
				System.out.println("spawnrate"+spawnRate);
			}
			if(difficulty == 1){
				spawnRate += 5;
			}
			if(difficulty == 3){
				spawnRate += 10;
			}

		}
	}
	/**
	 * Render is a method to allow everything on-screen to, well, render.
	 * It will check what state the game is in and render accordingly.
	 */
	private void render (){

		BufferStrategy bs = this.getBufferStrategy(); //handles buffering behind the scenes, this refers to canvas
		if (bs == null){
			createBufferStrategy(3); //3 means triple buffering (loads 2 images in "stock/supply"), increases performance
			return;
		}

		Graphics g = bs.getDrawGraphics(); //draws out buffers
		//////////////////////////////
		if (State == STATE.GAME){
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			if (bg_X < 0){
				bg_X ++;
			}
			else{
				bg_X = -1000;
			}
			g.drawImage(background, 0, bg_X, null);
			g.drawImage(hudRight, HUDX, 0, null);
			HUD.render(g);
			for (int i = 0; i < passiveSpawner.size(); i++){
				passiveSpawner.get(i).render(g);
			}
			for (int i = 0; i < aggressiveSpawner.size(); i++){
				aggressiveSpawner.get(i).render(g);
			}
			for (int i = 0; i < Controller.pprojectiles.size(); i++){
				Controller.pprojectiles.get(i).render(g);
			}
			for (int i = 0; i < Controller.eprojectiles.size(); i++){
				Controller.eprojectiles.get(i).render(g);
			}
			p.render(g);
			c.render(g);
			b.render(g);
			testC.render(g);

		}else if (State == STATE.CUTSCENE_1){ //If the game is in the first cutscene state
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			g.drawImage(background, 0, 0, null);
			g.drawImage(hudRight, 500, 0, null);
			HUD.render(g);
			CutScenes.render(g);

			p.render(g);
			c.render(g);

		}else if (State == STATE.MENU){//If it's the MENU state, it will display the menu
			if(konami == false){
				g.drawImage(menuBG_1, 0, 0, null);

			}
			else{
				g.drawImage(menuBG_2, 0, 0, null);
			}
			if(konami == false){
				//				ottoSplash.paintIcon(this,g, 0, 0);// was supposed to be a gif but it fell through
				g.drawImage(ottoSplash_2, 0, 0, null);
				if (cloudsX > -1332){
					cloudsX--;
				}
				else{
					cloudsX= 666;
				}
				g.drawImage(clouds, cloudsX, cloudsY, null);
			}
			else{
				g.drawImage(blobSplash, 0, 325, null);
			}
			menu.render(g);
		}else if (State == STATE.SELECT){//If it's the MENU state, it will display the menu
			if (konami == true){
				Game.State = Game.STATE.GAME;
			}
			else{
				g.drawImage(selectionBG, 0, 0, null);
				try {
					CharacterSelection.render(g);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else if (State == STATE.GMSELECT){//If it's the MENU state, it will display the menu
			if (konami == true){
				Game.State = Game.STATE.GAME;
			}
			else{
				g.drawImage(selectionBG, 0, 0, null);
				try {
					GamemodeSelection.render(g);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}else if (State == STATE.SETTINGS){//If it's the MENU state, it will display the menu
			g.drawImage(selectionBG, 0, 0, null);
			Settings.preLoad();
			Settings.render(g);

		}else if (State == STATE.PAUSE){//If it's the MENU state, it will display the pause screen
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			g.drawImage(background, 0, 0, null);
			g.drawImage(pauseOverlay, 0, 0, null);
			Pause.render(g);
		}else if (State == STATE.GAMEOVER){
			Player.lives = 3;
			hudTimer = 0;
			Player.bombs = 3;
			HUD.score = 0;
			Font highScores = new Font("Century Gothic", Font.PLAIN, 15);
			g.setFont(highScores);
			g.drawImage(background, 0, 0, null);
			g.drawString("Game © Born To Fail 2016 ", 520, 475);
			g.drawImage(gameoverText, 30, 30, null);
			g.drawImage(highScoresTitle, 75, 100, null);
			g.drawImage(highScoresBG, 40, 150, null);
			g.drawImage(menuButton, 460, 300, null);
			g.drawImage(replayButton, 460, 200, null);
			g.drawImage(quitButton, 460, 400, null);
			//drawing highscores on screen
			g.drawString("Player Name",50,200);
			g.drawString("Character",160,200);
			g.drawString("Score",300,200);
			int x = 50, y = 250;
			for(int i = gtoHighScores.size()-1; i >= 0; i--){
				g.drawString(gtoHighScores.get(i).getsavedName(),x,y);
				x += 110;
				if(gtoHighScores.get(i).getSavedCharacter() ==1){
					g.drawString("Otto",x,y);
				}
				else if(gtoHighScores.get(i).getSavedCharacter() ==2){
					g.drawString("Fennel",x,y);
				}
				else{//(gtoHighScores.get(i).getSavedCharacter() ==3){
					g.drawString("Perditus",x,y);
				}

				x += 150;
				g.drawString(""+gtoHighScores.get(i).getsavedScore(), x, y);
				x = 50;
				y += 40;

			}
			g.drawString("Last Round's Score:" + lastScore, 460, 145);

		}else if (State == STATE.G_ERROR){
			ErrorScreen.render(g);
		}

		/////////////////////////////

		g.dispose();
		bs.show();
	}
	/**
	 * This handles all of the actions done with key presses. 
	 * For example, if the program is in the "GAME" state, it will allow the player press an arrow key to move.
	 */
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();


		if (State == STATE.GAME){
			/**
			 * If the player presses the right arrow key, the selected character will move right with 
			 * their specified velocity
			 */
			if (key == KeyEvent.VK_RIGHT){
				if(Player.selectedCharacter == 1){
					p.setVelX(5);	
				}
				if(Player.selectedCharacter == 2){
					p.setVelX(10);	
				}
				if(Player.selectedCharacter == 3){
					p.setVelX(3);	
				}
				Player.tiltRight();
			}
			/**
			 * If the player presses the left arrow key, the selected character will move left with 
			 * their specified velocity
			 */
			else if (key == KeyEvent.VK_LEFT){
				if(Player.selectedCharacter == 1){
					p.setVelX(-5);	
				}
				if(Player.selectedCharacter == 2){
					p.setVelX(-10);	
				}
				if(Player.selectedCharacter == 3){
					p.setVelX(-3);	
				}
				Player.tiltLeft();
			}
			/**
			 * If the player presses the down arrow key, the selected character will move down with
			 * their specified velocity
			 */
			else if (key == KeyEvent.VK_DOWN){
				if(Player.selectedCharacter == 1){
					p.setVelY(5);	
				}
				if(Player.selectedCharacter == 2){
					p.setVelY(10);	
				}
				if(Player.selectedCharacter == 3){
					p.setVelY(3);	
				}
			}
			/**
			 * If the player presses the up arrow key, the selected character will move up with
			 * their specified velocity
			 */
			else if (key == KeyEvent.VK_UP){
				if(Player.selectedCharacter == 1){
					p.setVelY(-5);	
				}
				if(Player.selectedCharacter == 2){
					p.setVelY(-10);	
				}
				if(Player.selectedCharacter == 3){
					p.setVelY(-3);	
				}
			}	
			/**
			 * If the player presses the space bar, the selected character will shoot with their
			 * specified bullets, one at a time.
			 */
			else if (key == KeyEvent.VK_SPACE && !shooting){
				if(Player.selectedCharacter == 1){
					Controller.pprojectiles.add(new OttoBullet(p.getX()-5,p.getY()-17, this));
					Controller.pprojectiles.add(new OttoBullet(p.getX()+5,p.getY()-17, this));

				}
				if(Player.selectedCharacter == 2){
					Controller.pprojectiles.add(new FennelBullet(p.getX()-5,p.getY()-17, this));
					Controller.pprojectiles.add(new FennelBullet(p.getX()+5,p.getY()-17, this));

				}
				if(Player.selectedCharacter == 3){
					Controller.pprojectiles.add(new PlayerBullet(p.getX(),p.getY(), this));
					Controller.pprojectiles.add(new PerditusBullet1(p.getX()-5,p.getY()-17, this));
					Controller.pprojectiles.add(new PerditusBullet2(p.getX()+5,p.getY()-17, this));

				}
				shooting = true;
				if (sounds == true){
					Music.playBullet();
				}
			}	
			/**
			 * If the player presses the "Z" key, a bomb will be used, killing all enemies on screen
			 */
			else if (key == KeyEvent.VK_Z){
				if (Player.bombs > 0){
					Player.bombs --;
					for (int i = 0; i <(passiveSpawner.size()); i++){
						passiveSpawner.remove(i);
					}
					if (sounds == true){
						Music.playBomb();
					}
				}
			}	
			/**
			 * If the player presses the "X" key, the selected character will shoot with their
			 * specified bullets, in a constant line.
			 */
			else if (key == KeyEvent.VK_X){
				stopSpray = false;
				timeofX = System.currentTimeMillis();
				if(Player.selectedCharacter == 1){
					Controller.pprojectiles.add(new OttoBullet(p.getX()-5,p.getY()+20, this));
					Controller.pprojectiles.add(new OttoBullet(p.getX()+5,p.getY()+20, this));

				}
				if(Player.selectedCharacter == 2){
					Controller.pprojectiles.add(new FennelBullet(p.getX()-5,p.getY()+20, this));
					Controller.pprojectiles.add(new FennelBullet(p.getX()+5,p.getY()+20, this));

				}
				if(Player.selectedCharacter == 3){
					Controller.pprojectiles.add(new PlayerBullet(p.getX(),p.getY()+20, this));
					Controller.pprojectiles.add(new PerditusBullet1(p.getX()-5,p.getY()+20, this));
					Controller.pprojectiles.add(new PerditusBullet2(p.getX()+5,p.getY()+20, this));

				}


			}

			/**
			 * If the player presses "escape" key or "backspace" key, the game will set state to 
			 * "PAUSE" and pause the game.
			 */
			else if (key == KeyEvent.VK_BACK_SPACE&&paused == false||key == KeyEvent.VK_ESCAPE &&paused == false){
				Game.State = Game.STATE.PAUSE;
				paused = true;
			}
		}



		/**
		 * Allows the game to test if the iconic Konami cheatcode (Up, up, down, down, left, right,
		 * left, right, B, A) is pressed, and modifies the game into it's "Konami" state if the user
		 * inputs the sprite correctly.
		 */
		if (State == STATE.MENU){
			System.out.println(key);

			if (key == sequence[currentButton]){
				currentButton++;
				if(currentButton == sequence.length){
					System.out.print("ACCESS GRANTED");
					this.konami = true;
					Player.setSprite();
					currentButton = 0; 
					this.spawnRate = 500;

				}
			}
			else{
				currentButton = 0;
			}

		}

	}



	public void keyReleased(KeyEvent e){


		int key = e.getKeyCode();

		if (State == STATE.GAME){


			if (key == KeyEvent.VK_RIGHT){
				p.setVelX(0);
				Player.setSprite();

			}
			else if (key == KeyEvent.VK_LEFT){
				p.setVelX(0);
				Player.setSprite();
			}
			else if (key == KeyEvent.VK_DOWN){
				p.setVelY(0);
			}
			else if (key == KeyEvent.VK_UP){
				p.setVelY(0);
			}
			else if (key == KeyEvent.VK_SPACE){
				shooting = false;
			}
		}
		else if (State == STATE.CUTSCENE_1){
			CutScenes.cutscene_F ++;
		}
	}

	public static void main(String args[]){
		Game game = new Game();

		//initializes a new dimension with a specified width and height
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();


	}

	//method that passes spriteSheet to player class
	public static BufferedImage getSpriteSheet(){
		return spriteSheet;
	}
	public static BufferedImage getSpriteSheetK(){
		return spriteSheetK;
	}

}