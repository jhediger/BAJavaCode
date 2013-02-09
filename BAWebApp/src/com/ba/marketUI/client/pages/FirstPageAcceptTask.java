package com.ba.marketUI.client.pages;

import com.ba.marketUI.client.WriterTimeSaver;
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
	WriterTimeSaver w = new WriterTimeSaver();

	@SuppressWarnings("deprecation")
	public void loadPage() {
		final VerticalPanel welcomePanel = new VerticalPanel();
		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		final VerticalPanel textPanel = new VerticalPanel();
		final VerticalPanel textPanelLeft = new VerticalPanel();
		
		w.setInputParameter();

		String titel = "3G Bandwith Game";
		String text1 = "This is a study from the  University of Zurich about a market user interface design. In the main part of the study you will have to play a few rounds of a short easy game.";
		String bonus = "Important: We pay a high bonus depending on your final game score.";
		String purpose = "Purpose of the study: We want to analyse the behaviour of participants in a hypothetical market environment.";
	    String benefits = "Benefits: The overall goal is to gather information which leads to a better understanding of how people act in new market situations. Furthermore, we want to improve our market user interface for further research studies.";
	    String participation = "Voluntary participation: You may stop the participation at any time by clicking the \"Return HIT\" button. ";
	    String endpart = "We may end or reject your participation if you are not following the instructions or if your decision time needed significantly deviates from the mean.";
	    String conf = "Confidentiality: All the given answers are treated anonymously.";
	    String quest = "Questions/concerns: If you have any concerns or questions please feel free to contact Jessica Hediger (jessica.hediger@uzh.ch).";
		String text2= "If you are interested to participate in the study please click on the \"accept HIT\" button and after that the \"Go to study\" button to start the questionnaire.  ";
		
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
		Label l_bonus= new Label(bonus);
		Label l_purpose= new Label(purpose);
		Label l_benefits= new Label(benefits);
		Label l_participation= new Label(participation);
		Label l_endpart= new Label(endpart);
		Label l_conf= new Label(conf);
		Label l_quest= new Label(quest);
		Label l_text2= new Label(text2);
		
		ti.addStyleName("titel");
		l_bonus.addStyleName("titel");
		
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
		if(GameParameter.NumOptions==0){
			GameParameter.NumOptions=4;
		}
		
		i.setPixelSize(270, 440);
		if(GameParameter.NumOptions==4){
		i.setUrl("images/screenshot4.png");
		}else if(GameParameter.NumOptions==5){
			i.setUrl("images/screenshot5.png");
		}else if(GameParameter.NumOptions==6){
			i.setUrl("images/screenshot6.png");
		}else if(GameParameter.NumOptions==7){
			i.setUrl("images/screenshot7.png");
		}else if(GameParameter.NumOptions==3){
			i.setUrl("images/screenshot3.png");
		}
		
		textPanel.add(l_purpose);
		textPanel.add(l_benefits);
		textPanel.add(l_participation);
		textPanel.add(l_endpart);
		textPanel.add(l_conf);
		textPanel.add(l_quest);
	
		textPanelLeft.add(l_text1);
		textPanelLeft.add(l_bonus);
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
