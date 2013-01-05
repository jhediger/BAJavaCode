package com.ba.marketUI.client.introductionPages;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FourthPageComprehensionQuestions {

	private WriterTimeSaver w;

	public FourthPageComprehensionQuestions(WriterTimeSaver w) {
		this.w = w;
	}

	public void loadPage() {
		final HorizontalPanel welcomePanel = new HorizontalPanel();
		final VerticalPanel textPanelLeft = new VerticalPanel();

		String text1 = "Comprehension questions to the game situation showed in the image on the right side:";
		String question1 = "1)	What is the new score after someone pushed the button with the speed 100.0 KB/s?";
		String question2 = "2)	What happens if none button is pushed within 6 seconds?";
		String question3 = "3)	How many tokens are spent in total after someone pushed the button with the speed 900.0 KB/s?";
		String text2 = "In the next step, we suggest you to try out the game and to complete at least two or three games to get into the game mechanism. ";
		String text3 = "After that, you can start with the actual experiment. Depending on the experimental group you have 3, 4, 5 or 6 buttons from which you can choose one. In addition, the time constraint can be slightly different.";

		Label l_text1 = new Label(text1);
		Label l_text2 = new Label(question1);
		Label l_text3 = new Label(question2);
		Label l_text4 = new Label(question3);
		Label l_text5 = new Label(text2);
		Label l_text6 = new Label(text3);

		// Make some radio buttons, all in one group.
		RadioButton rb10 = new RadioButton("GroupQ1", "$-4.1");
		final RadioButton rb11 = new RadioButton("GroupQ1", "$-5.0");
		RadioButton rb12 = new RadioButton("GroupQ1",
				"It's not possible to know that");

		final RadioButton rb20 = new RadioButton("GroupQ2",
				"The deepest button is automatically chosen");
		RadioButton rb21 = new RadioButton("GroupQ2", "Nothing");
		RadioButton rb22 = new RadioButton("GroupQ2",
				"It's not possible to know that");

		final RadioButton rb30 = new RadioButton("GroupQ3", "12");
		RadioButton rb31 = new RadioButton("GroupQ3", "5");
		RadioButton rb32 = new RadioButton("GroupQ3",
				"It's not possible to know that");

		rb10.setStyleName("radioB");
		rb11.setStyleName("radioB");
		rb12.setStyleName("radioB");
		rb20.setStyleName("radioB");
		rb21.setStyleName("radioB");
		rb22.setStyleName("radioB");
		rb30.setStyleName("radioB");
		rb31.setStyleName("radioB");
		rb32.setStyleName("radioB");

		FlowPanel panelQ1 = new FlowPanel();
		panelQ1.add(rb10);
		panelQ1.add(rb11);
		panelQ1.add(rb12);

		FlowPanel panelQ2 = new FlowPanel();
		panelQ2.add(rb20);
		panelQ2.add(rb21);
		panelQ2.add(rb22);

		FlowPanel panelQ3 = new FlowPanel();
		panelQ3.add(rb30);
		panelQ3.add(rb31);
		panelQ3.add(rb32);

		textPanelLeft.add(l_text1);
		textPanelLeft.add(l_text2);
		textPanelLeft.add(panelQ1);
		textPanelLeft.add(l_text3);
		textPanelLeft.add(panelQ2);
		textPanelLeft.add(l_text4);
		textPanelLeft.add(panelQ3);
		textPanelLeft.add(l_text5);
		textPanelLeft.add(l_text6);

		l_text1.addStyleName("textintro");
		l_text2.addStyleName("textintro");
		l_text3.addStyleName("textintro");
		l_text4.addStyleName("textintro");
		l_text5.addStyleName("textintro");
		l_text6.addStyleName("textintro");

		Image i = new Image();
		// to ensure that the screen is not to huge ->because of the iFrame in
		// Amazone Turk
		i.setPixelSize(280, 450);
		i.setUrl("images/lastround.png");

		final Button button1 = new Button("Go back to the instructions");
		button1.setStyleName("startGameButton");

		button1.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				goBack();
			}

			private void goBack() {
				ThirdPageGameInstruction i = new ThirdPageGameInstruction(w);
				i.loadPage();
			}
		});

		final Button button2 = new Button("Go to the game");
		button2.setStyleName("startGameButton");

		button2.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if (rb30.getValue() == true && rb20.getValue() == true
						&& rb11.getValue() == true) {
					initialGameLayout();
				} else {
					Dialog d = new Dialog(
							"There is a mistake in your answers, please read the instructions again",
							"Ok");
					d.show();
				}

			}
		});

		textPanelLeft.add(button1);
		textPanelLeft.add(button2);

		welcomePanel.add(textPanelLeft);
		welcomePanel.add(i);

		welcomePanel.addStyleName("main_panel");

		RootPanel.get("mUI").clear();
		RootPanel.get("mUI").add(welcomePanel);

	}

	private void initialGameLayout() {
		FifthPageTestGame g = new FifthPageTestGame(w);
		g.loadPage();
	}

}
