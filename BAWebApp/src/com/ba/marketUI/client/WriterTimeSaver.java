package com.ba.marketUI.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ba.marketUI.client.pages.GameParameter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class WriterTimeSaver {

	private ComClientInterfaceAsync dataStoreService = (ComClientInterfaceAsync) GWT
			.create(ComClientInterface.class);

	Map<String, String> map = new HashMap<String, String>();

	long startOverallTime;

	private String datei;

	private ArrayList<Double> scores = new ArrayList<Double>();

	private ArrayList<Long> times = new ArrayList<Long>();

	private long startExpTime;

	public void addMessage(String fileName, String message) {
		if (map.containsKey(fileName)) {
			String m = map.get(fileName) + "nnnnm" + message;
			map.remove(fileName);
			map.put(fileName, m);
		} else {
			map.put(fileName, message);
		}

	}

	public void writeToFile() {
		for (String g : map.keySet()) {
			try {
				dataStoreService.myMethod(false, map.get(g), g,
						new AsyncCallback<String>() {

							@Override
							public void onFailure(Throwable caught) {
								if(GameParameter.InSandbox){
									Window.alert("Save failed.");
								}

							}

							@Override
							public void onSuccess(String result) {
								if(GameParameter.InSandbox){
									Window.alert("Save succed.");
								}

							}

						});
				map.remove(g);
			} catch (IOException e) {
				//Window.alert("IOE");
				// e.printStackTrace();
			}
		}
	}

	public void setTimeOverall() {
		startOverallTime = System.currentTimeMillis();

	}

	public int getTimeOverall() {
		long time = System.currentTimeMillis() - startOverallTime;
		// time/10^9;
		return (int) time;
	}

	public void startTime() {
		startExpTime = System.currentTimeMillis();
	}

	public void stopTime() {
		times.add(System.currentTimeMillis() - startExpTime);
	}

	public int getTime(int exp) {
		return (int) ((long) times.get(exp));
	}

	public String getMessage(String key) {
		return map.get(key);
	}

	public void setInputParameter() {
		if (datei == null) {
			try {
				readFromFile(GameParameter.FileNameForInputParameter, 1);
			} catch (IOException e) {
				GameParameter.NumOptions = 4;
				GameParameter.ReOptimized = false;
				GameParameter.ComputeLambda = false;
				e.printStackTrace();
			}
			return;
		}
		String input = datei;
		// Window.alert(msg);
		int start = input.indexOf(GameParameter.numOfChoices)
				+ GameParameter.numOfChoices.length() + 1;
		GameParameter.NumOptions = Integer.valueOf(input.substring(start,
				input.indexOf(";", start)));
		start = input.indexOf(GameParameter.reOptimized)
				+ GameParameter.reOptimized.length() + 1;
		GameParameter.ReOptimized = Boolean.valueOf(input.substring(start,
				input.indexOf(";", start)));
		start = input.indexOf(GameParameter.computeLambda)
				+ GameParameter.computeLambda.length() + 1;
		GameParameter.ComputeLambda = Boolean.valueOf(input.substring(start,
				input.indexOf(";", start)));
		start = input.indexOf(GameParameter.inSandobx)
				+ GameParameter.inSandobx.length() + 1;
		GameParameter.InSandbox= Boolean.valueOf(input.substring(start,
				input.indexOf(";", start)));
		start = input.indexOf(GameParameter.save)
				+ GameParameter.save.length() + 1;
		//GameParameter.path= "/home/user/hediger/tomcat/";//input.substring(start,input.indexOf(";",start));
		
		if(GameParameter.InSandbox){
			Window.alert(datei+" "+GameParameter.path+" "+GameParameter.NumOptions+" "+GameParameter.InSandbox);
		}else if(!GameParameter.InSandbox){
			//Window.alert("Welcome"+GameParameter.path);
		}
		datei=null;
		

	}

	public void readFromFile(String fileName, final int numFunction)
			throws IOException {
		dataStoreService.myMethod(true, "", fileName,
				new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
					//	Window.alert("RPC failed.");
						datei = "onFailure";
					}

					@Override
					public void onSuccess(String result) {
					//	Window.alert("RPC to sendEmail() succed." + result);
						datei = result;
						if (numFunction == 1) {
							setInputParameter();
						}else if(numFunction==2){
							computeLambda();
						}
					}

				});

		// Window.alert("d2"+datei);
		// return datei;
	}

	public void setScoreExp(double score) {
		scores.add(score);

	}

	public double getFinalScore() {
		double sum = 0;
		for (Double s : scores) {
			sum += s;
		}
		return sum;
	}

	public void computeLambda() {
		if(datei==null){
		try {
			readFromFile(GameParameter.FileNameForInput,2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		//TODO computation
		
	}

}
