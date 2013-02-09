package com.ba.marketUI.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
/**
 * 
 * @author Jessica Hediger
 *
 */
public class Game {
	// fix values for game initialization
	private int numPriceLevels;
	private int maxBudget;
	private boolean negativeValues;
	private boolean reOptimized;
	private int numCategories;
	private int maxSpeed;
	private double maxValue = 3.1;
	private int numOptions;
	private int maxRounds;
	private int numValueVariations;// each value is perturbed up/down with p:1/3

	private Random rand;

	// current_state
	private int currentBudget;
	private Double currentScore;
	private Integer currentCategory;
	private ArrayList<Integer> currentValueVariation = new ArrayList<Integer>();
	private int currentPriceLevel;

	

	// for UI
	private Vector<String> category = new Vector<String>();

	// speeds for all three categories speeds[0][0] = the speed for high
	// importance for the first button
	private double[][] speeds;

	// the prices depends on the speeds
	private List<ArrayList<ArrayList<Integer>>> prices;

	private boolean changingChoices;

	// list with all three categories and per category numOfChoices sublists
	// (choose one of them with p=1/3)
	private List<List<ArrayList<Double>>> values;
	
	public Game(int numCategories, int numOptions, int numPriceLevels,
			int maxBudget, int valueVariation, boolean negativeValues,
			boolean changingChoices, boolean reOptimized, int timeSteps) {
		rand = new Random();
		this.maxRounds = timeSteps;
		this.numCategories = numCategories;
		this.numOptions = numOptions;
		this.numPriceLevels = numPriceLevels;
		this.maxBudget = maxBudget;
		this.numValueVariations = valueVariation;
		// this.totalScore = 0;
		this.negativeValues = negativeValues;
		this.changingChoices = changingChoices;
		this.reOptimized = reOptimized;

		initialGame();

		this.maxSpeed = 1000;
		speeds = generateSpeeds();

		values = generateValues();
		makeValuesNegative();
	

		category.add("High"); // 0 is high importance
		category.add("Medium");
		category.add("Low");

		prices = generatePrices();

		// catProbabilities = generateProbabilities();
		// this.priceProbabilities = generatePriceProbabilities();
		// generatePerformanceProfiles();
		// setLambdaParameters();
	}

	public Game(int numCategories, int numOptions, int numPriceLevels,
			int maxBudget, int valueVariation, boolean negativeValues,
			boolean changingChoices, boolean reOptimized, double[][] speeds,
			int timeSteps) {
		rand = new Random();
		this.maxRounds = timeSteps;
		this.numCategories = numCategories;
		this.numOptions = numOptions;
		this.numPriceLevels = numPriceLevels;
		this.maxBudget = maxBudget;
		this.numValueVariations = valueVariation;
		this.negativeValues = negativeValues;
		this.changingChoices = changingChoices;
		this.reOptimized = reOptimized;

		initialGame();

		this.maxSpeed = 1000;
		this.speeds = speeds;

		values = generateValues();
		makeValuesNegative();
		// catProbabilities = generateProbabilities();
		// this.priceProbabilities = generatePriceProbabilities();
		// generatePerformanceProfiles();
		// setLambdaParameters();

		category.add("High"); // 0 is high importance
		category.add("Medium");
		category.add("Low");

		prices = generatePrices();

		// catProbabilities = generateProbabilities();
		// this.priceProbabilities = generatePriceProbabilities();
		// generatePerformanceProfiles();
		// setLambdaParameters();
	}

	private void initialGame() {
		currentCategory = rand.nextInt(numCategories);
		currentPriceLevel = rand.nextInt(numPriceLevels);
		currentValueVariation.clear();
		for(int i=0;i<numOptions;i++){
			currentValueVariation.add(rand.nextInt(numValueVariations));
		}
		

		//currentRound = 0;
		currentBudget = 0;
		currentScore = 0.0;

	}

	public String getCurrentCategoryAsString() {
		return category.get(currentCategory);
	}

	public double[] getCurrentSpeeds() {

		return speeds[currentCategory];
	}

	/**
	 * 
	 * @return for all choices it return a value, depending on a random
	 *         valueLevel
	 */
	public ArrayList<Double> getCurrentValues() {

		ArrayList<Double> val = new ArrayList<Double>();
		for (int i = 0; i < numOptions; i++) {
			val.add(values.get(currentCategory).get(i)
					.get(currentValueVariation.get(i)));
		}
		return val;
	}

	public ArrayList<Integer> getCurrentPrices() {

		return prices.get(currentPriceLevel).get(currentCategory);
	}

	public void changeState(int pushedbutton) {

		currentScore = currentScore
				+ values.get(currentCategory).get(pushedbutton)
						.get(currentValueVariation.get(pushedbutton));
		currentBudget += prices.get(currentPriceLevel).get(currentCategory)
				.get(pushedbutton);
		

		currentCategory = rand.nextInt(numCategories);
		currentPriceLevel = rand.nextInt(numPriceLevels);
		currentValueVariation.clear();
		for(int i=0;i<numOptions;i++){
			currentValueVariation.add(rand.nextInt(numValueVariations));
		}
	}

	/*public Integer getCurrentRound() {

		return currentRound +1;
	}*/

	public Integer getTokensLeft() {

		return currentBudget;
	}

	public Double getCurrentScore() {

		return currentScore;
	}

	/*public boolean notFinish() {
		if (maxRounds > currentRound) {
			return true;
		}
		return false;
	}*/

	public Integer getNumOfOptions() {
		return numOptions;
	}

	public List<Boolean> disableButton() {

		List<Boolean> disable_buttons = new ArrayList<Boolean>();

		int tokens_to_spend = maxBudget - currentBudget;

		for (int i = 0; i < numOptions; i++) {
			if (tokens_to_spend < prices.get(currentPriceLevel)
					.get(currentCategory).get(i)) {
				disable_buttons.add(true);
			} else {
				disable_buttons.add(false);
			}
		}

		return disable_buttons;
	}

	public void setNew() {
		//currentRound = 0;
		currentBudget = 0;
		currentScore = 0.0;

	}

	/**
	 * 
	 * @return a List with the three categories and per category numOfChoices
	 *         sublists [[[3.1, 2.6, 3.7], [2.0, 1.4, 2.0], [1.3, 0.8, 1.2],
	 *         [0.0, 0.0, 0.0]], [[1.8, 1.3, 1.3], .....]]
	 */
	private List<List<ArrayList<Double>>> generateValues() {

		List<List<ArrayList<Double>>> newValuesout = new ArrayList<List<ArrayList<Double>>>();

		for (int i = 0; i < numCategories; i++) {
			newValuesout.add(generateConcaveValues(i));
		}
		return newValuesout;
	}

	/**
	 * 
	 * @param currentCat
	 * @return List with three different Values(if value Variation ==1) for each
	 *         Option i.e. category low: [[0.9, 0.3, 0.3], [0.6, 0.2, 0.2],
	 *         [0.4, 0.2, 0.2], [0.0, 0.0, 0.0]]
	 */
	private List<ArrayList<Double>> generateConcaveValues(int currentCat) {
		double exponent = 0.5;
		List<ArrayList<Double>> concaveValues = new ArrayList<ArrayList<Double>>();

		double topValue = (numCategories - currentCat + 0.5)
				* (maxValue / (numCategories + 0.5));
		double multiplier = topValue / Math.pow(this.maxSpeed, exponent);

		// normal
		for (int i = 0; i < numOptions; i++) {

			ArrayList<Double> a = new ArrayList<Double>();
			a.add(round(multiplier * Math.pow(speeds[currentCat][i], exponent),
					1));
			concaveValues.add(a);
		}

		// now generate all speeds Values array
		 double[] allSpeedValues = new double[11];
		for (int i = 0; i < 11; i++) {
			double val = round((multiplier * Math.pow(100 * i, exponent)),1);
			//double valD = valInt / 10;
			allSpeedValues[i] = val;
		}

		if (this.numValueVariations == 3) {
			// down and up
			for (int i = 0; i < numOptions; i++) {
				double normal = concaveValues.get(i).get(0);
				int normalSpeed = (int) (speeds[currentCat][i]);
				int normalSpeedIndex = normalSpeed / 100;

				double normal_minus_1;
				double normal_plus_1;

				double diff_down;
				double diff_up;

				if (normalSpeed == 0) // bottom choice
				{
					normal_plus_1 = allSpeedValues[normalSpeedIndex + 1];
					normal_minus_1 = normal
							- (allSpeedValues[normalSpeedIndex + 1] - normal);
					normal_minus_1 = normal;
				} else if (normalSpeed == 1000) // top choice
				{
					normal_plus_1 = normal
							+ (normal - allSpeedValues[normalSpeedIndex - 1]);
					normal_minus_1 = allSpeedValues[normalSpeedIndex - 1];
				} else // in the middle
				{
					normal_plus_1 = allSpeedValues[normalSpeedIndex + 1];
					normal_minus_1 = allSpeedValues[normalSpeedIndex - 1];
				}

				diff_down = normal - normal_minus_1;
				diff_up = normal_plus_1 - normal;

				concaveValues.get(i).add(
						round(multiplier
								* Math.pow(speeds[currentCat][i], exponent)
								- (5.0 / 10.0) * diff_down, 1)); // down
				concaveValues.get(i).add(
						round(multiplier
								* Math.pow(speeds[currentCat][i], exponent)
								+ (5.0 / 10.0) * diff_up, 1)); // up

			}
		}
		return concaveValues;
	}

	/**
	 * make some of the values from values neg.
	 * 
	 */
	private void makeValuesNegative() {
		double minValue = 0;
		for (int c = 0; c < this.numCategories; c++) {
			for (int o = 0; o < this.numOptions; o++) {
				for (int i = 0; i < this.numValueVariations; i++) {
					if (negativeValues) {
						values.get(c)
								.get(o)
								.set(i,
										round(values.get(c).get(o).get(i) - 1.2
												* (5.0 / (c + 5.0)), 1));
						minValue = Math.min(minValue,
								values.get(c).get(o).get(i));
					}
				}
			}
		}
		//this.minimumValue = minValue;
	}

	private double[][] generateSpeeds() {
		double[][] newSpeeds = new double[this.numCategories][];

		if (!changingChoices) {
			for (int c = 0; c < this.numCategories; c++) {
				//not used for the experiment!
				if (this.numPriceLevels == 1) {
					switch (numOptions) {// fix prices, fixed choices, rational opt (7.1.2012)
					case 2:
						newSpeeds[c] = new double[] { 100, 0 };
						break;

					case 3:
						newSpeeds[c] = new double[] { 1000, 100, 0 };
						break;

					case 4:
						newSpeeds[c] = new double[] { 1000, 900, 100, 0 };
						break;

					case 5:
						newSpeeds[c] = new double[] { 1000, 900, 200, 100, 0 };
						break;

					case 6:
						newSpeeds[c] = new double[] { 1000, 900, 300, 200, 100,
								0 };
						break;
					}
				} else {// changing prices, fixed choices, rational opt (7.2.2012) ->used for the experiment
					 if (!this.reOptimized)
                     {

                         switch (numOptions)
                         {
                             case 2:
                                 newSpeeds[c] = new double[] { 300, 0 };
                                 break;

                             case 3:
                                 newSpeeds[c] = new double[] { 600, 200, 0 };
                                 break;

                             case 4:
                                 newSpeeds[c] = new double[] { 900, 300, 100, 0 }; //this one!
                                 break;

                             case 5:
                                 newSpeeds[c] = new double[] { 900, 400, 200, 100, 0 };
                                 break;

                             case 6:
                                 newSpeeds[c] = new double[] { 900, 400, 300, 200, 100, 0 };
                                 break;
                         }
                         /*
					if (!this.reOptimized) {

						switch (numOptions) {
						case 2:
							newSpeeds[c] = new double[] { 1000, 0 };
							break;

						case 3:
							newSpeeds[c] = new double[] { 1000, 300, 0 };
							break;

						case 4:
							newSpeeds[c] = new double[] { 1000, 500, 300, 0 }; // this
																				// one!
							break;

						case 5:
							newSpeeds[c] = new double[] { 1000, 900, 500, 300, 0 };
							break;

						case 6:
							newSpeeds[c] = new double[] { 1000, 900, 500, 300,
									100, 0 };
							break;
						}*/
					} else // re-optimized values, changing prices, fixed
							// choices
					{
						switch (numOptions) {
						case 2:
							newSpeeds[c] = new double[] { 100, 0 };
							break;

						case 3:
							newSpeeds[c] = new double[] { 600, 200, 0 };
							break;

						case 4:
							newSpeeds[c] = new double[] { 400, 300, 100, 0 }; // this
																				// one!
							break;

						case 5:
							newSpeeds[c] = new double[] { 400, 300, 200, 100, 0 };
							break;

						case 6:
							newSpeeds[c] = new double[] { 500, 400, 300, 200,
									100, 0 };
							break;
						}
					}
				}// end changing prices
			}
		} 
		//no speeds computed for changing choices...not used....
		/*else {// changing choices
			if (!this.reOptimized) {
				switch (numOptions) {
				case 2:
					newSpeeds[0] = new double[] { 700, 0 };
					newSpeeds[1] = new double[] { 500, 0 };
					newSpeeds[2] = new double[] { 300, 0 };
					break;

				case 3:
					newSpeeds[0] = new double[] { 600, 300, 0 };
					newSpeeds[1] = new double[] { 500, 200, 0 };
					newSpeeds[2] = new double[] { 400, 100, 0 };
					break;

				case 4: // this one!
					newSpeeds[0] = new double[] { 1000, 400, 200, 0 };
					newSpeeds[1] = new double[] { 600, 200, 100, 0 };
					newSpeeds[2] = new double[] { 400, 200, 100, 0 };
					break;

				case 5:
					newSpeeds[0] = new double[] { 1000, 800, 600, 400, 0 };
					newSpeeds[1] = new double[] { 800, 600, 400, 200, 0 };
					newSpeeds[2] = new double[] { 600, 500, 350, 150, 0 };
					break;

				case 6:
					newSpeeds[0] = new double[] { 1000, 800, 600, 400, 200, 0 };
					newSpeeds[1] = new double[] { 800, 600, 400, 200, 100, 0 };
					newSpeeds[2] = new double[] { 700, 500, 300, 150, 100, 0 };
					break;
				}
			} else {// re-optimized, changing choices, changing prices
				switch (numOptions) {
				case 2:
					newSpeeds[0] = new double[] { 700, 0 };
					newSpeeds[1] = new double[] { 500, 0 };
					newSpeeds[2] = new double[] { 300, 0 };
					break;

				case 3:
					newSpeeds[0] = new double[] { 600, 300, 0 };
					newSpeeds[1] = new double[] { 500, 200, 0 };
					newSpeeds[2] = new double[] { 400, 100, 0 };
					break;

				case 4: // this one!
					newSpeeds[0] = new double[] { 500, 400, 100, 0 };
					newSpeeds[1] = new double[] { 400, 300, 100, 0 };
					newSpeeds[2] = new double[] { 300, 200, 100, 0 };
					break;

				case 5:
					newSpeeds[0] = new double[] { 1000, 800, 600, 400, 0 };
					newSpeeds[1] = new double[] { 800, 600, 400, 200, 0 };
					newSpeeds[2] = new double[] { 600, 500, 350, 150, 0 };
					break;

				case 6:
					newSpeeds[0] = new double[] { 1000, 800, 600, 400, 200, 0 };
					newSpeeds[1] = new double[] { 800, 600, 400, 200, 100, 0 };
					newSpeeds[2] = new double[] { 700, 500, 300, 150, 100, 0 };
					break;
				}
			}
		}*/
		return newSpeeds;
	}

	/**
	 * 
	 * @return priceList ->
	 *         prices.get(currentPriceLevel).get(CurrentCategory).get(Button)
	 *         [[[10, 4, 2, 0], [6, 2, 1, 0], [4, 2, 1, 0]], [[20, 8, 4, 0],
	 *         [12, 4, 2, 0]..]]]
	 */
	private List<ArrayList<ArrayList<Integer>>> generatePrices() {
		List<ArrayList<ArrayList<Integer>>> priceVector = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for (int i = 0; i < numPriceLevels; i++) {
			ArrayList<ArrayList<Integer>> list1 = new ArrayList<ArrayList<Integer>>();
			for (int c = 0; c < this.numCategories; c++) {

				ArrayList<Integer> list2 = new ArrayList<Integer>();
				for (int j = 0; j < this.speeds[c].length; j++) {

					list2.add((int) (10 * (i + 1) * (speeds[c][j] / 1000.0)));
				}
				list1.add(list2);
			}
			priceVector.add(list1);
		}
		return priceVector;
	}

	/**
	 * 
	 * @param toRound
	 * @param digit
	 * @return return a double rounded to x digits, for 0 digits: 0.5 ->1 and
	 *         0.4 ->0
	 */
	private double round(double toRound, int digit) {

		boolean isneg=false;
		if(toRound<0){
			toRound=toRound*(-1);
			isneg=true;
		}
		digit++;
		int digits = (int) Math.pow(10, digit);
		int valInt = (int) (digits * toRound);

			// to get 0.5 ->1 and 0.4 ->0
			if (valInt % 10 >= 5) {
				valInt = valInt + 10;
			}
		

		valInt = valInt / 10;
		digit--;
		double valDouble = ((double) valInt) / Math.pow(10, digit);

		if(isneg){
			valDouble=valDouble*(-1);
		}
		return valDouble;

	}


	/**
	 * @return a List with the three categories and per category numOfChoices
	 *         sublists [[[3.1, 2.6, 3.7], [2.0, 1.4, 2.0], [1.3, 0.8, 1.2],
	 *         [0.0, 0.0, 0.0]], [[1.8, 1.3, 1.3], .....]]
	 */
	public List<List<ArrayList<Double>>> getallValue() {
		return values;
	}

	/**
	 * // the prices depends on the speeds private
	 * List<ArrayList<ArrayList<Integer>>> prices;
	 * 
	 * @return priceList ->
	 *         prices.get(currentPriceLevel).get(CurrentCategory).get(Button)
	 */
	public List<ArrayList<ArrayList<Integer>>> getallPrices() {
		return prices;
	}

	public void setCurrentScoreUP() {
		currentScore = currentScore + 0.0001;
		currentScore = round(currentScore, 1);

	}

	public double[][] getallSpeeds() {
		return speeds;
	}

	public ArrayList<Integer> getCurrentStat(int round) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(numOptions);
		round= round-1;
		list.add(round);
		list.add(maxBudget - currentBudget);
		list.add(currentCategory);
		list.addAll(currentValueVariation);
		list.add(currentPriceLevel);
		return list;
	}
	
	public Game(int numCategories, int numOptions, int numPriceLevels,
			int maxBudget, int valueVariation, boolean negativeValues,
			boolean reOptimized, double[][] speeds,
			int timeSteps) {
		rand = new Random();
		this.maxRounds = timeSteps;
		this.numCategories = numCategories;
		this.numOptions = numOptions;
		this.numPriceLevels = numPriceLevels;
		this.maxBudget = maxBudget;
		this.numValueVariations = valueVariation;
		this.negativeValues = negativeValues;
		this.reOptimized = reOptimized;

		initialGame();

		this.maxSpeed = 1000;
		this.speeds = speeds;

		values = generateValues();
		makeValuesNegative();
		// catProbabilities = generateProbabilities();
		// this.priceProbabilities = generatePriceProbabilities();
		// generatePerformanceProfiles();
		// setLambdaParameters();

		category.add("High"); // 0 is high importance
		category.add("Medium");
		category.add("Low");

		prices = generatePrices();

		// catProbabilities = generateProbabilities();
		// this.priceProbabilities = generatePriceProbabilities();
		// generatePerformanceProfiles();
		// setLambdaParameters();
	}

}