package com.ba.marketUI.client;

import java.io.IOException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author Jessica Hediger
 * 
 */
@RemoteServiceRelativePath("comInterface")
public interface ComClientInterface extends RemoteService {
	public String myMethod(Boolean read, String message, String fileName) throws IOException;

}
