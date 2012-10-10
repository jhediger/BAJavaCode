package com.ba.marketUI.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.dev.util.log.*;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BAWebApp implements EntryPoint {

	// private int time_per_round = 7;
	private Integer max_rounds = 6;
	private Integer max_tokens = 30;
	private Integer game_score = 0;

	private VerticalPanel main_panel = new VerticalPanel();
	private VerticalPanel buttons_panel = new VerticalPanel();
	private HorizontalPanel gamestates_panel = new HorizontalPanel();
	private VerticalPanel time_panel = new VerticalPanel();
	private VerticalPanel rounds_panel = new VerticalPanel();
	private VerticalPanel token_panel = new VerticalPanel();
	private VerticalPanel score_panel = new VerticalPanel();

	private Label category = new Label();
	private Label time_t = new Label();
	private Label rounds_left_t = new Label();
	private Label token_t = new Label();
	private Label score_t = new Label();
	private Label time = new Label();
	private Label rounds_left = new Label();
	private Label token = new Label();
	private Label score = new Label();
	private Button game_button_1 = new Button();
	private Button game_button_2 = new Button();
	private Button game_button_3 = new Button();
	private Button game_button_4 = new Button();
	private Button start_new_game = new Button();

	private Game game;

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {

		gamestates_panel.add(time_panel);
		gamestates_panel.add(rounds_panel);
		gamestates_panel.add(token_panel);
		gamestates_panel.add(score_panel);

		time_panel.add(time_t);
		time_panel.add(time);
		rounds_panel.add(rounds_left_t);
		rounds_panel.add(rounds_left);
		token_panel.add(token_t);
		token_panel.add(token);
		score_panel.add(score_t);
		score_panel.add(score);

		buttons_panel.add(game_button_1);
		buttons_panel.add(game_button_2);
		buttons_panel.add(game_button_3);
		buttons_panel.add(game_button_4);

		main_panel.add(gamestates_panel);
		main_panel.add(category);
		main_panel.add(buttons_panel);
		main_panel.add(start_new_game);
		

		main_panel.addStyleName("main_panel");
		buttons_panel.addStyleName("buttons_panel");

		gamestates_panel.addStyleName("gamestates_panel");
		rounds_panel.addStyleName("rounds_panel");

		category.addStyleName("category");
		time_t.addStyleName("black_label");
		rounds_left_t.addStyleName("black_label");
		token_t.addStyleName("black_label");
		score_t.addStyleName("black_label");
		time.addStyleName("black_label");
		rounds_left.addStyleName("black_label");
		token.addStyleName("black_label");
		score.addStyleName("black_label");
		
		start_new_game.setEnabled(false);
		start_new_game.setText("Start new Game");
		start_new_game.setStyleName("newGameButton");

		// TODO: Testgame
		initialTestGame();

		// Associate the Main panel with the HTML host page.
		RootPanel.get("mUI").add(main_panel);

		// Listen for mouse events on the Add button.
		game_button_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				game.changeState(0);
				changeGameState();
			}
		});

		game_button_2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				game.changeState(1);
				changeGameState();
			}
		});

		game_button_3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				game.changeState(2);
				changeGameState();
			}
		});

		game_button_4.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				game.changeState(3);
				changeGameState();
			}
		});
		
		start_new_game.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				game.setNew();
				changeGameState();
				game_button_4.setEnabled(true);
				game_button_1.setEnabled(true);
				game_button_2.setEnabled(true);
				game_button_3.setEnabled(true);
				start_new_game.setEnabled(false);
			}
		});	
		

	}

	private void initialTestGame() {
		time_t.setText("Time");
		rounds_left_t.setText("Rounds Left");
		token_t.setText("Tokens");
		score_t.setText("Score");

		time.setText("5s/7s");

		rounds_left.setText("0/" + max_rounds.toString());
		token.setText("0/" + max_tokens.toString());
		score.setText("$0");

		int timeSteps = 6;
		int numPriceLevels = 3;
		int maxBudget = 30;
		boolean negativeValues = true;
		int stepSize = 100;
		boolean reOptimized = false;
		int numCategories = 3;
		int maxSpeed = 1000;
		double maxValue = 3.1;
		int numOptions = 4;
		int valueVariation= 1;
		//make anything with the speeds..
		boolean changingChoices= true;

		
		game = new Game(timeSteps,numCategories, numOptions, numPriceLevels, maxBudget, valueVariation, negativeValues, changingChoices, reOptimized);
		

		category.setText("Task Category: " + game.getCurrentCategoryAsString()
				+ " Importance");

		double[] speeds = game.getCurrentSpeeds();
		ArrayList<Double> values = game.getCurrentValues();
		ArrayList<Integer> prices = game.getCurrentPrices();

		game_button_1.setHTML("Speed: " + String.valueOf(speeds[0]) + "<br>Value: "
				+ values.get(0).toString() + "<br>Price: " + prices.get(0).toString());
		game_button_2.setHTML("Speed: " + String.valueOf(speeds[1]) + "<br>Value: "
				+ values.get(1).toString() + "<br>Price: " + prices.get(1).toString());
		game_button_3.setHTML("Speed: " + String.valueOf(speeds[2]) + "<br>Value: "
				+ values.get(2).toString() + "<br>Price: " + prices.get(2).toString());
		game_button_4.setHTML("Speed: " + String.valueOf(speeds[3]) + "<br>Value: "
				+ values.get(3).toString() + "<br>Price: " + prices.get(3).toString());
		

	}

	private void changeGameState() {

		if (game.notFinish()) {

			//game.fileWriter(4);
			
			rounds_left.setText(game.getRoundsLeft().toString() + "/"
					+ max_rounds.toString());
			token.setText(game.getTokensLeft().toString() + "/"
					+ max_tokens.toString());
			
			score.setText("$" + game.getCurrentScore().toString().substring(0,5));

			category.setText("Task Category: "
					+ game.getCurrentCategoryAsString() + " Importance");

			double[] speeds = game.getCurrentSpeeds();
			ArrayList<Double> values = game.getCurrentValues();
			ArrayList<Integer> prices = game.getCurrentPrices();

			game_button_1.setHTML("Speed: " + String.valueOf(speeds[0]) + "<br>Value: "
					+ values.get(0).toString() + "<br>Price: " + prices.get(0).toString());
			game_button_2.setHTML("Speed: " + String.valueOf(speeds[1]) + "<br>Value: "
					+ values.get(1).toString() + "<br>Price: " + prices.get(1).toString());
			game_button_3.setHTML("Speed: " + String.valueOf(speeds[2]) + "<br>Value: "
					+ values.get(2).toString() + "<br>Price: " + prices.get(2).toString());
			game_button_4.setHTML("Speed: " + String.valueOf(speeds[3]) + "<br>Value: "
					+ values.get(3).toString() + "<br>Price: " + prices.get(3).toString());


			boolean[] dis_buttons = game.disableButton();
			if (dis_buttons[0] == true) {
				game_button_1.setEnabled(false);
			}
			if (dis_buttons[1] == true) {
				game_button_2.setEnabled(false);
			}
			if (dis_buttons[2] == true) {
				game_button_3.setEnabled(false);
			}
			if (dis_buttons[3] == true) {
				game_button_4.setEnabled(false);
			}

		} else {
			token.setText(game.getTokensLeft().toString() + "/"
					+ max_tokens.toString());
			score.setText("$" + game.getCurrentScore().toString().substring(0,5));
			rounds_left.setText(game.getRoundsLeft().toString() + "/"
					+ max_rounds.toString());
			game_button_1.setEnabled(false);
			game_button_2.setEnabled(false);
			game_button_3.setEnabled(false);
			
			game_button_4.setEnabled(false);
			start_new_game.setEnabled(true);
		}

	}

}