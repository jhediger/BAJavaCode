package com.ba.marketUI.client.introductionPages;

import java.io.IOException;
import java.util.Hashtable;

import com.ba.marketUI.client.ComClientInterface;
import com.ba.marketUI.client.ComClientInterfaceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;


public class WriterTimeSaver {

	private ComClientInterfaceAsync dataStoreService = (ComClientInterfaceAsync) GWT
			.create(ComClientInterface.class);

	private Hashtable<String,String> messages= new Hashtable<String,String>();
	
	private static final int REFRESH_INTERVAL = 1000; // ms
	
	
	
	public void addMessage(String fileName, String message){
		if(messages.containsKey(fileName)){
			String m= messages.get(fileName) + "/n"+message;
			messages.remove(fileName);
			messages.put(fileName, m);
		}
		else{
			messages.put(fileName, message);
		}
		
	}
	
	public void writeToFile(){
		for(String g:messages.keySet()){
			try {
				dataStoreService.myMethod(messages.get(g),g,
						new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								// Window.alert("Fehler 1: RPC to sendEmail() failed.");

							}

							@Override
							public void onSuccess(Void result) {
								// Window.alert("RPC to sendEmail() succed.");

							}

						});
			} catch (IOException e) {
				// Window.alert("Fehler 2: RPC to sendEmail() failed.");
				// e.printStackTrace();
			}
		}
	}

	public void setTimeOverall() {
		// Setup timer to refresh automatically.
				Timer refreshTimer = new Timer() {

					@Override
					public void run() {
					}
				};

				refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
		
	}

}
