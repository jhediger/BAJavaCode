package com.ba.marketUI.client.introductionPages;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FifthPageTestGame {

	private WriterTimeSaver w;
	private int gameCounter=0;

	public FifthPageTestGame(WriterTimeSaver w) {
		this.w = w;
	}

	@SuppressWarnings("deprecation")
	public void loadPage() {
	
		VerticalPanel vp = new VerticalPanel();
		final GamePanel g = new GamePanel(false,w);
		
		Button startNewGame = new Button("Start new game");
		final Button startExperiment = new Button("Start the experiment");
		startExperiment.setEnabled(false);
	
		startNewGame.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				g.startNewGame();
				gameCounter++;
				if(gameCounter==2){
					startExperiment.setEnabled(true);
				}
			}
		});

		startExperiment.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				goToExperiment();
			}

			private void goToExperiment() {
				PageSetUp1Experiment s = new PageSetUp1Experiment(w);
				s.loadPage();
				
			}
		});
		
		vp.add(g.getPanel());
		vp.add(startNewGame);
		vp.add(startExperiment);
		
		vp.addStyleName("main_panel");
		
		RootPanel.get("mUI").clear();
		RootPanel.get("mUI").add(vp);
	}

}
