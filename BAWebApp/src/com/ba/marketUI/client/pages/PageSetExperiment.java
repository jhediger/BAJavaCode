package com.ba.marketUI.client.pages;

import com.ba.marketUI.client.ComputeLamdba;
import com.ba.marketUI.client.WriterTimeSaver;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PageSetExperiment {

	private WriterTimeSaver w;

	public PageSetExperiment(WriterTimeSaver w) {
		this.w = w;
	}

	@SuppressWarnings("deprecation")
	public void loadPage() {
		final VerticalPanel welcomePanel = new VerticalPanel();

		String titel = "";
		String text0 = "";
		String text1 = "";
		String text2 = "";
		String buttonText = "";
		if (!GameParameter.BehavioralOptimizationExperiment) {
			if (GameParameter.NumOfExperiment == 0) {
				titel = "3G Bandwith Game - Experiment - First Part";
				text0 = "The experiment consists of four parts.";
				text1 = "In each part of the experiment you have to play four games. After each part the number of buttons will be changed and you have to play another four games.";
				text2 = "You will start with three buttons in the first part.";
				buttonText = "Start part one";
				GameParameter.NumOptions = 3;
				w.setTimeExp1();
			} else if (GameParameter.NumOfExperiment == 1) {
				w.stopTimeExp1();
				titel = "3G Bandwith Game - Experiment - Second Part";
				text0 = "You have terminated the first part.";
				text1 = "In the second part the game has 4 buttons to choose.";
				buttonText = "Start part two";
				GameParameter.NumOptions = 4;
				w.setTimeExp2();
			} else if (GameParameter.NumOfExperiment == 2) {
				w.stopTimeExp2();
				titel = "3G Bandwith Game - Experiment - Third Part";
				text0 = "You have terminated the second part.";
				text1 = "In the thid part the game has 5 buttons to choose.";
				buttonText = "Start part three";
				GameParameter.NumOptions = 5;
				w.setTimeExp3();
			} else if (GameParameter.NumOfExperiment == 3) {
				w.stopTimeExp3();
				titel = "3G Bandwith Game - Experiment - Fourth and last Part";
				text0 = "You have terminated the third part.";
				text1 = "In the fourth part the game has 6 buttons to choose.";
				buttonText = "Start part four";
				GameParameter.NumOptions = 6;
				w.setTimeExp4();
			}
		} else {
			if (GameParameter.NumOfExperiment == 0) {
				titel = "3G Bandwith Game - Experiment - First Part";
				text0 = "The experiment consists of two parts.";
				text1 = "In each part of the experiment you have to play four games.";
				// TODO break aufgabe falls rechner zu lange braucht..
				text2 = "Before the second part starts there will be a short break.";
				buttonText = "Start part one";
				w.setTimeExp1();
			} else if (GameParameter.NumOfExperiment == 1) {
				w.stopTimeExp1();
				titel = "3G Bandwith Game - Experiment - Second Part";
				text0 = "You have terminated the first part.";
				// text1 =
				// "In the second part the game has 4 buttons to choose.";
				buttonText = "Start part two";
				w.setTimeExp2();
			}

		}

		final Button button = new Button(buttonText);
		button.setStyleName("startGameButton");

		button.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				PageExperiment p = new PageExperiment(w);
				p.loadPage();
			}
		});

		Label l_text0 = new Label(text0);

		l_text0.addStyleName("textintro");

		Label l_text1 = new Label(text1);

		l_text1.addStyleName("textintro");

		Label l_text2 = new Label(text2);

		l_text2.addStyleName("textintro");

		Label ti = new Label(titel);

		ti.addStyleName("titel");

		welcomePanel.add(ti);
		welcomePanel.add(l_text0);
		welcomePanel.add(l_text1);
		welcomePanel.add(l_text2);
		welcomePanel.add(button);

		welcomePanel.addStyleName("main_panel");

		RootPanel.get("mUI").clear();
		RootPanel.get("mUI").add(welcomePanel);

	}

}
