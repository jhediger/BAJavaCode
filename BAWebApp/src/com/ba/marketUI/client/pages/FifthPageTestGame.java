package com.ba.marketUI.client.pages;

import com.ba.marketUI.client.WriterTimeSaver;
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
		final GamePanel g = new GamePanel(false,w, null);
		
		Button startNewGame = new Button("Start new game");
		final Button startExperiment = new Button("Start the experiment");
		startExperiment.setEnabled(false);
	
		startNewGame.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				g.startNewGame();
				gameCounter++;
				if(gameCounter>=GameParameter.NumOfTestRounds){
					startExperiment.setEnabled(true);
				}
			}
		});

		startExperiment.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				goToExperiment();
			}

			private void goToExperiment() {
				PageSetExperiment s = new PageSetExperiment(w);
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
