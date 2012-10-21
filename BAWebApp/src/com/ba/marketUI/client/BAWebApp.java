package com.ba.marketUI.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BAWebApp implements EntryPoint {

	// Parameters to initial games
	// fix
	private int timeSteps = 6;
	private int maxBudget = 30;
	private int numCategories = 3; // high, medium, low
	private boolean negativeValues = true;
	
	// TODO: find out how to use:
	// make anything with the speeds..
	private boolean changingChoices = true;
	private boolean reOptimized = false;

	// variable
	private int numPriceLevels = 3;// num of different prices
	private int valueVariation = 1;// if 1 then 3 different values
	private int numOptions = 4; // to decide how many buttons the game has
	private int maxTimePerRound = 7;
	// int stepSize = 100;
	// int maxSpeed = 1000;
	// double maxValue = 3.1;



	private static final int REFRESH_INTERVAL = 1000; // ms

	/**
	 * Create a remote service proxy to talk to the server-side Store service.
	 */
	private ComClientInterfaceAsync dataStoreService = (ComClientInterfaceAsync) GWT
			.create(ComClientInterface.class);

	private Integer max_rounds = 6;
	private Integer max_tokens = 30;

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
	private List<Button> game_buttons = new ArrayList<Button>();

	private Button start_new_game = new Button();

	private Integer countTime = 0;

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

		for (int i = 0; i < numOptions; i++) {
			game_buttons.add(new Button());
			buttons_panel.add(game_buttons.get(i));
		}

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

		initialTestGame();

		// Associate the Main panel with the HTML host page.
		RootPanel.get("mUI").add(main_panel);

		// Setup timer to refresh list automatically.
		Timer refreshTimer = new Timer() {

			@Override
			public void run() {
				refreshWatchList();
			}

			private void refreshWatchList() {
				if (game.notFinish()) {
					countTime++;
					if (countTime > maxTimePerRound) {
						storeData(String.valueOf(numOptions - 1));
						game.changeState(numOptions-1);
						changeGameState();
						setTimeToZero();
						
					}
					time.setText(countTime.toString() + "/"
							+ String.valueOf(maxTimePerRound) + "s");
				}
			}
		};

		refreshTimer.scheduleRepeating(REFRESH_INTERVAL);

		// Listen for mouse events on the Add button.
		for (final Button b : game_buttons) {
			b.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					storeData(String.valueOf(game_buttons.indexOf(b)));
					game.changeState(game_buttons.indexOf(b));
					changeGameState();
					setTimeToZero();
				}
			});
		}

		start_new_game.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				game.setNew();
				changeGameState();
				for (Button b : game_buttons) {
					b.setEnabled(true);
				}
				start_new_game.setEnabled(false);
				setTimeToZero();
			}
		});

	}

	private void initialTestGame() {
		
		time_t.setText("Time");
		rounds_left_t.setText("Rounds Left");
		token_t.setText("Tokens");
		score_t.setText("Score");

		time.setText("0s/7s");

		rounds_left.setText("0/" + max_rounds.toString());
		token.setText("0/" + max_tokens.toString());
		score.setText("$0");

		game = new Game(numCategories, numOptions, numPriceLevels,
				maxBudget, valueVariation, negativeValues, changingChoices,
				reOptimized);

		storeInitialData();
		category.setText("Task Category: " + game.getCurrentCategoryAsString()
				+ " Importance");

		double[] speeds = game.getCurrentSpeeds();
		ArrayList<Double> values = game.getCurrentValues();
		ArrayList<Integer> prices = game.getCurrentPrices();

		for (int i = 0; i < game_buttons.size(); i++) {
			game_buttons.get(i).setHTML(
					"Speed: " + String.valueOf(speeds[i]) + "<br>Value: "
							+ values.get(i).toString() + "<br>Price: "
							+ prices.get(i).toString());
		}

	}

	/**
	 * Make a GWT-RPC call to the server. The myEmailService class member was
	 * initalized when the module started up.
	 * 
	 * @param button
	 *            0 for top button
	 * 
	 */
	void storeData(String button) {

		String message = game.getNumOfOptions() + " " + game.getCurrentRound()
				+ " " + countTime + " " + game.getTokensLeft() + " "
				+ game.getCurrentScore() + " ";
		for (double d : game.getCurrentSpeeds()) {
			message = message + String.valueOf(d) + " ";
		}
		for (double a : game.getCurrentValues()) {
			message = message + a + " ";
		}
		for (int b : game.getCurrentPrices()) {
			message = message + b + " ";
		}
		message = message + " " + "option: " + button;

		try {
			dataStoreService.myMethod(message, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Fail
					Window.alert("Fehler 1: RPC to sendEmail() failed.");

				}

				@Override
				public void onSuccess(Void result) {
					// Window.alert("RPC to sendEmail() succed.");

				}

			});
		} catch (IOException e) {
			// TODO fail
			// TODO Auto-generated catch block
			Window.alert("Fehler 2: RPC to sendEmail() failed.");
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	void storeInitialData() {

		String message = "initialData: " + numPriceLevels + " "
				+ valueVariation + " " + numOptions + " " + maxTimePerRound;

		try {
			dataStoreService.myMethod(message, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Fail
					Window.alert("Fehler 1: RPC to sendEmail() failed.");

				}

				@Override
				public void onSuccess(Void result) {
					// Window.alert("RPC to sendEmail() succed.");

				}

			});
		} catch (IOException e) {
			// TODO fail
			// TODO Auto-generated catch block
			Window.alert("Fehler 2: RPC to sendEmail() failed.");
			e.printStackTrace();
		}
	}

	void storeDataFinalRound() {

		String message = "finalResult: " + game.getTokensLeft() + " "
				+ game.getCurrentScore() + " ";

		try {
			dataStoreService.myMethod(message, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Fail
					Window.alert("Fehler 1: RPC to sendEmail() failed.");

				}

				@Override
				public void onSuccess(Void result) {
					// Window.alert("RPC to sendEmail() succed.");

				}

			});
		} catch (IOException e) {
			// TODO fail
			// TODO Auto-generated catch block
			Window.alert("Fehler 2: RPC to sendEmail() failed.");
			e.printStackTrace();
		}
	}

	private void setTimeToZero() {
		countTime = 0;
	}

	private void changeGameState() {

		if (game.notFinish()) {

			rounds_left.setText(game.getCurrentRound().toString() + "/"
					+ max_rounds.toString());
			token.setText(game.getTokensLeft().toString() + "/"
					+ max_tokens.toString());

			if (game.getCurrentScore().toString().length() > 5) {
				score.setText("$"
						+ game.getCurrentScore().toString().substring(0, 4));
			} else {
				score.setText("$" + game.getCurrentScore().toString());
			}

			category.setText("Task Category: "
					+ game.getCurrentCategoryAsString() + " Importance");

			double[] speeds = game.getCurrentSpeeds();
			ArrayList<Double> values = game.getCurrentValues();
			ArrayList<Integer> prices = game.getCurrentPrices();

			for (int i = 0; i < game_buttons.size(); i++) {
				game_buttons.get(i).setHTML(
						"Speed: " + String.valueOf(speeds[i]) + "<br>Value: "
								+ values.get(i).toString() + "<br>Price: "
								+ prices.get(i).toString());
			}

			for (int i = 0; i < game.disableButton().size(); i++) {
				if (game.disableButton().get(i) == true) {
					game_buttons.get(i).setEnabled(false);
				}
			}

		} else {
			token.setText(game.getTokensLeft().toString() + "/"
					+ max_tokens.toString());
			if (game.getCurrentScore().toString().length() > 5) {
				score.setText("$"
						+ game.getCurrentScore().toString().substring(0, 4));
			} else {
				score.setText("$" + game.getCurrentScore().toString());
			}
			rounds_left.setText(game.getCurrentRound().toString() + "/"
					+ max_rounds.toString());

			for (Button b : game_buttons) {
				b.setEnabled(false);
			}

			start_new_game.setEnabled(true);
			storeDataFinalRound();

		}

	}

}