package gto.classes;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class HighScore implements Comparable {
	 private int savedScore;
	 private String savedName;
	 private int savedCharacter;


	public int getsavedScore() {
		return savedScore;
	}
	public void setsavedScore(int savedScore) {
		this.savedScore = savedScore;
	}
	public String getsavedName() {
		return savedName;
	}
	public void setsavedName(String savedName) {
		this.savedName = savedName;
	}
	public int getSavedCharacter() {
		return savedCharacter;
	}
	public void setSavedCharacter(int savedCharacter) {
		this.savedCharacter = savedCharacter;
	}
	
	public HighScore(int score, String name, int character){
		this.setsavedScore(score);
		this.setSavedCharacter(character);
		this.setsavedName(name);
	}
//	public void testScores(int currentScore, String currentName){
//		for (int i = 0; i < savedScore.size();i++){
//			if(currentScore > savedScore.get(i)){
//				tempName = savedName.get(i);
//				tempScore = savedScore.get(i);
//				savedScore.remove(i);
//				savedName.remove(i);
//				savedScore.add(currentScore);
//				savedName.add(currentName);
//				currentScore = tempScore;
//				currentName = tempName;
//			}
//		}
//		//augh why
//	}
	@Override
	public int compareTo(Object arg0) {
		HighScore check = (HighScore)arg0;
		if (this.getsavedScore()>check.getsavedScore()) {return 1;}
		else if(this.getsavedScore()<check.getsavedScore()){return -1;}
		else{return 0;}
	}

	public String toString(){
		return savedScore+" "+savedName+" "+savedCharacter;
		
	}

}
