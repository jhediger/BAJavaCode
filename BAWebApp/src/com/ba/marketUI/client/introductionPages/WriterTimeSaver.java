package com.ba.marketUI.client.introductionPages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import com.ba.marketUI.client.ComClientInterface;
import com.ba.marketUI.client.ComClientInterfaceAsync;
import com.google.gwt.core.client.GWT;
//import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;



public class WriterTimeSaver {

	private ComClientInterfaceAsync dataStoreService = (ComClientInterfaceAsync) GWT
			.create(ComClientInterface.class);

	Map<String,String> map = new HashMap<String,String>();
	
	private static final int REFRESH_INTERVAL = 1000; // ms
	
	private int counterOfOverallTime=0;
	private int counterOfExp2Time=0;
	private int counterOfExp1Time=0;
	long startOverallTime;
	long startExp1Time;
	long startExp2Time;
	
	
	public void addMessage(String fileName, String message){
		if(map.containsKey(fileName)){
			String m= map.get(fileName) + "nnnnm" +message;
			map.remove(fileName);
			map.put(fileName, m);
		}
		else{
			map.put(fileName, message);
		}
		
	}
	
	public void writeToFile(){
		for(String g:map.keySet()){
			try {
				dataStoreService.myMethod(map.get(g),g,
						new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								 Window.alert("RPC failed.");

							}

							@Override
							public void onSuccess(Void result) {
								// Window.alert("RPC to sendEmail() succed.");

							}

						});
			} catch (IOException e) {
				 Window.alert("IOE");
				// e.printStackTrace();
			}
		}
	}

	public void setTimeOverall() {
		startOverallTime= System.currentTimeMillis();
		
		
	}
	
	public void setTimeExp1() {
		startExp1Time= System.currentTimeMillis();;
		
		// Setup timer to refresh automatically.
				Timer refreshTimer = new Timer() {

					@Override
					public void run() {
						counterOfExp1Time++;
					}
				};

				refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
		
	}
	
	public void setTimeExp2() {
		startExp2Time= System.currentTimeMillis();

		// Setup timer to refresh automatically.
				Timer refreshTimer = new Timer() {

					@Override
					public void run() {
						counterOfExp2Time++;
					}
				};

				refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
		
	}
	
	public int getTimeOverall(){
		long time= System.currentTimeMillis()-startOverallTime;
		//time/10^9;
		return (int) time;
	}
	
	public int getTimeExp1(){
		long time= System.currentTimeMillis()-startExp1Time;
		//time/10^9;
		return (int) time;
	}
	
	public int getTimeExp2(){
		long time= System.currentTimeMillis()-startExp2Time;
		//time/10^9;
		return (int) time;
	}
	
	

}
