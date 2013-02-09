package com.ba.marketUI.client.pages;

import java.io.IOException;

import com.ba.marketUI.client.ComClientInterface;
import com.ba.marketUI.client.ComClientInterfaceAsync;
import com.ba.marketUI.client.WriterTimeSaver;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
	// To get the computed Q-values
	private ComClientInterfaceAsync dataStoreService = (ComClientInterfaceAsync) GWT
			.create(ComClientInterface.class);

	private String datei;

	public FourthPageComprehensionQuestions(WriterTimeSaver w) {
		this.w = w;
	}

	public void loadPage() {
		final HorizontalPanel welcomePanel = new HorizontalPanel();
		final VerticalPanel textPanelLeft = new VerticalPanel();

		String text1 = "Comprehension questions to the game situation showed in the image on the right side:";
		String question1 = "1) What is the current task category?";
		String question2 = "2) What are the price and the value of the button with the speed";
		String question3 = "3) What is the new total score after someone pushed the button with the speed";
		String question4 = "4) What happens if no button is pushed within";
		String question5 = "5) How many tokens are spent in total after someone pushed the button with the speed";
		String question6 = "6) Why is it not possible to push the button with the speed";

		// Make some radio buttons, all in one group.
		final RadioButton rb10 = new RadioButton("GroupQ1");
		RadioButton rb11 = new RadioButton("GroupQ1");
		RadioButton rb12 = new RadioButton("GroupQ1");

		final RadioButton rb20 = new RadioButton("GroupQ2");
		RadioButton rb21 = new RadioButton("GroupQ2");
		RadioButton rb22 = new RadioButton("GroupQ2");
		
		RadioButton rb30 = new RadioButton("GroupQ3");
		RadioButton rb31 = new RadioButton("GroupQ3");
		final RadioButton rb32 = new RadioButton("GroupQ3");
		
		RadioButton rb40 = new RadioButton("GroupQ4");
		final RadioButton rb41 = new RadioButton("GroupQ4");
		RadioButton rb42 = new RadioButton("GroupQ4");
		
		final RadioButton rb50 = new RadioButton("GroupQ5");
		RadioButton rb51 = new RadioButton("GroupQ5");
		RadioButton rb52 = new RadioButton("GroupQ5");
		
		RadioButton rb60 = new RadioButton("GroupQ6");
		RadioButton rb61 = new RadioButton("GroupQ6");
		final RadioButton rb62 = new RadioButton("GroupQ6");
		
		Image i = new Image();
		// to ensure that the screen is not to huge ->because of the iFrame in
		// Amazone Turk
		i.setPixelSize(280, 450);
		if (GameParameter.NumOptions == 4) {
			i.setUrl("images/screenshot4.png");
			//solution is 0,0,2,1,0,2
			//1)What is the current task category?
			rb10.setText("Low Importance");
			rb11.setText("High Importance");
			rb12.setText("It is not possible to know that.");
			//2)What are the price and the value of the button with the speed [num OF Choices]?
			question2+= " 100.0 KB/s?";
			rb20.setText("Price:3 Value:$0.4");
			rb21.setText("Price:0 Value:$0.2");
			rb22.setText("It is not possible to know that.");
			//3)What is the new total score after someone pushed the button with the speed [oberster ->Num Of Choices]?
			question3+= " 300.0 KB/s?";
			rb30.setText("$1");
			rb31.setText("$2");
			rb32.setText("$2.6");
			//4)What happens if no button is pushed within [gross] seconds?
			question4+= " 4 seconds?";
			rb40.setText("Nothing");
			rb41.setText("The lowest connection speed is automatically chosen.");
			rb42.setText("It is not possible to know that.");
			//5)How many tokens are spent in total after someone pushed the button with the speed [mittlerer ->Num Of Choices]?
			question5+= " 300.0 KB/s?";
			rb50.setText("18");
			rb51.setText("6");
			rb52.setText("12");
			//6)Why is it not possible to push the button with the speed [numOfChoices]?
			question6+= " 900.0 KB/s?";
			rb60.setText("It is not possible to know that.");
			rb61.setText("Because it is not a good strategy to take this button.");
			rb62.setText("Because there are too few tokens left.");
			
		} else if (GameParameter.NumOptions == 5) {
			i.setUrl("images/screenshot5.png");
			//solution is 0,0,2,1,0,2
			//1)What is the current task category?
			rb10.setText("Low Importance");
			rb11.setText("High Importance");
			rb12.setText("It is not possible to know that.");
			//2)What are the price and the value of the button with the speed [num OF Choices]?
			question2+= " 100.0 KB/s?";
			rb20.setText("Price:3 Value:$0.2");
			rb21.setText("Price:0 Value:$0.2");
			rb22.setText("It is not possible to know that.");
			//3)What is the new total score after someone pushed the button with the speed [oberster ->Num Of Choices]?
			question3+= " 200.0 KB/s?";
			rb30.setText("$1");
			rb31.setText("$2");
			rb32.setText("$2.7");
			//4)What happens if no button is pushed within [gross] seconds?
			question4+= " 7 seconds?";
			rb40.setText("Nothing");
			rb41.setText("The lowest connection speed is automatically chosen.");
			rb42.setText("It is not possible to know that.");
			//5)How many tokens are spent in total after someone pushed the button with the speed [mittlerer ->Num Of Choices]?
			question5+= " 200.0 KB/s?";
			rb50.setText("15");
			rb51.setText("10");
			rb52.setText("7");
			//6)Why is it not possible to push the button with the speed [numOfChoices]?
			question6+= " 900.0 KB/s?";
			rb60.setText("It is not possible to know that.");
			rb61.setText("Because it is not a good strategy to take this button.");
			rb62.setText("Because there are too few tokens left.");
		} else if (GameParameter.NumOptions == 6) {
			i.setUrl("images/screenshot6.png");
			//solution is 0,0,2,1,0,2
			//1)What is the current task category?
			rb10.setText("Low Importance");
			rb11.setText("High Importance");
			rb12.setText("It is not possible to know that.");
			//2)What are the price and the value of the button with the speed [num OF Choices]?
			question2+= " 200.0 KB/s?";
			rb20.setText("Price:6 Value:$0.6");
			rb21.setText("Price:0 Value:$0.6");
			rb22.setText("It is not possible to know that.");
			//3)What is the new total score after someone pushed the button with the speed [oberster ->Num Of Choices]?
			question3+= " 300.0 KB/s?";
			rb30.setText("$1");
			rb31.setText("$2.4");
			rb32.setText("$2");
			//4)What happens if no button is pushed within [gross] seconds?
			question4+= " 8 seconds?";
			rb40.setText("Nothing");
			rb41.setText("The lowest connection speed is automatically chosen.");
			rb42.setText("It is not possible to know that.");
			//5)How many tokens are spent in total after someone pushed the button with the speed [mittlerer ->Num Of Choices]?
			question5+= " 100.0 KB/s?";
			rb50.setText("9");
			rb51.setText("6");
			rb52.setText("12");
			//6)Why is it not possible to push the button with the speed [numOfChoices]?
			question6+= " 900.0 KB/s?";
			rb60.setText("It is not possible to know that.");
			rb61.setText("Because it is not a good strategy to take this button.");
			rb62.setText("Because there are too few tokens left.");
			i.setUrl("images/screenshot6.png");
		} else if (GameParameter.NumOptions == 3) {
			i.setUrl("images/screenshot3.png");
			//solution is 0,0,2,1,0,2
			//1)What is the current task category?
			rb10.setText("Medium Importance");
			rb11.setText("High Importance");
			rb12.setText("It is not possible to know that.");
			//2)What are the price and the value of the button with the speed [num OF Choices]?
			question2+= " 0.0 KB/s?";
			rb20.setText("Price:0 Value:$0.4");
			rb21.setText("Price:0 Value:$0.2");
			rb22.setText("It is not possible to know that.");
			//3)What is the new total score after someone pushed the button with the speed [oberster ->Num Of Choices]?
			question3+= " 200.0 KB/s?";
			rb30.setText("$8");
			rb31.setText("$9.4");
			rb32.setText("$9.8");
			//4)What happens if no button is pushed within [gross] seconds?
			question4+= " 8 seconds?";
			rb40.setText("Nothing");
			rb41.setText("The lowest connection speed is automatically chosen.");
			rb42.setText("It is not possible to know that.");
			//5)How many tokens are spent in total after someone pushed the button with the speed [mittlerer ->Num Of Choices]?
			question5+= " 200.0 KB/s?";
			rb50.setText("30");
			rb51.setText("6");
			rb52.setText("12");
			//6)Why is it not possible to push the button with the speed [numOfChoices]?
			question6+= " 600.0 KB/s?";
			rb60.setText("It is not possible to know that.");
			rb61.setText("Because it is not a good strategy to take this button.");
			rb62.setText("Because there are too few tokens left.");
		}

		String text2 = "Test rounds: In the next step, we suggest you to conduct at least four test rounds to get familiar with the game mechanism.";
		
		Label l_text1 = new Label(text1);
		Label l_question1 = new Label(question1);
		Label l_question2 = new Label(question2);
		Label l_question3 = new Label(question3);
		Label l_question4 = new Label(question4);
		Label l_question5 = new Label(question5);
		Label l_question6 = new Label(question6);
		Label l_text2 = new Label(text2);
		

		rb10.setStyleName("radioB");
		rb11.setStyleName("radioB");
		rb12.setStyleName("radioB");
		rb20.setStyleName("radioB");
		rb21.setStyleName("radioB");
		rb22.setStyleName("radioB");
		rb30.setStyleName("radioB");
		rb31.setStyleName("radioB");
		rb32.setStyleName("radioB");
		rb40.setStyleName("radioB");
		rb41.setStyleName("radioB");
		rb42.setStyleName("radioB");
		rb50.setStyleName("radioB");
		rb51.setStyleName("radioB");
		rb52.setStyleName("radioB");
		rb60.setStyleName("radioB");
		rb61.setStyleName("radioB");
		rb62.setStyleName("radioB");

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
		
		FlowPanel panelQ4 = new FlowPanel();
		panelQ4.add(rb40);
		panelQ4.add(rb41);
		panelQ4.add(rb42);
		
		FlowPanel panelQ5 = new FlowPanel();
		panelQ5.add(rb50);
		panelQ5.add(rb51);
		panelQ5.add(rb52);
		
		FlowPanel panelQ6 = new FlowPanel();
		panelQ6.add(rb60);
		panelQ6.add(rb61);
		panelQ6.add(rb62);
		

		textPanelLeft.add(l_text1);
		textPanelLeft.add(l_question1);
		textPanelLeft.add(panelQ1);
		textPanelLeft.add(l_question2);
		textPanelLeft.add(panelQ2);
		textPanelLeft.add(l_question3);
		textPanelLeft.add(panelQ3);
		textPanelLeft.add(l_question4);
		textPanelLeft.add(panelQ4);
		textPanelLeft.add(l_question5);
		textPanelLeft.add(panelQ5);
		textPanelLeft.add(l_question6);
		textPanelLeft.add(panelQ6);
		textPanelLeft.add(l_text2);
		
		l_text1.addStyleName("textintro");
		l_text2.addStyleName("textintro");
		l_question1.addStyleName("textintro");
		l_question2.addStyleName("textintro");
		l_question3.addStyleName("textintro");
		l_question4.addStyleName("textintro");
		l_question5.addStyleName("textintro");
		l_question6.addStyleName("textintro");

		
		

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
				if (rb10.getValue() == true && rb20.getValue() == true
						&& rb32.getValue() == true && rb41.getValue()==true && rb50.getValue()==true && rb62.getValue()==true) {
					initialGameLayout();
				} 
				//only 1) false
				else if(rb10.getValue() != true && rb20.getValue() == true
						&& rb32.getValue() == true && rb41.getValue()==true && rb50.getValue()==true && rb62.getValue()==true){
					Dialog b2 = new Dialog(
							"You didn't answer the question number 1 right. The right answer is the first answer. Please ensure that you understand all aspects of the game during the test rounds.",
							"ok");
					int left = Window.getClientWidth() / 2;
					int top = Window.getClientHeight() / 2;
					b2.setPopupPosition(left, top);
					b2.show();
					
				}
				//only 2) false
				else if(rb10.getValue() == true && rb20.getValue() != true
						&& rb32.getValue() == true && rb41.getValue()==true && rb50.getValue()==true && rb62.getValue()==true){
					Dialog b2 = new Dialog(
							"You didn't answer the question number 2 right. The right answer is the first answer. Please ensure that you understand all aspects of the game during the test rounds.",
							"ok");
					int left = Window.getClientWidth() / 2;
					int top = Window.getClientHeight() / 2;
					b2.setPopupPosition(left, top);
					b2.show();
				}
				//only 3) false
				else if(rb10.getValue() == true && rb20.getValue() == true
						&& rb32.getValue() != true && rb41.getValue()==true && rb50.getValue()==true && rb62.getValue()==true){
					Dialog b2 = new Dialog(
							"You didn't answer the question number 3 right. The right answer is the last answer. Please ensure that you understand all aspects of the game during the test rounds.",
							"ok");
					int left = Window.getClientWidth() / 2;
					int top = Window.getClientHeight() / 2;
					b2.setPopupPosition(left, top);
					b2.show();
					
				}
				//only 4) false
				else if(rb10.getValue() == true && rb20.getValue() == true
						&& rb32.getValue() == true && rb41.getValue()!=true && rb50.getValue()==true && rb62.getValue()==true){
					Dialog b2 = new Dialog(
							"You didn't answer the question number 4 right. The right answer is the second answer. Please ensure that you understand all aspects of the game during the test rounds.",
							"ok");
					int left = Window.getClientWidth() / 2;
					int top = Window.getClientHeight() / 2;
					b2.setPopupPosition(left, top);
					b2.show();
					
				}
				//only 5) false
				else if(rb10.getValue() == true && rb20.getValue() == true
						&& rb32.getValue() == true && rb41.getValue()==true && rb50.getValue()!=true && rb62.getValue()==true){
					Dialog b2 = new Dialog(
							"You didn't answer the question number 5 right. The right answer is the first answer. Please ensure that you understand all aspects of the game during the test rounds.",
							"ok");
					int left = Window.getClientWidth() / 2;
					int top = Window.getClientHeight() / 2;
					b2.setPopupPosition(left, top);
					b2.show();
					
				}
				//only 6) false
				else if(rb10.getValue() == true && rb20.getValue() == true
						&& rb32.getValue() == true && rb41.getValue()==true && rb50.getValue()==true && rb62.getValue()!=true){
					Dialog b2 = new Dialog(
							"You didn't answer the question number 6 right. The right answer is the third answer. Please ensure that you understand all aspects of the game during the test rounds.",
							"ok");
					int left = Window.getClientWidth() / 2;
					int top = Window.getClientHeight() / 2;
					b2.setPopupPosition(left, top);
					b2.show();
					
				}else{
					// Two of three calculations are not correct -> reject the
					// participant
					Dialog b2 = new Dialog(
							"Sorry you are not allowed to participate because you didn't fill in enough of the comprehension questions correctly.",
							"ok");
					int left = Window.getClientWidth() / 2;
					int top = Window.getClientHeight() / 2;
					b2.setPopupPosition(left, top);
					b2.show();
					String message= Window.Location.getParameter(GameParameter.hitId)+","+Window.Location.getParameter(GameParameter.assignmentId)+","+Window.Location
							.getParameter(GameParameter.workerId)+","+GameParameter.False+","+0+","+"Sorry you are not allowed to participate because you didn't followed the instructions.";
					w.addMessage(GameParameter.MTurk, message);
					if (Window.Location.getParameter(GameParameter.assignmentId) != null) {
						String assignmentId = Window.Location
								.getParameter(GameParameter.assignmentId);
						String hitId = Window.Location.getParameter(GameParameter.hitId);
						String url = "";
						if (GameParameter.InSandbox) {
							url = "https://workersandbox.mturk.com/mturk/externalSubmit?assignmentId="
									+ assignmentId + "&amp;hitId=" + hitId;
						} else {
							url = "https://www.mturk.com/mturk/externalSubmit?assignmentId="
									+ assignmentId + "&amp;hitId=" + hitId;
						}

						Window.Location.replace(url);

					}
					//return;
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
