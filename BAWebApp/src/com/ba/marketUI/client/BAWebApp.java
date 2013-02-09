package com.ba.marketUI.client;


import com.ba.marketUI.client.pages.FifthPageTestGame;
import com.ba.marketUI.client.pages.FirstPageAcceptTask;
import com.ba.marketUI.client.pages.FourthPageComprehensionQuestions;
import com.ba.marketUI.client.pages.GameParameter;
import com.ba.marketUI.client.pages.PageExperiment;
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

		FirstPageAcceptTask f = new FirstPageAcceptTask();
		f.loadPage();
	
		GameParameter.NumOptions= 3;
		GameParameter.ReOptimized=false;
		
		/*WriterTimeSaver w= new WriterTimeSaver();
		//FifthPageTestGame s=new FifthPageTestGame(w);
		PageExperiment s= new PageExperiment(w);
		s.loadPage();*/
	}
	
	@Override
	public void onValueChange(ValueChangeEvent event) {
		
		
	}

}