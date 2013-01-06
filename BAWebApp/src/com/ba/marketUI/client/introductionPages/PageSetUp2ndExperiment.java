package com.ba.marketUI.client.introductionPages;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PageSetUp2ndExperiment {

		private WriterTimeSaver w;

		public PageSetUp2ndExperiment(WriterTimeSaver w) {
			this.w = w;
		}

		public void loadPage() {
			final VerticalPanel welcomePanel = new VerticalPanel();
				
			//TODO experimentsetting description
			String titel = "The first part is finish. In the second part the game setting is changed: ";
			//TODO neues setting beschreiben
			String text1 = "You have only 3 buttons in the following games";
			setParameterNew();
	
		    final Button button = new Button("Start part two");
			button.setStyleName("startGameButton");

			button.addClickListener(new ClickListener() {
			
				public void onClick(Widget sender) {
					w.setTimeExp2();
					PageExperiment p=new PageExperiment(w);
					p.loadPage();
				}
			});
			
			Label l_text1= new Label(text1);

			l_text1.addStyleName("textintro");
	
			
		
			Label ti = new Label(titel);
			
			ti.addStyleName("titel");

					
			welcomePanel.add(ti);
			welcomePanel.add(l_text1);
			welcomePanel.add(button);

			
			welcomePanel.addStyleName("main_panel");
			

			RootPanel.get("mUI").clear();
			RootPanel.get("mUI").add(welcomePanel);
			
		}

		private void setParameterNew() {
			GameParameter.NumOptions=3;
			
		}
	

}
