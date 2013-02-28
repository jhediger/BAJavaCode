package com.ba.marketUI.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.ba.marketUI.client.pages.GameParameter;

public class MDP {

	private int maxBudget;
	private int numCategories; // high, medium, low
	private boolean negativeValues;

	private int timeSteps;
	private int numPriceLevels;
	private int numOptions;
	private int numValueVariation;

	private boolean changingChoices;
	private boolean reOptimized;

	private Game game;

	private ArrayList<ArrayList<Integer>> listValueVariation = new ArrayList<ArrayList<Integer>>();
	private Map<String, List<Double>> tablePV = new HashMap<String, List<Double>>();
	private HashMap<String, List<ArrayList<Double>>> tableAll = new HashMap<String, List<ArrayList<Double>>>();
	private HashMap<String, ArrayList<Double>> tableOpt = new HashMap<String, ArrayList<Double>>();
	private HashMap<String, Double> tableFutureValue = new HashMap<String, Double>();
	
	public void setGame(int cat, int opt, int pl, int vv, int budget,
			int steps, boolean cC, boolean rO, boolean nV) {
		numCategories = cat;
		numOptions = opt;
		numPriceLevels = pl;
		maxBudget = budget;
		numValueVariation = vv;
		negativeValues = true;
		changingChoices = cC;
		reOptimized = rO;
		timeSteps = steps;
		negativeValues = nV;

		tablePV.clear();

		initializedValueVariationCombination();

		game = new Game(cat, opt, pl, budget, vv, negativeValues,
				changingChoices, reOptimized, timeSteps, 1);

		setAllPriceValueCombinations();

	}
	
	/**
	 * The method produce all possible price-value combinations for the buttons
	 * 
	 * @return a [price,value] pair
	 */
	private void setAllPriceValueCombinations() {
		// priceList ->
		// prices.get(currentPriceLevel).get(CurrentCategory).get(Button)
		// values ->values.get(currentCategory).get(Button)
		// .get(currentValueVariation))
		for (int button = 0; button < numOptions; button++) {
			for (int category = 0; category < numCategories; category++) {
				for (int possibleValue = 0; possibleValue < (Math.pow(
						numValueVariation, numOptions)); possibleValue++) {
					for (int possiblePrice = 0; possiblePrice < numPriceLevels; possiblePrice++) {
						List<Double> pairs = new ArrayList<Double>();
						double d = game.getallPrices().get(possiblePrice)
								.get(category).get(button);
						pairs.add(d);
						pairs.add(game
								.getallValue()
								.get(category)
								.get(button)
								.get(listValueVariation.get(possibleValue).get(
										button)));
						tablePV.put(
								generateKey(button, category, possibleValue,
										possiblePrice), pairs);

					}
				}

			}
		}
	}

	
	public int getNumberOfVV(ArrayList<Integer> list) {

		for (int i = 0; i < listValueVariation.size(); i++) {
			List<Integer> temp = listValueVariation.get(i).subList(0,
					list.size());
			if (temp.equals(list)) {
				// System.out.println("getVV "+list + " i "+ i);
				return i;
			}
		}
		//System.out.println("Error while solving-vv not found");
		return 0;

	}
	
	
	public void initializedValueVariationCombination() {

		listValueVariation.clear();
		for (int b7 = 0; b7 < numValueVariation; b7++) {
			for (int b6 = 0; b6 < numValueVariation; b6++) {
				for (int b5 = 0; b5 < numValueVariation; b5++) {
					for (int b4 = 0; b4 < numValueVariation; b4++) {
						for (int b3 = 0; b3 < numValueVariation; b3++) {
							for (int b2 = 0; b2 < numValueVariation; b2++) {
								for (int b1 = 0; b1 < numValueVariation; b1++) {
									for (int b0 = 0; b0 < numValueVariation; b0++) {
										ArrayList<Integer> templist = new ArrayList<Integer>();
										templist.add(b0);
										templist.add(b1);
										templist.add(b2);
										templist.add(b3);
										templist.add(b4);
										templist.add(b5);
										templist.add(b6);
										templist.add(b7);
										listValueVariation.add(templist);
									}
								}
							}
						}
					}
				}
			}
		}

	}


	/**
	 * 
	 * @param currentRound
	 * @param currentBudget
	 * @param currentCategory
	 * @param currentVV
	 * @param currentPL
	 * @return
	 */
	public String generateKey(int currentRound, int currentBudget,
			int currentCategory, int currentVV, int currentPL) {
		String key = "round" + String.valueOf(currentRound) + "budg"
				+ currentBudget + "cat" + currentCategory + "vv"
				+ listValueVariation.get(currentVV) + "pl" + currentPL;
		// System.out.println(key);
		return key;
	}

	/**
	 * 
	 * @param button
	 * @param currentCategory
	 * @param currentVV
	 * @param currentPL
	 * @return key for priceValue-Table
	 */
	private String generateKey(int button, int currentCategory, int currentVV,
			int currentPL) {
		String key = "cat" + currentCategory + "vv"
				+ listValueVariation.get(currentVV) + "pl" + currentPL
				+ "button" + button;
		// System.out.println(key);
		return key;
	}

	public HashMap<String, List<ArrayList<Double>>> getTableAll() {
		return tableAll;
	}


	/*public void setInitialParametersforComputeOptimalButtons(int maxBudget2,
			int numCategories2, boolean negativeValues2, int timeSteps2,
			int numPriceLevels2, int valueVariation2, int numOptions,
			boolean changingChoices2, boolean reOptimized2) {

		maxBudget = maxBudget2;
		numCategories = numCategories2;
		negativeValues = negativeValues2;

		timeSteps = timeSteps2;
		numPriceLevels = numPriceLevels2;
		numValueVariation = valueVariation2;

		changingChoices = changingChoices2;
		reOptimized = reOptimized2;

		this.numOptions = numOptions;

		initializedValueVariationCombination();

	}*/
	
	/**
	 * r = 0 is the last round
	 */
	public void createTable() {
		getTableAll().clear();
		tableOpt.clear();
		for (int r = 0; r < timeSteps; r++) {
			for (int b = 0; b <= maxBudget; b++) {
				for (int cat = 0; cat < numCategories; cat++) {
					// for each state there are vv^buttons possibilities
					double sumV = Math.pow(numValueVariation, numOptions);
					for (int vv = 0; vv < sumV; vv++) {
						for (int pl = 0; pl < numPriceLevels; pl++) {
							tableAll.put(generateKey(r, b, cat, vv, pl),
									findmax(r, b, cat, vv, pl));
							// System.out.println("computing" + r + b + cat + vv
							// + pl);
						}

					}
				}
			}
		}
	}
	
	private List<ArrayList<Double>> findmax(int r, int bud, int c, int vv,
			int pl) {
		double p = 1.0;
		if (numPriceLevels > 0 && numValueVariation > 0) {
			p = (1.0 / (double) numCategories)
					* (1.0 / (double) numPriceLevels)
					* (1.0 / (double) (Math.pow(numValueVariation, numOptions)));
			// * (1.0 / (double)
			// numValueVariation);
			// System.out.println(numValueVariation+" "+numPriceLevels+" "+numValueVariation+" "+p);
		} else if (numPriceLevels > 0 && numValueVariation <= 0) {
			p = (1.0 / (double) numCategories)
					* (1.0 / (double) numPriceLevels);
		} else if (numValueVariation > 0 && numPriceLevels <= 0) {
			p = (1.0 / (double) numCategories)
					* (1.0 / (double) (Math.pow(numValueVariation, numOptions)));
		}

		List<ArrayList<Double>> listAll = new ArrayList<ArrayList<Double>>();

		double futurevalue = 0.0;
		double max_value = -1000;
		// we need this in the cases where there are more than one optional
		// value
		List<Integer> opt = new ArrayList<Integer>();

		for (int b = 0; b < numOptions; b++) {
			ArrayList<Double> pairs = new ArrayList<Double>();
			double valu = ((ArrayList<Double>) tablePV.get(generateKey(b, c,
					vv, pl))).get(1);
			double price = ((ArrayList<Double>) tablePV.get(generateKey(b, c,
					vv, pl))).get(0);
			futurevalue = 0.0;
			if (price <= bud) {

				if (r > 0) {
					int round = r - 1;
					int budget = Math.max(0, bud - (int) price);
					String key = "r" + r + "b" + budget;
					if (!tableFutureValue.containsKey(key)) {
						for (int val = 0; val < (Math.pow(numValueVariation,
								numOptions)); val++) {
							for (int prle = 0; prle < numPriceLevels; prle++) {
								for (int cat = 0; cat < numCategories; cat++) {
									futurevalue = futurevalue
											+ tableOpt.get(
													generateKey(round, budget,
															cat, val, prle))
													.get(1);
								}
							}
						}
						futurevalue = futurevalue * p;
						//System.out.println(futurevalue);
						tableFutureValue.put(key, futurevalue);
					}else{
						futurevalue= tableFutureValue.get(key);
					}

				}
				pairs.add(0.0);
				futurevalue = futurevalue + valu;
				// value = round(value, 3);
				pairs.add(futurevalue);
				if (r == 2) {
					// System.out.println(r+" "+
					// bud+" "+c+" "+vv+" "+pl+" "+"futureval"+futurevalue +
					// " "+valu);
				}

				if (futurevalue > max_value) {
					max_value = futurevalue;
					opt.clear();
					opt.add(b);
				} else if (futurevalue == max_value) {
					max_value = futurevalue;
					opt.add(b);
				}

			} else {
				pairs.add((double) -3000);
				pairs.add(valu);
			}
			listAll.add(pairs);
		}
		ArrayList<Double> optpair = new ArrayList<Double>();
		// max_value = round(max_value, 3);
		for (int o = 0; o < opt.size(); o++) {
			Double d = (double) opt.get(o);
			optpair.add(d);
			optpair.add(max_value);
			listAll.get(opt.get(o)).set(0, 1.0);
		}
		tableOpt.put(generateKey(r, bud, c, vv, pl), optpair);

		return listAll;
	}
}
