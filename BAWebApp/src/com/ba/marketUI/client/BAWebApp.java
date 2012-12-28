package com.ba.marketUI.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;


/**
 * 
 * @author Jessica Hediger
 * 
 * Entry point classes define <code>onModuleLoad()</code>.
 *
 */
 
@SuppressWarnings({ "rawtypes"})
public class BAWebApp implements EntryPoint, ValueChangeHandler {

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {

		InterfaceFirst f = new InterfaceFirst();
		f.loadPage();

	}
	
	@Override
	public void onValueChange(ValueChangeEvent event) {
		
		
	}

}