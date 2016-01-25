package gto.classes;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URL;

import javax.sound.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
public class Music {
	static AudioInputStream ais;
	static AudioInputStream ais_B;
	static Clip clip;
	static Clip clip_B;
	static File bullet = new File("soundeffects/laser1.wav");
	static File death = new File("soundeffects/bullet_1.wav");
	static File bomb = new File("soundeffects/bomb.wav");

	static boolean playing = false;
	public Music(){


		//	public void playMenuMusic(){
		//		menuMusic = getAudioClip(getDocumentBase(), "/thesongofmypeople.wav");
		//		menuMusic.play();
		//	}


	}
	static void playMenuMusic() {
		if (playing == false){
			playing = true;
			try{
				File menuMusic = new File("music/menuMusic.wav");
				ais = AudioSystem.getAudioInputStream(menuMusic);
				clip= AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(menuMusic));
			}
			catch(Exception e){
				System.out.println(e);
			}


			AudioClip menuMusic;
			AudioClip backgroundMusic;


			try{
				clip.start();

			}
			catch(Exception e){
				System.out.println(e);
				System.out.println("It opens but wont play");
			}
		}
		else{
			playing = false;
			clip.stop();
		}
		
	}
	static void playBackgroundMusic() {
		try{
			File BGM = new File("music/timelapse.wav");
			ais = AudioSystem.getAudioInputStream(BGM);
			clip= AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(BGM));
		}
		catch(Exception e){
			System.out.println(e);
		}

		try{
			clip.start();

		}
		catch(Exception e){
			System.out.println(e);
			System.out.println("It opens but wont play");
		}
	}
	static void playBullet() {

		try{
			ais_B = AudioSystem.getAudioInputStream(bullet);
			clip_B= AudioSystem.getClip();
			clip_B.open(AudioSystem.getAudioInputStream(bullet));
		}
		catch(Exception e){
			System.out.println(e);
		}


		try{
			clip_B.start();

		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	public static void playEDeath() {
		try{
			ais = AudioSystem.getAudioInputStream(death);
			clip= AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(death));
		}
		catch(Exception e){
			System.out.println(e);
		}


		try{
			clip.start();

		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	public static void playBomb() {
		try{
			ais = AudioSystem.getAudioInputStream(bullet);
			clip= AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(bullet));
		}
		catch(Exception e){
			System.out.println(e);
		}


		try{
			clip_B.start();

		}
		catch(Exception e){
			System.out.println(e);
			System.out.println("It opens but wont play");
		}
	}
}

