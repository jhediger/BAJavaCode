package com.ba.marketUI.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;



public class WriterTimeSaver {

	private ComClientInterfaceAsync dataStoreService = (ComClientInterfaceAsync) GWT
			.create(ComClientInterface.class);

	Map<String,String> map = new HashMap<String,String>();
	
	long startOverallTime;
	long startExp1Time;
	long startExp2Time;

	private long timeExp1;

	private long timeExp2;

	private long startExp4Time;

	private long startExp3Time;

	private long timeExp4;

	private long timeExp3;
	
	
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
	}
	
	public void setTimeExp2() {
		startExp2Time= System.currentTimeMillis();		
	}
	
	public void setTimeExp3() {
		startExp3Time= System.currentTimeMillis();		
	}
	
	public void setTimeExp4() {
		startExp4Time= System.currentTimeMillis();		
	}
	
	public int getTimeOverall(){
		long time= System.currentTimeMillis()-startOverallTime;
		//time/10^9;
		return (int) time;
	}
	
	public void stopTimeExp1(){
		timeExp1 = System.currentTimeMillis()-startExp1Time;
	}
	
	public void stopTimeExp2(){
		timeExp2 = System.currentTimeMillis()-startExp2Time;
	}
	
	public void stopTimeExp3(){
		timeExp3 = System.currentTimeMillis()-startExp3Time;
	}
	
	public void stopTimeExp4(){
		timeExp4 = System.currentTimeMillis()-startExp4Time;
	}
		
	public int getTimeExp1(){
		return (int) timeExp1;
	}
	
	public int getTimeExp2(){
		return (int) timeExp2;
	}
	
	public int getTimeExp3(){
		return (int) timeExp3;
	}
	
	public int getTimeExp4(){
		return (int) timeExp4;
	}
	
	public String getMessage(String key){
		return map.get(key);
	}
	

}
