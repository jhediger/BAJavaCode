package com.ba.marketUI.client.pages;

import com.ba.marketUI.client.ComputeLamdba;
import com.ba.marketUI.client.WriterTimeSaver;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PageExperiment {

	private WriterTimeSaver w;

	public PageExperiment(WriterTimeSaver w) {
		this.w = w;
	}

	public void loadPage() {
		// TODO
		// ComputeLamdba d= new ComputeLamdba(w);

		final Button startNewGame = new Button("Start new game");

		final GamePanel g = new GamePanel(true, w, startNewGame);

		startNewGame.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				g.startNewGame();
				startNewGame.setEnabled(false);
			}
		});

		VerticalPanel p = new VerticalPanel();
		p.add(g.getPanel());
		p.add(startNewGame);
		startNewGame.setEnabled(false);
		startNewGame.setStyleName("newGameButton");
		p.addStyleName("main_panel");
		RootPanel.get("mUI").clear();
		RootPanel.get("mUI").add(p);

	}

}
