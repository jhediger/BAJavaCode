package com.ba.marketUI.client.introductionPages;

/**
 * Parameters to initial games
 *  
 *  @author JH
 */
public class GameParameter {
	
	//Attention for the second part of the experiment they may be changed in the class PageSetUp2ndExperiment

	// fix
	static Integer TimeSteps = 6;
	static Integer Tokens = 30;
	static int Categories = 3; // high, medium, low
	static boolean NegativeValues = true;

	static boolean ChangingChoices = false; // if we have different choice sets
												// depending on the categories
	static boolean ReOptimized = false; // if the user interface is optimized
											// depending on a lambda

	// variable
	static int NumPriceLevels = 3;// num of different prices
	static int ValueVariation = 3;
	static int NumOptions = 4; // to decide how many buttons the game has
	static boolean RoundTime = true;//to switch between the two following modus
	static int MaxTimePerRound = 7;
	static int MaxTime = 42;//7*6

	//for the experiment
	static int RoundsToPlay = 4; //how many games a player has to play
	public static int NumOfExperiment = 0;
	public static int MaxNumOfExperiment=2;
	public static double FinalScore = 0;
	static boolean InSandbox= true;
	
	//fileName
	public static String Worker= "worker";
	static String GameData= "game";
	public static String MTurk= "mTurk";
	
	
}
