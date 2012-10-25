package com.ba.marketUI.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

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

	private Random rand;

	// current_state
	private Integer currentRound;
	private int currentBudget;
	private Double currentScore;
	private Integer currentCategory;
	private int currentValueVariation;
	private int currentPriceLevel;

	// each value is perturbed up/down with p:1/3
	private int valueVariation;
	private int optionValueVariation;

	// for UI
	private Vector<String> category = new Vector<String>();

	// speeds for all three categories speeds[0][0] = the speed for high
	// importance for the first button
	private double[][] speeds;

	// the prices depends on the speeds
	private List<ArrayList<ArrayList<Integer>>> prices;

	// TODO what is changingChoices
	private boolean changingChoices = true;

	// TODO unknown why I use them...what is allSpeedValues
	private double[] allSpeedValues;
	private double minimumValue;

	// list with all three categories and per category numOfChoices sublists
	// (choose one of them with p=1/3)
	private List<List<ArrayList<Double>>> values;
	private int numValueVariations;

	public Game(int numCategories, int numOptions,
			int numPriceLevels, int maxBudget, int valueVariation,
			boolean negativeValues, boolean changingChoices, boolean reOptimized, int timeSteps) {
		rand = new Random();
		this.maxRounds = timeSteps;
		this.numCategories = numCategories;
		this.numOptions = numOptions;
		this.numPriceLevels = numPriceLevels;
		this.maxBudget = maxBudget;
		this.valueVariation = valueVariation;
		this.optionValueVariation = (int) Math.pow(3, this.valueVariation);
		if (valueVariation == 0)
			this.numValueVariations = 1;
		else
			numValueVariations = 3;
		// this.totalScore = 0;
		this.negativeValues = negativeValues;
		this.changingChoices = changingChoices;
		this.reOptimized = reOptimized;

		initialGame();

		speeds = generateSpeeds();
		this.maxSpeed = 1000;

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
		currentValueVariation = rand.nextInt(numValueVariations);

		currentRound = 0;
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
	// TODO all the same up/middle/down other each alone?
	public ArrayList<Double> getCurrentValues() {

		ArrayList<Double> val = new ArrayList<Double>();
		for (int i = 0; i < numOptions; i++) {
			val.add(values.get(currentCategory).get(i)
					.get(currentValueVariation));
		}
		return val;
	}

	public ArrayList<Integer> getCurrentPrices() {

		return prices.get(currentPriceLevel).get(currentCategory);
	}

	public void changeState(int pushedbutton) {

		currentScore += values.get(currentCategory).get(pushedbutton)
				.get(currentValueVariation);
		currentBudget += prices.get(currentPriceLevel).get(currentCategory)
				.get(pushedbutton);
		currentRound = currentRound + 1;

		currentCategory = rand.nextInt(numCategories);
		currentPriceLevel = rand.nextInt(numPriceLevels);
		currentValueVariation = rand.nextInt(numValueVariations);
	}

	public Integer getCurrentRound() {

		return currentRound;
	}

	public Integer getTokensLeft() {

		return currentBudget;
	}

	public Double getCurrentScore() {

		return currentScore;
	}

	public boolean notFinish() {
		if (currentRound < maxRounds) {
			return true;
		}
		return false;
	}

	public Integer getNumOfOptions() {
		return numOptions;
	}

	public List<Boolean> disableButton() {
		
		List<Boolean> disable_buttons= new ArrayList<Boolean>();
		
		int tokens_to_spend = maxBudget - currentBudget;

		for(int i=0;i<numOptions;i++){
			if (tokens_to_spend < prices.get(currentPriceLevel)
					.get(currentCategory).get(i)) {
				disable_buttons.add(true);
			}
			else{
				disable_buttons.add(false);
			}
		}

		return disable_buttons;
	}

	public void setNew() {
		currentRound = 0;
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
		allSpeedValues = new double[11];
		for (int i = 0; i < 11; i++) {
			int valInt = (int) (10 * multiplier * Math.pow(100 * i, exponent));
			double valD = valInt / 10;
			allSpeedValues[i] = valD;
		}

		if (this.valueVariation == 1) {
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
				for (int i = 0; i < this.optionValueVariation; i++) {
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
		this.minimumValue = minValue;
	}

	private double[][] generateSpeeds() {
		double[][] newSpeeds = new double[this.numCategories][];

		if (!changingChoices) {
			for (int c = 0; c < this.numCategories; c++) {
				if (this.numPriceLevels == 1) {
					switch (numOptions) {
					case 2:
						newSpeeds[c] = new double[] { 500, 0 };
						break;

					case 3:
						newSpeeds[c] = new double[] { 300, 200, 0 };
						break;

					case 4:
						newSpeeds[c] = new double[] { 400, 200, 100, 0 };
						break;

					case 5:
						newSpeeds[c] = new double[] { 400, 300, 200, 100, 0 };
						break;

					case 6:
						newSpeeds[c] = new double[] { 500, 400, 300, 200, 100,
								0 };
						break;
					}
				} else {// changing prices, fixed choices,
					if (!this.reOptimized) {

						switch (numOptions) {
						case 2:
							newSpeeds[c] = new double[] { 100, 0 };
							break;

						case 3:
							newSpeeds[c] = new double[] { 300, 200, 0 };
							break;

						case 4:
							newSpeeds[c] = new double[] { 900, 300, 100, 0 }; // this
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
					} else // re-optimized values, changing prices, fixed
							// choices
					{
						switch (numOptions) {
						case 2:
							newSpeeds[c] = new double[] { 100, 0 };
							break;

						case 3:
							newSpeeds[c] = new double[] { 200, 100, 0 };
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
		} else {// changing choices
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
		}
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
					if (numPriceLevels == 1)
						list2.add((int) (10 * (i + 2) * (speeds[c][j] / 1000.0)));
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

		int digits = (int) Math.pow(10, digit);
		int valInt = (int) (digits * toRound);

		// to get 0.5 ->1 and 0.4 ->0
		if (valInt % 10 >= 5) {
			valInt++;
		}
		double valDouble = ((double) valInt) / 10;

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

	public void setCurrentScore(double d) {
		currentScore= currentScore+d;
		
	}

}