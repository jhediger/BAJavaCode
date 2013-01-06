package com.ba.marketUI.client.introductionPages;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ThirdPageGameInstruction {

	WriterTimeSaver w = new WriterTimeSaver();
	
	public ThirdPageGameInstruction(WriterTimeSaver w) {
		this.w = w;
	}

	public void loadPage() {
		final HorizontalPanel welcomePanel = new HorizontalPanel();
		final VerticalPanel textPanelLeft = new VerticalPanel();

		String text1 = "Please read the following information carefully because on the next page you have to answer some comprehension questions.";
		String titel1 = "Background information of the game:";
		String text2 = "Today many people are using a lot of 3G bandwidth with their phones in their everyday lives. Sometimes they use it for doing simple things such as reading the newspaper or browsing through some websites. Sometimes they want to do important tasks for instance writing an urgent email. The Bandwidth Game is based on the assumption that we will not have enough 3G bandwidth within the near future and we use it for low, medium or important tasks. Thus, an effective allocation is needed in case of a shortage in bandwidth. The game is built to analyse how a potential user acts in this new market environment.";
		String titel2 = "3G Bandwidth game instructions:";
		String text3 = "The goal of the game is to spend all tokens (1) during six rounds (2) and to try scoring (3) the maximum.";
		String text4 = "You have to choose one button (4) in each round. Each button consists of the three labels: speed, value and price. The speed signalises the bandwidth you choose in the specific situation. The value is added to the score (3) and the price is added to the token spent (1). If the player has not got enough tokens to pay for the speed of a specific button then the button is not enabled (5).";
		String text5 = "The game has an underlying market mechanism which randomly chooses the actual task category (6). It also computes speeds, values and prices which strongly varying because of the actual task category. Thus, it can be essential to save tokens in low important situations and to spend them in high important situations.";
		String text6 = "In each round of the game you have a certain time in seconds (7) to take a decision. In case no button is pushed within this time, a mechanism automatically takes the button with the speed of 0 KB/s.";

		
		Label l_titel1 = new Label(titel1);
		Label l_titel2 = new Label(titel2);
		Label l_text1 = new Label(text1);
		Label l_text2 = new Label(text2);
		Label l_text3 = new Label(text3);
		Label l_text4 = new Label(text4);
		Label l_text5 = new Label(text5);
		Label l_text6 = new Label(text6);

		textPanelLeft.add(l_text1);
		textPanelLeft.add(l_titel1);
		textPanelLeft.add(l_text2);
		textPanelLeft.add(l_titel2);
		textPanelLeft.add(l_text3);
		textPanelLeft.add(l_text4);
		textPanelLeft.add(l_text5);
		textPanelLeft.add(l_text6);
		
		l_titel1.addStyleName("titel");
		l_titel2.addStyleName("titel");

		l_text1.addStyleName("textintro");
		l_text2.addStyleName("textintro");
		l_text3.addStyleName("textintro");
		l_text4.addStyleName("textintro");
		l_text5.addStyleName("textintro");
		l_text6.addStyleName("textintro");
		
	
		Image i = new Image();
		// to ensure that the screen is not to huge ->because of the iFrame in
		// Amazone Turk
		i.setPixelSize(270, 440);
		i.setUrl("images/numbersgross.png");
		
		final Button button = new Button("Go to test");
		button.setStyleName("startGameButton");
		
		button.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				initialGameLayout();
			}
		});

		textPanelLeft.add(button);
		
		welcomePanel.add(textPanelLeft);
		welcomePanel.add(i);

		welcomePanel.addStyleName("main_panel");

		RootPanel.get("mUI").clear();
		RootPanel.get("mUI").add(welcomePanel);

	}

	private void initialGameLayout() {
		FourthPageComprehensionQuestions g = new FourthPageComprehensionQuestions(w);
		g.loadPage();
	}

}
