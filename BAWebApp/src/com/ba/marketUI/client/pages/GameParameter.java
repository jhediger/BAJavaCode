package com.ba.marketUI.client.pages;

import com.ba.marketUI.client.Game;

/**
 * Parameters to initial games
 * 
 * @author JH
 */
public class GameParameter {

	// Attention for the second part of the experiment they may be changed in
	// the class PageSetUp2ndExperiment

	
	// fix
	static Integer TimeSteps = 6;
	static Integer Tokens = 30;
	public static int Categories = 3; // high, medium, low
	static boolean NegativeValues = false;
	static boolean ChangingChoices = false; // if we have different choice sets
											// depending on the categories
	static boolean ReOptimized = false; // if the user interface is optimized
										// depending on a lambda

	// variable
	static int NumPriceLevels = 1;// num of different prices
	static int ValueVariation = 3;
	public static int NumOptions = 4; // to decide how many buttons the game has
	static boolean RoundTime = false;// to switch between the two following
										// modus ->false 240s modus
	static int MaxTimePerRound = 12;
	static int MaxTime = 240;// 7*6

	// for the experiment
	static int RoundsToPlay = 1; // how many games a player has to play
	public static int NumOfExperiment = 0;
	public static int MaxNumOfExperiment = 2;
	public static double FinalScore = 0;
	static boolean InSandbox = true;
	public static Game game;

	//behavioral optimization experiment
	public static boolean BehavioralOptimizationExperiment = true;
	public static double[][] Speeds;
	public static final String FileNameForInput = "OutputSamplefileTableALL33.txt";
	
	// fileName
	public static String Worker = "workerChoiceSet240secPl1";
	public static String GameData = "gameChoiceSet240secPl1";
	public static String MTurk = "mTurkChoiceSet240secPl1";


}
