package com.ba.marketUI.client;

import java.io.IOException;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author Jessica Hediger
 *
 */

public interface ComClientInterfaceAsync {
	public void myMethod(String s, AsyncCallback<Void> callback)
			throws IOException;
}