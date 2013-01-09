package com.ba.marketUI.client.pages;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Dialog extends DialogBox {

	boolean goback = false;

	//geht nicht..
	/**public Dialog(String string, String string2, String string3) {
		//setText(string);//titel
		HorizontalPanel hp = new HorizontalPanel();
		VerticalPanel vp = new VerticalPanel();
		

		Label l = new Label(string);
		// Enable animation.
		setAnimationEnabled(true);

		// Enable glass background.
		setGlassEnabled(true);

		// DialogBox is a SimplePanel, so you have to set its widget
		// property to
		// whatever you want its contents to be.
		Button yes = new Button(string2);
		Button no = new Button(string3);
		yes.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				goback = true;
				Dialog.this.hide();
				setVisible(false);
			}
		});
		no.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				goback = false;
				Dialog.this.hide();
				setVisible(false);
			}
		});
		
		hp.add(yes);
		hp.add(no);
		vp.add(l);
		vp.add(hp);
		setWidget(vp);
	}*/

	public Dialog(String string, String string2) {
		//setText(string);

VerticalPanel vp = new VerticalPanel();
		

		Label l = new Label(string);
		// Enable animation.
		setAnimationEnabled(true);

		// Enable glass background.
		setGlassEnabled(true);

		// DialogBox is a SimplePanel, so you have to set its widget
		// property to
		// whatever you want its contents to be.
		Button yes = new Button(string2);
		yes.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Dialog.this.hide();
			}
		});
		vp.add(l);
		vp.add(yes);
		setWidget(vp);
	}

	public boolean getResult() {
		return goback;
	}

}
