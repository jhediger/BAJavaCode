package com.ba.marketUI.client.introductionPages;


import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;


public class SixthPageExperiment {

	private WriterTimeSaver w;

	public SixthPageExperiment(WriterTimeSaver w) {
		this.w = w;
	}

	public void loadPage() {
	
			final GamePanel g = new GamePanel(true);
			g.setW(w);
			HorizontalPanel p = new HorizontalPanel();
			p.add(g.getPanel());
			p.addStyleName("main_panel");
			RootPanel.get("mUI").clear();
			RootPanel.get("mUI").add(p);
			
		}

}
