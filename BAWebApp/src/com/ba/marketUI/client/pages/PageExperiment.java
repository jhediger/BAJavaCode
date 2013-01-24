package com.ba.marketUI.client.pages;


import com.ba.marketUI.client.ComputeLamdba;
import com.ba.marketUI.client.WriterTimeSaver;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;


public class PageExperiment {

	private WriterTimeSaver w;

	public PageExperiment(WriterTimeSaver w) {
		this.w = w;
	}

	public void loadPage() {
		
			ComputeLamdba d= new ComputeLamdba(w);
			
	
			final GamePanel g = new GamePanel(true,w);
			HorizontalPanel p = new HorizontalPanel();
			p.add(g.getPanel());
			p.addStyleName("main_panel");
			RootPanel.get("mUI").clear();
			RootPanel.get("mUI").add(p);
			
		}

}
