package com.ba.marketUI.client.introductionPages;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PageSetUp1Experiment {

	private WriterTimeSaver w;

	public PageSetUp1Experiment(WriterTimeSaver w) {
		this.w = w;
	}

	public void loadPage() {
		final VerticalPanel welcomePanel = new VerticalPanel();
			
		//TODO experimentsetting description
		String titel = "In the next step you have to play four games with 6 rounds. After that the game stetting is changed a bit and you have to play another four games.";

	    
	    final Button button = new Button("Start part one");
		button.setStyleName("startGameButton");

		button.addClickListener(new ClickListener() {
		
			public void onClick(Widget sender) {
				w.setTimeExp1();
				PageExperiment p=new PageExperiment(w);
				p.loadPage();
			}
		});
		
		Label ti = new Label(titel);
		
		ti.addStyleName("titel");

				
		welcomePanel.add(ti);

		welcomePanel.add(button);
		
		welcomePanel.addStyleName("main_panel");
		

		RootPanel.get("mUI").clear();
		RootPanel.get("mUI").add(welcomePanel);
	
		
	}

}
