package com.ba.marketUI.client.pages;

import org.apache.xerces.dom.ParentNode;

import com.ba.marketUI.client.WriterTimeSaver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SecondPageSpammerGenerallyQuestions {

	@SuppressWarnings("deprecation")
	public void loadPage() {
		final VerticalPanel welcomePanel = new VerticalPanel();
		final VerticalPanel textPanelLeft = new VerticalPanel();

		String titel = "3G Bandwith Game";
		String text1 = "Please answer the following questions first:";
		String calculation1 = "2+3= ";
		String calculation2 = "7-5=";
		String calculation3 = "8*2=";
		// String country = "From which country are you from?";
		String age = "How old are you?";
		String sex = "Are you female or male?";

		Label ti = new Label(titel);
		Label l_text1 = new Label(text1);
		Label l_calculation1 = new Label(calculation1);
		Label l_calculation2 = new Label(calculation2);
		Label l_calculation3 = new Label(calculation3);
		// Label l_country = new Label(country);
		Label l_age = new Label(age);
		Label l_sex = new Label(sex);

		final TextBox t_calculation1 = new TextBox();
		final TextBox t_calculation2 = new TextBox();
		final TextBox t_calculation3 = new TextBox();
		// final TextBox t_country = new TextBox();
		final TextBox t_age = new TextBox();
		final RadioButton b1 = new RadioButton("s", "female");
		final RadioButton b2 = new RadioButton("s", "male");
		HorizontalPanel buttonPanel = new HorizontalPanel();
		b1.setStyleName("radioB");
		b2.setStyleName("radioB");
		buttonPanel.add(b1);
		buttonPanel.add(b2);

		final WriterTimeSaver w = new WriterTimeSaver();

		w.setTimeOverall();

		final Button button = new Button("Go to the game instruction");
		button.setStyleName("startGameButton");

		button.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				int numOfrightCalculations = 0;

				if (t_calculation1.getValue().equals("5")
						&& t_calculation2.getValue().equals("2")
						&& t_calculation3.getValue().equals("16")) {
					numOfrightCalculations = 3;
				}
				if (t_calculation1.getValue().equals("5")
						&& t_calculation2.getValue().equals("2")
						&& !t_calculation3.getValue().equals("16")) {
					numOfrightCalculations = 2;
				}
				if (t_calculation1.getValue().equals("5")
						&& !t_calculation2.getValue().equals("2")
						&& t_calculation3.getValue().equals("16")) {
					numOfrightCalculations = 2;
				}
				if (!t_calculation1.getValue().equals("5")
						&& t_calculation2.getValue().equals("2")
						&& t_calculation3.getValue().equals("16")) {
					numOfrightCalculations = 2;
				}
				if (numOfrightCalculations >= 2) {

					if (t_age.getValue().equals("")
							|| (!b1.getValue() && !b2.getValue())) {
						Dialog b = new Dialog(
								"You have not filled in all the fields, if you don't want to give the information please fill in a '-'.",
								"Ok");
						int left = Window.getClientWidth() / 2;
						int top = Window.getClientHeight() / 2;
						b.setPopupPosition(left, top);
						b.show();
					} else {
						String assignmentId = Window.Location
								.getParameter(GameParameter.assignmentId);
						String workerId = Window.Location
								.getParameter(GameParameter.workerId);
						String message = ","+assignmentId + "," + workerId + ","
								+ numOfrightCalculations + ","
								+ t_age.getValue() +",";
						if(b1.getValue()){
							message += "f";
						}else if(b2.getValue()){
							message += "m";
						}
						w.addMessage(GameParameter.Worker, message);
						initialGameLayout(w);
					}
				} else {
					// Two of three calculations are not correct -> reject the
					// participant
					String message= Window.Location.getParameter(GameParameter.hitId)+","+Window.Location.getParameter(GameParameter.assignmentId)+","+Window.Location
							.getParameter(GameParameter.workerId)+","+GameParameter.False+","+0+","+"Sorry you are not allowed to participate because you didn't followed the instructions."+"Fall through SpammerQuestions";
					w.addMessage(GameParameter.MTurk, message);
					w.writeToFile();
					
					PageString ps= new PageString(w,"Sorry you are not allowed to participate because you didn't fill in the most of the calculations correctly.");
					ps.loadPage();
										
					//return;
				}
			}
		});

		ti.addStyleName("titel");

		l_text1.addStyleName("textintro");
		l_calculation1.addStyleName("textintro");
		l_calculation2.addStyleName("textintro");
		l_calculation3.addStyleName("textintro");
		// l_country.addStyleName("textintro");
		l_age.addStyleName("textintro");
		l_sex.addStyleName("textintro");

		Image i = new Image();
		// to ensure that the screen is not to huge ->because of the iFrame in
		// Amazone Turk
		i.setPixelSize(270, 440);

		if (GameParameter.NumOptions == 4) {
			i.setUrl("images/screenshot4.png");
		} else if (GameParameter.NumOptions == 5) {
			i.setUrl("images/screenshot5.png");
		} else if (GameParameter.NumOptions == 6) {
			i.setUrl("images/screenshot6.png");
		} else if (GameParameter.NumOptions == 7) {
			i.setUrl("images/screenshot7.png");
		} else if (GameParameter.NumOptions == 3) {
			i.setUrl("images/screenshot3.png");
		}

		textPanelLeft.add(l_text1);
		textPanelLeft.add(l_calculation1);
		textPanelLeft.add(t_calculation1);
		textPanelLeft.add(l_calculation2);
		textPanelLeft.add(t_calculation2);
		textPanelLeft.add(l_calculation3);
		textPanelLeft.add(t_calculation3);
		// textPanelLeft.add(l_country);
		// textPanelLeft.add(t_country);
		textPanelLeft.add(l_age);
		textPanelLeft.add(t_age);
		textPanelLeft.add(l_sex);
		textPanelLeft.add(buttonPanel);

		welcomePanel.add(ti);
		welcomePanel.add(textPanelLeft);
		welcomePanel.add(button);

		welcomePanel.addStyleName("main_panel");

		RootPanel.get("mUI").clear();
		RootPanel.get("mUI").add(welcomePanel);

	}

	private void initialGameLayout(WriterTimeSaver w) {
		ThirdPageGameInstruction g = new ThirdPageGameInstruction(w);
		g.loadPage();
	}

}
