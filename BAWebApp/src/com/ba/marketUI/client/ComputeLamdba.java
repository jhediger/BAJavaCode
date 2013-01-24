package com.ba.marketUI.client;

import java.io.IOException;
import java.util.ArrayList;

import com.ba.marketUI.client.numerics.Calcfc;
import com.ba.marketUI.client.numerics.Cobyla;
import com.ba.marketUI.client.numerics.CobylaExitStatus;
import com.ba.marketUI.client.pages.GameParameter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ComputeLamdba implements Calcfc {

	// To get the computed Q-values
	private ComClientInterfaceAsync dataStoreService = (ComClientInterfaceAsync) GWT
			.create(ComClientInterface.class);
	
    private String datei;

	// FIELDS
	private static double rhobeg = 0.1; // stepp of variable
	private static double rhoend = 1.0e-6;// 5;//1.0e-6;//0;//100;//1.0e-6;
	private static int iprint = 1;// 1;
	private static int maxfun = 300;// 350000;
	private static double[] x = { 20.0 }; // startValue

	public ComputeLamdba(WriterTimeSaver w) {
		
		String m = w.getMessage(GameParameter.GameData);

		double lambda = computeLabmda(generateData(m));

		// depending on lambda choose one speed
		// near 9,6,3
		if (9 - lambda > 6 - lambda) {
			if (6 - lambda > 3 - lambda) {
				// toto get speeds
				// lambda=3
				for (int c = 0; c < GameParameter.Categories; c++) {
					GameParameter.Speeds[c] = new double[] { 1000, 800.400,
							200, 0 };
				}
			} else {
				// lambda=6
				for (int c = 0; c < GameParameter.Categories; c++) {
					GameParameter.Speeds[c] = new double[] { 1000, 800.400,
							200, 0 };
				}
			}
		} else {
			// lambda=9
			for (int c = 0; c < GameParameter.Categories; c++) {
				GameParameter.Speeds[c] = new double[] { 1000, 800.400, 200, 0 };
			}
		}

	}

	private void readFromFile() throws IOException {
		dataStoreService.myMethod(true, "", GameParameter.FileNameForInput, 
				new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						datei = "onFailure";
					}

					@Override
					public void onSuccess(String result) {
						datei = result;
					}

				});

	}
	
	public void writeToFile() throws IOException{
				try {
					dataStoreService.myMethod(false,"u","gzzg",
							new AsyncCallback<String>() {

								@Override
								public void onFailure(Throwable caught) {
									 Window.alert("RPC failed.");

								}

								@Override
								public void onSuccess(String result) {
									Window.alert("RPC to sendEmail() succed.");

								}

							});
				} catch (IOException e) {
					 Window.alert("IOE");
					// e.printStackTrace();
				}
			
		
	}


	private double computeLabmda(ArrayList<ArrayList<Integer>> generateData) {
		computeMaxLabda(generateData);
		return 0;
	}

	private ArrayList<ArrayList<Integer>> generateData(String m) {
		ArrayList<ArrayList<Integer>> data = new ArrayList<ArrayList<Integer>>();
		String t= datei;
		int startposition = m.indexOf("data", 0) + "data[".length();
		while (startposition > 10) {
			int round = 0;
			int tokens = 0;
			int cat = 0;
			int vv = 0;
			int pl = 0;
			int action = 0;
			try {
				round = Integer.valueOf(m.substring(startposition,
						m.indexOf(",", startposition)));
				startposition += 3;
				tokens = Integer.valueOf(m.substring(startposition,
						m.indexOf(",", startposition)));
				startposition += ((m.substring(startposition,
						m.indexOf(",", startposition)).length() + 2));
				cat = Integer.valueOf(m.substring(startposition,
						m.indexOf(",", startposition)));
				startposition += ((m.substring(startposition,
						m.indexOf(",", startposition)).length() + 2));
				vv = Integer.valueOf(m.substring(startposition,
						m.indexOf(",", startposition)));
				startposition += ((m.substring(startposition,
						m.indexOf(",", startposition)).length() + 2));
				pl = Integer.valueOf(m.substring(startposition,
						m.indexOf("]", startposition)));
				startposition = m.indexOf("option:", startposition)
						+ "option:".length();
				action = Integer.valueOf(m.substring(startposition,
						startposition + 1));
				startposition = m.indexOf("data", startposition)
						+ "data[".length();
			} catch (Exception e) {
				break;
			}
			ArrayList<Integer> l = new ArrayList<Integer>();
			l.add(round);
			l.add(tokens);
			l.add(cat);
			l.add(vv);
			l.add(pl);
			l.add(action);
			data.add(l);
		}
		return data;
	}

	public static void computeMaxLabda(final ArrayList<ArrayList<Integer>> data) {

		Calcfc calcfc = new Calcfc() {
			@Override
			public double Compute(int n, int m, double[] x, double[] con) {

				con[0] = x[0]; // x>0

				double loglikelihood = 0.0;

				for (int u = 0; u < data.size(); u++) {
					int r = data.get(u).get(0);
					int bud = data.get(u).get(1);
					int cat = data.get(u).get(2);
					int vv = data.get(u).get(3);
					int pl = data.get(u).get(4);
					int act = data.get(u).get(5);

					ArrayList<ArrayList<Double>> qValues = new ArrayList<ArrayList<Double>>();
					qValues = getAllQValues(getQValueCode(r, bud, cat, vv, pl));

					loglikelihood += x[0] * qValues.get(act).get(1);

					double sum = 0.0;
					for (int j = 0; j < GameParameter.NumOptions; j++) {
						if (Double.valueOf(qValues.get(j).get(0)) != -3000.0) {
							sum += Math.pow(Math.E, x[0]
									* qValues.get(j).get(1));
						}
					}
					loglikelihood -= Math.log(sum);

				}

				return (-1) * loglikelihood;
			}
		};

		// double[] x = {40.0};
		double rhobeg = 0.1;
		CobylaExitStatus result = Cobyla.FindMinimum(calcfc, 1, 1, x, rhobeg,
				rhoend, iprint, maxfun);
	}

	/**
	 * 
	 * @param r
	 * @param bud
	 * @param cat
	 * @param vv
	 * @param pl
	 * @return qValue for all ava. options -> attaintion: the order is not
	 *         correct if certain options are not ava.
	 */
	private static ArrayList<ArrayList<Double>> getAllQValues(String code) {

		ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();

	/*	int startposition = datei.indexOf(code) + code.length() + 2;

		for (int i = 0; i <GameParameter.NumOptions; i++) {
			ArrayList<Double> pairs = new ArrayList<Double>();
			String firstValue = datei.substring(startposition + 1,
					datei.indexOf(",", startposition));
			double secValue = Double.valueOf(datei.substring(
					datei.indexOf(",", startposition) + 2,
					datei.indexOf("]", startposition)));
			pairs.add(Double.valueOf(firstValue));
			pairs.add(secValue);
			startposition = datei.indexOf("[", startposition + 2);
			list.add(pairs);
		}
*/
		return null;

	}

	private static String getQValueCode(int r, int bud, int cat, int vv, int pl) {
		String code = "round" + r + "budg" + bud + "cat" + cat + "vv" + vv
				+ "pl" + pl;
		return code;
	}

	@Override
	public double Compute(int n, int m, double[] x, double[] con) {
		// TODO Auto-generated method stub
		return 0;
	}

}
