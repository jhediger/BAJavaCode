package com.ba.marketUI.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.ba.marketUI.client.numerics.Calcfc;
import com.ba.marketUI.client.numerics.Cobyla;
import com.ba.marketUI.client.numerics.CobylaExitStatus;
import com.ba.marketUI.client.pages.GameParameter;


public class ComputeLamdba {

	private static int numOfOptions;
	private static MDP mdpComp;

	// FIELDS
	private static double rhobeg = 0.1; // stepp of variable
	private static double rhoend = 0.1;// 1.0e-6;//5;//1.0e-6;//0;//100;//1.0e-6;
	private static int iprint = 2;// 1;
	private static int maxfun = 35000;// 300;//350000;
	private static double[] x = { 5.0 };// {20.0}; //startValue
	private static boolean tla;
	private static String fileName;

	// private static Hashtable<String, List<ArrayList<Double>>> tableData = new
	// Hashtable<String, List<ArrayList<Double>>>();
	private static Map<String, List<ArrayList<Double>>> tableDataSwitch = new HashMap<String, List<ArrayList<Double>>>();

	public ComputeLamdba(int numOfOptions, ArrayList<ArrayList<Integer>> data,
			MDP mdp, String fileName2, boolean tlamb) {
		ComputeLamdba.numOfOptions = numOfOptions;
		mdpComp = mdp;
		fileName = fileName2;
		tla= tlamb;
		
		rhobeg = 0.1; // stepp of variable
		rhoend = 0.1;// 1.0e-6;//5;//1.0e-6;//0;//100;//1.0e-6;
		iprint = 2;// 1;
		maxfun = 35000;// 300;//350000;
		x[0] = 5.0;// {20.0}; //startValue

		//computeMaxLambda(data);

	}

	public double computeMaxLambda(
			final ArrayList<ArrayList<Integer>> data) {

		Calcfc calcfc = new Calcfc() {

			private int timeSteps = 6;

			@Override
			public double Compute(int n, int m, double[] x, double[] con) {

				con[0] = x[0]; // x>0
				// x>0
				System.out.println(x[0]);
				if (x[0] < 0) {
					return 0;
				}

				double loglikelihood = 0.0;
				for (int u = 0; u < data.size(); u++) {
					int r = data.get(u).get(0);
					int bud = data.get(u).get(1);
					int cat = data.get(u).get(2);
					int vv = data.get(u).get(3);
					int pl = data.get(u).get(4);
					// TODO
					// int numOptions= data.get(u).get(5);
					int act = data.get(u).get(5);

					// ArrayList<Doouble> qValuesOfOptions= new
					// ArrayList<Double>();
					// qValuesOfOptions= getAllQValues(r, bud, cat, vv, pl);
					ArrayList<ArrayList<Double>> qValues = new ArrayList<ArrayList<Double>>();
					// TODO qValues= getAllQValues(getQValueCode(r, bud, cat,
					// vv, pl));
					// TODO r?
					String key = mdpComp.generateKey(timeSteps - 1 - r, bud,
							cat, vv, pl);

					if (tableDataSwitch.containsKey(key)) {
						qValues = (ArrayList<ArrayList<Double>>) tableDataSwitch.get(key);
					} else if (mdpComp.getTableAll().containsKey(key)) {
						// 20.2.13 neu: wenn Q1=10,Q2=8,Q3=6 dann
						// Q1=0;Q2=-2;Q3=-4
						qValues = (ArrayList<ArrayList<Double>>) mdpComp
								.getTableAll().get(key);
						int max = 0;
						for (int p = 0; p < qValues.size(); p++) {
							if (qValues.get(p).get(0) == 1.0) {
								max = p;
								break;
							}
						}
						double valMax = qValues.get(max).get(1);
						for (int w = 0; w < qValues.size(); w++) {
							double v1 = qValues.get(w).get(1);
							qValues.get(w).remove(1);
							//v1 = (-1) * (v1 - valMax) / 100;
							v1= (v1-valMax)/10;
							qValues.get(w).add(1, v1);
						}
						tableDataSwitch.put(key, qValues);

					} else if (fileName != null) {
						// System.out.println("lambdamitfile");
						int step = timeSteps - 1 - r;
						//FileReaderAll fileReader = new FileReaderAll(fileName,
							//	numOfOptions);
						WriterTimeSaver wts= new WriterTimeSaver();
						try {
							wts.readFromFile(fileName, 2);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						qValues = wts.getAll(mdpComp, step, bud, cat, vv, pl);

						/*try {
							BufferedWriter out = new BufferedWriter(
									new FileWriter("qValues3" + ".txt", true));
							out.newLine();
							out.write("option: " + act + " "
									+ qValues.toString());
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}*/

						// 20.2.13 neu: wenn Q1=10,Q2=8,Q3=6 dann
						// Q1=0;Q2=-2;Q3=-4

						int max = 0;
						for (int p = 0; p < qValues.size(); p++) {
							if (qValues.get(p).get(0) == 1.0) {
								max = p;
								break;
							}
						}
						double valMax = qValues.get(max).get(1);
						for (int w = 0; w < qValues.size(); w++) {
							double v1 = qValues.get(w).get(1);
							qValues.get(w).remove(1);
							//v1 = (-1) * (v1 - valMax) / 100;// 9
							// Turker: 9.575673587391975E-16
							// Seuken: F(l): 103.82928011395309
							// l: 9.992007221626409E-16
							// Eigenes: 8.604228440844963E

							 v1= (v1-valMax)/10; //18   -0.02   Turker[7-17]
							// v1= v1-valMax;
							qValues.get(w).add(1, v1);
						}
						tableDataSwitch.put(key, qValues);

						/*//TODO read from server!!
						try {
							BufferedWriter out = new BufferedWriter(
									new FileWriter("qValues22" + ".txt", true));
							out.newLine();
							out.write("option: " + act + " "
									+ qValues.toString());
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}*/
					}

					// System.out.println("data "+ data.get(u));
					// System.out.println("opt: "+ act+ " qValues: "+ qValues );

					// System.out.println("opt: "+ act+ " qValues22: "+ qValues
					// );
					// fehler? nicht e`^Qvalue
					// loglikelihood += x[0]
					double sum1 = x[0] * qValues.get(act).get(1);

					double sum = 0.0;
					for (int j = 0; j < numOfOptions; j++) {
						if (Double.valueOf(qValues.get(j).get(0)) != -3000.0) {
							sum += Math.pow(Math.E, x[0]
									* qValues.get(j).get(1));
						}
					}
					// loglikelihood -= Math.log(sum);
					sum1 -= Math.log(sum);

					loglikelihood += sum1;

				}

				return (-1) * loglikelihood;
			}
		};

		CobylaExitStatus result = Cobyla.FindMinimum(calcfc, 1, 1, x, rhobeg,
				rhoend, iprint, maxfun);

		// TODO important :D
		//System.out.println("resutl" + result);
		//System.out.println("F(l): " + Cobyla.getF());
		//System.out.println("l: " + Cobyla.getfirstX()[0]);
		return Cobyla.getfirstX()[0];

	}

}
