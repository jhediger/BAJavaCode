package com.ba.marketUI.client.introductionPages;

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
		String country = "From which country are you from?";
		String age = "How old are you?";
		String sex = "Are you female or male?";

		Label ti = new Label(titel);
		Label l_text1 = new Label(text1);
		Label l_calculation1 = new Label(calculation1);
		Label l_calculation2 = new Label(calculation2);
		Label l_country = new Label(country);
		Label l_age = new Label(age);
		Label l_sex = new Label(sex);

		final TextBox t_calculation1 = new TextBox();
		final TextBox t_calculation2 = new TextBox();
		final TextBox t_country = new TextBox();
		final TextBox t_age = new TextBox();
		final TextBox t_sex = new TextBox();
		
		final WriterTimeSaver w= new WriterTimeSaver();
		
		w.setTimeOverall();
	
		final Button button = new Button("Go to the game instruction");
		button.setStyleName("startGameButton");

		button.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				if (t_calculation1.getValue().equals("5")
						&& t_calculation2.getValue().equals("2")) {
					// TODO was machen mit diesen?
					if (t_country.getValue().equals("")
							|| t_age.getValue().equals("")
							|| t_sex.getValue().equals("")) {
						Dialog b = new Dialog(
								"You have not filled in all the fields, if you don't want to give the information please fill in a '-'.",
								"Ok");
						int left = Window.getClientWidth() / 2;
						int top = Window.getClientHeight() / 2;
						b.setPopupPosition(left, top);
						b.show();						
					}else{
						String assignmentId= Window.Location.getParameter("assignmentId");
						String workerId= Window.Location.getParameter("workerId");
						String message= assignmentId+" "+workerId+" "+t_country.getValue()+" "+t_age.getValue()+" "+ t_sex.getValue();
						w.addMessage(GameParameter.Worker, message);
						initialGameLayout(w);
					}
				} else {
					Dialog b2 = new Dialog(
							"The calculations are not correct, please correct them!",
							"ok");
					int left = Window.getClientWidth() / 2;
					int top = Window.getClientHeight() / 2;
					b2.setPopupPosition(left, top);
					b2.show();
					return;
				}
			}
		});

		ti.addStyleName("titel");

		l_text1.addStyleName("textintro");
		l_calculation1.addStyleName("textintro");
		l_calculation2.addStyleName("textintro");
		l_country.addStyleName("textintro");
		l_age.addStyleName("textintro");
		l_sex.addStyleName("textintro");

		Image i = new Image();
		// to ensure that the screen is not to huge ->because of the iFrame in
		// Amazone Turk
		i.setPixelSize(270, 440);
		i.setUrl("images/screenshot4.png");

		textPanelLeft.add(l_text1);
		textPanelLeft.add(l_calculation1);
		textPanelLeft.add(t_calculation1);
		textPanelLeft.add(l_calculation2);
		textPanelLeft.add(t_calculation2);
		textPanelLeft.add(l_country);
		textPanelLeft.add(t_country);
		textPanelLeft.add(l_age);
		textPanelLeft.add(t_age);
		textPanelLeft.add(l_sex);
		textPanelLeft.add(t_sex);

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
