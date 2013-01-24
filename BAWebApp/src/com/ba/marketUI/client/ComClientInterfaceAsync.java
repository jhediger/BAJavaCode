package com.ba.marketUI.client;

import java.io.IOException;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * 
 * @author Jessica Hediger
 *
 */

public interface ComClientInterfaceAsync{
	public void myMethod(Boolean read, String message,String fileName, AsyncCallback<String> callback)
			throws IOException;	  
}