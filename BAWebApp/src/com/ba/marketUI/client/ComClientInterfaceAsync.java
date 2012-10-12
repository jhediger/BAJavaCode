package com.ba.marketUI.client;

import java.io.IOException;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ComClientInterfaceAsync {
	public void myMethod(String s, AsyncCallback<Void> callback)
			throws IOException;
}