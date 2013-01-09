package com.ba.marketUI.client.pages;

import com.ba.marketUI.client.WriterTimeSaver;
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

public class PageFinalQuestionary {

	@SuppressWarnings("deprecation")
	public void loadPage(final WriterTimeSaver w) {
		stopLastTime(w);
		

		final VerticalPanel welcomePanel = new VerticalPanel();
		double result = GameParameter.FinalScore;

		result = round(result, 4);

		final double result2 = result;

		String titel = "Thanks a lot for your participation!";
		String text1 = "We analyse all the results of the research within the next days. In case your results not strongly deviate from them of the other participators your additional bonus will be transferred to your account.";
		String text2 = "If you want to give any feedback or if you have any additional questions please write them in the following text field or send an email to Jessica Hediger (jessica.hediger@uzh.ch)";

		final TextBox box = new TextBox();
		box.setPixelSize(280, 50);

		final Button button = new Button("Terminate the research");
		button.setStyleName("startGameButton");

		button.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {

				// if (Window.Location.getParameter("assignmentId") != null) {
				String messageMTurk = Window.Location
						.getParameter("assignmentId")
						+ " "
						+ Window.Location.getParameter("workerId")
						+ " "
						+ Window.Location.getParameter("hitId")
						+ " "
						+ result2
						+ " Times: "
						+ w.getTimeOverall()
						+ " "
						+ w.getTimeExp1() + " " + w.getTimeExp2()+" " +w.getTimeExp3()+" "+w.getTimeExp4()+" comment:"+box.getValue();
				w.addMessage(GameParameter.MTurk, messageMTurk);
				// }
				w.writeToFile();
				if (Window.Location.getParameter("assignmentId") != null) {
					String assignmentId = Window.Location
							.getParameter("assignmentId");
					String hitId = Window.Location.getParameter("hitId");
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

			}
		});

		Label ti = new Label(titel);
		Label l_text1 = new Label(text1);
		Label l_text2 = new Label(text2);

		ti.addStyleName("titel");

		l_text1.addStyleName("textintro");
		l_text2.addStyleName("textintro");

		welcomePanel.add(ti);
		welcomePanel.add(l_text1);
		welcomePanel.add(l_text2);
		welcomePanel.add(box);
		welcomePanel.add(button);

		welcomePanel.addStyleName("main_panel");

		RootPanel.get("mUI").clear();
		RootPanel.get("mUI").add(welcomePanel);

	}

	private void stopLastTime(WriterTimeSaver w) {
		switch(GameParameter.NumOfExperiment){
		case 1:
			w.stopTimeExp1();
			return;
		case 2:
			w.stopTimeExp2();
			return;
		case 3:
			w.stopTimeExp3();
			return;
		case 4:
			w.stopTimeExp4();
			return;
		default:
			w.stopTimeExp4();
		}
		
	}

	/**
	 * 
	 * @param toRound
	 * @param digit
	 * @return return a double rounded to x digits, for 0 digits: 0.5 ->1 and
	 *         0.4 ->0
	 */
	private double round(double toRound, int digit) {

		boolean isneg = false;
		if (toRound < 0) {
			toRound = toRound * (-1);
			isneg = true;
		}
		digit++;
		int digits = (int) Math.pow(10, digit);
		int valInt = (int) (digits * toRound);

		// to get 0.5 ->1 and 0.4 ->0
		if (valInt % 10 >= 5) {
			valInt = valInt + 10;
		}

		valInt = valInt / 10;
		digit--;
		double valDouble = ((double) valInt) / Math.pow(10, digit);

		if (isneg) {
			valDouble = valDouble * (-1);
		}
		return valDouble;

	}
}
