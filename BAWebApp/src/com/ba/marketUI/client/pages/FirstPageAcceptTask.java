package com.ba.marketUI.client.pages;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FirstPageAcceptTask {

	@SuppressWarnings("deprecation")
	public void loadPage() {
		final VerticalPanel welcomePanel = new VerticalPanel();
		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		final VerticalPanel textPanel = new VerticalPanel();
		final VerticalPanel textPanelLeft = new VerticalPanel();

		String titel = "3G Bandwith Game";
		String text1 = "Hi, we are from the University of Zurich and we would like to conduct a research about a market user interface design. During the main part of our research you have to play a few rounds of a short easy game.";
		String purpose = "Purpose of research study: We want to analyse the behaviour of the participants in a new market environment. Thus, we want to gain a better understanding of how a user interface can affect the quality of decisions. ";
	    String benefits = "Benefits: The overall goal is to gather information which leads to a better understanding of how people act in new market situations. Furthermore, we want to improve our market user interface for advance researches.";
	    String participation = "Voluntary participation: You can stop the participation at any time by clicking the \"Return HIT\" button.";
	    String endpart = "We may end or reject your participation if: You are not following the instructions or if your time needed significantly deviates from the mean.";
	    String conf = "Confidentiality: All the given answers are treated anonym.";
	    String quest = "Questions/concerns: If you have any concerns or questions please feel free to contact Jessica Hediger (jessica.hediger@uzh.ch)";
		String text2= "If you are interested please click on the \"accept HIT\" button and after that the \"Go to study\" button to start the research.";
		
	    final Button button = new Button("Go to study");
		button.setStyleName("startGameButton");

		button.addClickListener(new ClickListener() {
		
			public void onClick(Widget sender) {
				if(Window.Location.getParameter("assignmentId")!=null){
					if(Window.Location.getParameter("assignmentId")=="ASSIGNMENT_ID_NOT_AVAILABLE"){
						Window.alert("Please accept the hit first, through pushing the \"Accept hit\" button!");
						return;
					}
				}
				initialGameLayout();
			}
		});
		
		Label ti = new Label(titel);
		Label l_text1= new Label(text1);
		Label l_purpose= new Label(purpose);
		Label l_benefits= new Label(benefits);
		Label l_participation= new Label(participation);
		Label l_endpart= new Label(endpart);
		Label l_conf= new Label(conf);
		Label l_quest= new Label(quest);
		Label l_text2= new Label(text2);
		
		ti.addStyleName("titel");

		l_text1.addStyleName("textintro");
		l_purpose.addStyleName("textintro");
		l_benefits.addStyleName("textintro");
		l_participation.addStyleName("textintro");
		l_endpart.addStyleName("textintro");
		l_conf.addStyleName("textintro");
		l_quest.addStyleName("textintro");
		l_text2.addStyleName("textintro");
		
		Image i= new Image();
		//to ensure that the screen is not to huge ->because of the iFrame in Amazone Turk
		i.setPixelSize(270, 440);
		i.setUrl("images/screenshot4.png");
		
		textPanel.add(l_purpose);
		textPanel.add(l_benefits);
		textPanel.add(l_participation);
		textPanel.add(l_endpart);
		textPanel.add(l_conf);
		textPanel.add(l_quest);
	
		textPanelLeft.add(l_text1);
		textPanelLeft.add(l_text2);
				
		welcomePanel.add(ti);
		welcomePanel.add(textPanelLeft);
		welcomePanel.add(button);
		
		
		horizontalPanel.add(welcomePanel);
		horizontalPanel.add(i);
		horizontalPanel.add(textPanel);
		
		horizontalPanel.addStyleName("main_panel");
		

		RootPanel.get("mUI").clear();
		RootPanel.get("mUI").add(horizontalPanel);
	
		
	}
	
	private void initialGameLayout(){
		SecondPageSpammerGenerallyQuestions g = new SecondPageSpammerGenerallyQuestions();
		g.loadPage();
	}
}
