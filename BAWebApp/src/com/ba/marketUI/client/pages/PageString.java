package com.ba.marketUI.client.pages;

import com.ba.marketUI.client.ComClientInterface;
import com.ba.marketUI.client.ComClientInterfaceAsync;
import com.ba.marketUI.client.WriterTimeSaver;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PageString {

	private WriterTimeSaver w;
	
	private String text1;


	public PageString(WriterTimeSaver w, String text1) {
		this.w = w;
		this.text1 = text1;
	}

	public void loadPage() {

		VerticalPanel p = new VerticalPanel();
		
		String re = "If you push the \"OK\" the hit will be submitted and later rejected. Alternatively you can return the hit and try it another time with a different hit.";

		Label l_text2 = new Label(re);
		Label l_text1 = new Label(text1);
		Button b = new Button("OK");
		b.setStyleName("startGameButton");
		l_text1.setStyleName("titel");
		l_text2.setStyleName("textintro");

		b.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				String assignmentId = Window.Location
						.getParameter(GameParameter.assignmentId);
				String hitId = Window.Location
						.getParameter(GameParameter.hitId);
				String url = "";
				// Window.alert(assignmentId+" "+hitId);
				if (Window.Location.getParameter(GameParameter.assignmentId) != null) {

					if (GameParameter.InSandbox) {
						Window.alert(assignmentId + " " + hitId);
						url = "https://workersandbox.mturk.com/mturk/externalSubmit?assignmentId="
								+ assignmentId + "&amp;hitId=" + hitId;
						// + assignmentId + "&hitId=" + hitId;
					} else {
						url = "https://www.mturk.com/mturk/externalSubmit?assignmentId="
								+ assignmentId + "&amp;hitId=" + hitId;
					}
					Window.Location.assign(url);

					// Window.Location.replace(url);
				}
			}
		});

		
		p.add(l_text1);
		p.add(l_text2);
		p.add(b);

		p.addStyleName("main_panel");

		RootPanel.get("mUI").clear();
		RootPanel.get("mUI").add(p);

	
	
	}

}
