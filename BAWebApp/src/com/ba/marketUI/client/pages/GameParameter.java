package com.ba.marketUI.client.pages;

import com.ba.marketUI.client.Game;

/**
 * Parameters to initial games
 * 
 * @author JH
 */
public class GameParameter {
	
	// fix
	static Integer TimeSteps = 6;
	static Integer Tokens = 30;
	public static int Categories = 3; // high, medium, low
	static boolean NegativeValues = false;
	static boolean ChangingChoices = false; // if we have different choice sets
											// depending on the categories
	

	// variable
	static int NumPriceLevels = 3;// num of different prices
	static int ValueVariation = 3;
	public static int NumOptions = 4; // to decide how many buttons the game has
	
	static int MaxTimePerRound = 10;

	// for the experiment
	static int NumOfTestRounds = 0;
	//static int RoundsToPlay = 1; // how many games a player has to play
	public static int NumOfExperiment = 0;
	public static int MaxNumOfExperiment = 2;
	public static double FinalScore = 0;
	static boolean InSandbox = true;
	public static Game game;

	//behavioral optimization experiment
	static boolean ReOptimized = false; // if the user interface is optimized
	// depending on a lambda
	//public static boolean BehavioralOptimizationExperiment = false;
	public static double[][] Speeds;
	public static final String FileNameForInput = getFileName(NumOptions);
	
	// fileName
	public static String Worker = getFileNameWorker();
	public static String GameData = getFileNameGameData();
	public static String MTurk = getFileNameMTurk();
	
	private static String getFileName(int option) {
		if(option==2){
			return "OutputSamplefileTableALL32.txt";
		}else if(option==3){
			return "OutputSamplefileTableALL33.txt";
		}else if(option==4){
			return "OutputSamplefileTableALL34.txt";
		}else if(option==5){
			return "OutputSamplefileTableALL35.txt";
		}else{
			return "OutputSamplefileTableALL34.txt";
		}
		
	}

	private static String getFileNameMTurk() {
		if(ReOptimized==false){
			if(NumOptions==2){
				return "mTurkFalse2";
			}
			else if(NumOptions==3){
				return "mTurkFalse3";
			}
			else if(NumOptions==4){
				return "mTurkFalse4";
			}
			else if(NumOptions==5){
				return "mTurkFalse5";
			}else if(NumOptions==6){
				return "mTurkFalse6";
			}else if(NumOptions==7){
				return "mTurkFalse7";
			}else{
				return "mTurkFalse";
			}
		}else{
			return "mTurkTrue";
		}
	}

	private static String getFileNameGameData() {
		if(ReOptimized==false){
			if(NumOptions==2){
				return "gameFalse2";
			}
			else if(NumOptions==3){
				return "gameFalse3";
			}
			else if(NumOptions==4){
				return "gameFalse4";
			}
			else if(NumOptions==5){
				return "gameFalse5";
			}else if(NumOptions==6){
				return "gameFalse6";
			}else if(NumOptions==7){
				return "gameFalse7";
			}else{
				return "gameFalse";
			}
		}else{
			//TODO perhabs two files are better to find the difference
			return "gameTrue";
		}
	}

	private static String getFileNameWorker() {
		if(ReOptimized==false){
			if(NumOptions==2){
				return "workerFalse2";
			}
			else if(NumOptions==3){
				return "workerFalse3";
			}
			else if(NumOptions==4){
				return "workerFalse4";
			}
			else if(NumOptions==5){
				return "workerFalse5";
			}else if(NumOptions==6){
				return "workerFalse6";
			}else if(NumOptions==7){
				return "workerFalse7";
			}else{
				return "workerFalse";
			}
		}else{
			return "workerTrue";
		}
	}


}
