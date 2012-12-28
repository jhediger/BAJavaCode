package com.ba.marketUI.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class InterfaceFirst {
	
	private String assignmentId = "";
	private String turkSubmitTo ="";
	private String workerId="";
	private String hitId="";

	@SuppressWarnings("deprecation")
	public void loadPage() {
		final VerticalPanel welcomePanel = new VerticalPanel();
		final HorizontalPanel description = new HorizontalPanel();

		String titel = "Bandwith Game";
		String text = "Today many people are using a lot of bandwidth with their phones in their everyday live. Some of them perform just simple things like reading the newspaper or browsing through some websites. Others want to do important tasks like writing an urgent email. The Bandwidth Game is based on the assumption that we won\u0027t have enough bandwidth within the near future and thus it needs an effective allocation in case of a shortage in Bandwidth. The game is built to analyze how a potential user acts in this new market environment. It consists of buttons, counters for the rounds, the token, the score and the time, a label that shows the actual task category as well as an underlying market mechanism. It depends on the input parameters if the game has two, three, four, five or six buttons. Each button shows the speed in KB/s, a value in $ and a price in tokens. The player has to choose one of the buttons of which he thinks it is the best choice in each round. If the player hasn\u0027t got enough tokens to pay for the speed of a specific button then the button is not enable. The goal of the game is to spend all tokens during six rounds and to try to score the maximum.  The time counter is an additional difficulty that ensures that a user is forced to choose a button. In case no button is chosen within the maximal time, a mechanism automatically chooses the fourth button with the speed of 0 KB/s. The actual task category is chosen randomly by the underlying mechanism, in order to give a real touch to the game. It also computes speeds, values and prices depending on the actual task category.";

		final Button button = new Button("Start Game");
		button.setStyleName("startGameButton");

		button.addClickListener(new ClickListener() {
			

			

			public void onClick(Widget sender) {
				if(Window.Location.getParameter("assignmentId")!=null){
					if(Window.Location.getParameter("assignmentId")=="ASSIGNMENT_ID_NOT_AVAILABLE"){
						Window.alert("Please accept the hit first, through pushing the \"Accept hit\" button!");
						return;
					}
					assignmentId= Window.Location.getParameter("assignmentId");
					turkSubmitTo= Window.Location.getParameter("turkSubmitTo");
					workerId= Window.Location.getParameter("workerId");
					hitId= Window.Location.getParameter("hitId");
					//Window.alert(assignmentId+" " +hitId);
					
					
				}
				initialGameLayout();
			}
		});
		
		Label ti = new Label(titel);
		Label te = new Label(text);
		
		ti.addStyleName("titel");
		te.addStyleName("text");

		Image i= new Image();
		//to ensure that the screen is not to huge ->because of the iFrame in Amazone Turk
		i.setPixelSize(270, 440);
		i.setUrl("images/screenshot4.png");
		
		
		
		description.add(te);
		description.add(i);
		//description.add(button);
		
		
		welcomePanel.addStyleName("main_panel");
		welcomePanel.add(button);
		welcomePanel.add(ti);
		welcomePanel.add(description);

		RootPanel.get("mUI").clear();
		RootPanel.get("mUI").add(welcomePanel);
	
		
	}
	
	private void initialGameLayout(){
		InterfaceGame g = new InterfaceGame();
		g.loadPage();
	}

}
