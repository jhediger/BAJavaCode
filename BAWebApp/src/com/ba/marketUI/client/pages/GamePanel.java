package com.ba.marketUI.client.pages;

import java.util.ArrayList;
import java.util.List;

import com.ba.marketUI.client.Game;
import com.ba.marketUI.client.WriterTimeSaver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GamePanel {

	// private double scoreOverRound = 0.0;

	private static final int REFRESH_INTERVAL = 1000; // ms

	/**
	 * Create a remote service proxy to talk to the server-side Store service.
	 */

	private VerticalPanel main_panel = new VerticalPanel();
	private VerticalPanel buttons_panel = new VerticalPanel();
	private HorizontalPanel gamestates_panel = new HorizontalPanel();
	private VerticalPanel time_panel = new VerticalPanel();
	private VerticalPanel rounds_panel = new VerticalPanel();
	private VerticalPanel token_panel = new VerticalPanel();
	private VerticalPanel score_panel = new VerticalPanel();

	private Label numOfExpGames = new Label();
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

	private Integer countTime = 0;

	private Integer maxRounds = GameParameter.TimeSteps;
	
	private boolean storeData;

	private WriterTimeSaver w;

	private int numLastRound=1;
	
	private Integer timeForGames = 0; // overall time for all 6 rounds in
										// seconds

	private Integer round=1;

	public GamePanel(boolean isNotestGame, WriterTimeSaver w) {

		this.w = w;
		storeData = isNotestGame;
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

		for (int i = 0; i < GameParameter.NumOptions; i++) {
			game_buttons.add(new Button());
			buttons_panel.add(game_buttons.get(i));
		}

		main_panel.add(numOfExpGames);
		main_panel.add(gamestates_panel);
		main_panel.add(category);
		main_panel.add(buttons_panel);

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
		numOfExpGames.addStyleName("black_label");

		initialGame();

		// Setup timer to refresh automatically.
		Timer refreshTimer = new Timer() {

			private Integer intf;

			@Override
			public void run() {
				refreshWatchList();
			}

			private void refreshWatchList() {
				if (isNotFinish()) {
					countTime++;
					if (countTime > GameParameter.MaxTimePerRound) {
						storeData(String.valueOf(GameParameter.NumOptions - 1),round);
						GameParameter.game
								.changeState(GameParameter.NumOptions - 1);
						
						changeGameState();
						setTimeToZero();
					}

					time.setText(countTime.toString() + "/"
							+ String.valueOf(GameParameter.MaxTimePerRound)
							+ "s");
				}
			}

		};

		refreshTimer.scheduleRepeating(REFRESH_INTERVAL);

		// Listen for mouse events on the Add button.
		for (final Button b : game_buttons) {
			b.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					storeData(String.valueOf(game_buttons.indexOf(b)),round);
					GameParameter.game.changeState(game_buttons.indexOf(b));
					changeGameState();
					setTimeToZero();
				}
			});
		}

	}

	private void initialGame() {

		if (storeData == true) {
			numOfExpGames.setText("You have already played "
					+ GameParameter.NumOfExpGames + " of "
					+ GameParameter.MaxNumOfExpGames + " games");
		}

		time_t.setText("Time");
		rounds_left_t.setText("Rounds");
		token_t.setText("Tokens");
		score_t.setText("Score");

		time.setText("0s/" + GameParameter.MaxTimePerRound + "s");

		rounds_left.setText(round+"/" + GameParameter.TimeSteps.toString());
		token.setText("0/" + GameParameter.Tokens.toString());
		score.setText("$0");

		if (GameParameter.ReOptimized && GameParameter.Speeds != null) {
			GameParameter.game = new Game(GameParameter.Categories,
					GameParameter.NumOptions, GameParameter.NumPriceLevels,
					GameParameter.Tokens, GameParameter.ValueVariation,
					GameParameter.NegativeValues,
					GameParameter.ChangingChoices, GameParameter.ReOptimized,
					GameParameter.Speeds, GameParameter.TimeSteps);
		} else {
			GameParameter.game = new Game(GameParameter.Categories,
					GameParameter.NumOptions, GameParameter.NumPriceLevels,
					GameParameter.Tokens, GameParameter.ValueVariation,
					GameParameter.NegativeValues,
					GameParameter.ChangingChoices, GameParameter.ReOptimized,
					GameParameter.TimeSteps);
		}
		storeInitialData();
		category.setText("Task Category: "
				+ GameParameter.game.getCurrentCategoryAsString()
				+ " Importance");

		double[] speeds = GameParameter.game.getCurrentSpeeds();
		ArrayList<Double> values = GameParameter.game.getCurrentValues();
		ArrayList<Integer> prices = GameParameter.game.getCurrentPrices();

		for (int i = 0; i < game_buttons.size(); i++) {
			game_buttons.get(i).setHTML(
					"Speed: " + String.valueOf(speeds[i]) + " KB/s"
							+ "<br>Value: $" + values.get(i).toString()
							+ "<br>Price: " + prices.get(i).toString());
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
	void storeData(String button, int round) {

		if (storeData == true) {
			String message = "currentNumOptions|currentRound|budgetLeft|currentCategory|currentValueVariation|currentPriceLevel  data"
					+ GameParameter.game.getCurrentStat(round).toString();
			message = message + "," + countTime + "," + "option:" + button;

			message = message + "values:";
			for (double a : GameParameter.game.getCurrentValues()) {
				message = message + a + " ";
			}
			message = message + "prices:";
			for (int b : GameParameter.game.getCurrentPrices()) {
				message = message + b + " ";
			}
			message = message + "speeds:";
			for (double d : GameParameter.game.getCurrentSpeeds()) {
				message = message + String.valueOf(d) + " ";
			}
			sendMessage(message, GameParameter.GameData);
		}
	}

	/**
	 *
	 */
	void storeInitialData() {
		if (storeData == true) {
			int cC;
			int rO;
			int nV;
			if (GameParameter.ChangingChoices == true) {
				cC = 1;
			} else {
				cC = 0;
			}
			if (GameParameter.ReOptimized == true) {
				rO = 1;
			} else {
				rO = 0;
			}
			if (GameParameter.NegativeValues == true) {
				nV = 1;
			} else {
				nV = 0;
			}
			// Calendar cal = Calendar.getInstance();
			// SimpleDateFormat formater = new SimpleDateFormat();
			String message = Window.Location
					.getParameter(GameParameter.workerId)
					+ ","
					+ ","
					+ "pL|VV|NumOption|maxTimePerRound|timeSteps|cat|changingChoices|reOptimized|negValue initialData: "
					+ ","
					+ GameParameter.NumPriceLevels
					+ ","
					+ GameParameter.ValueVariation
					+ ","
					+ GameParameter.NumOptions
					+ ","
					+ GameParameter.MaxTimePerRound
					+ ","
					+ GameParameter.Categories
					+ ","
					+ GameParameter.TimeSteps
					+ "," + rO + "," + cC + "," + nV;
			message = message + "val" + GameParameter.game.getallValue()
					+ " price " + GameParameter.game.getallPrices();

			sendMessage(message, GameParameter.GameData);
		}

	}

	void storeDataFinalRound() {
		if (storeData == true) {
			String message = "finalResult:,"
					+ GameParameter.game.getTokensLeft() + ","
					+ GameParameter.game.getCurrentScore() + ","
					+ GameParameter.Score + "," + timeForGames;
			sendMessage(message, GameParameter.GameData);

		}

	}

	private void sendMessage(String message, String fileName) {

		w.addMessage(fileName, message);
	}

	private void setTimeToZero() {
		countTime = 0;
	}

	private void changeGameState() {
		round++;
		if (isNotFinish()) {

			timeForGames += countTime;
			//if(GameParameter.game.getCurrentRound()==numLastRound+1){
			rounds_left.setText(round.toString()
					+ "/" + GameParameter.TimeSteps.toString());
			//}else(GameParameter.game.setCurrentRound(numLastRound){
				
			//}
			token.setText(GameParameter.game.getTokensLeft().toString() + "/"
					+ GameParameter.Tokens.toString());

			if (GameParameter.game.getCurrentScore().toString().length() > 5) {
				GameParameter.game.setCurrentScoreUP();

			}
			score.setText("$" + GameParameter.game.getCurrentScore().toString());

			category.setText("Task Category: "
					+ GameParameter.game.getCurrentCategoryAsString()
					+ " Importance");

			double[] speeds = GameParameter.game.getCurrentSpeeds();
			ArrayList<Double> values = GameParameter.game.getCurrentValues();
			ArrayList<Integer> prices = GameParameter.game.getCurrentPrices();

			for (int i = 0; i < game_buttons.size(); i++) {
				game_buttons.get(i).setHTML(
						"Speed: " + String.valueOf(speeds[i]) + " KB/s"
								+ "<br>Value: $" + values.get(i).toString()
								+ "<br>Price: " + prices.get(i).toString());
			}

			for (int i = 0; i < GameParameter.game.disableButton().size(); i++) {
				if (GameParameter.game.disableButton().get(i) == true) {
					game_buttons.get(i).setEnabled(false);
				}
			}

		} else {
			token.setText(GameParameter.game.getTokensLeft().toString() + "/"
					+ GameParameter.Tokens.toString());
			if (GameParameter.game.getCurrentScore().toString().length() > 5) {
				GameParameter.game.setCurrentScoreUP();
			}
			score.setText("$" + GameParameter.game.getCurrentScore().toString());

			rounds_left.setText(round.toString()
					+ "/" + GameParameter.TimeSteps.toString());

			for (Button b : game_buttons) {
				b.setEnabled(false);
			}

			if (storeData == true) {
				GameParameter.NumOfExpGames++;
				w.setScoreExp(round(GameParameter.game.getCurrentScore(), 4));
				// scoreOverRound += GameParameter.game.getCurrentScore();
				storeDataFinalRound();
				//Window.alert(GameParameter.NumOfExpGames+" "+GameParameter.MaxNumOfExpGames);
				if (GameParameter.MaxNumOfExpGames
						- GameParameter.NumOfExpGames != 0) {
					// Window.alert(GameParameter.RoundsToPlay
					// - counterOfGames + "'rounds to play left");
					w.stopTime();
					w.startTime();
					startNewGame();
				} else {
					w.stopTime();
					// scoreOverRound = round(scoreOverRound, 4);
					sendMessage("Experiment part are over, #buttons: "
							+ GameParameter.NumOptions, GameParameter.GameData);
					// Window.alert("Game is finish. Your overall score is:"
					// + scoreOverRound);
					goToLastPage();
				}

				numOfExpGames.setText("You have already played "
						+ GameParameter.NumOfExpGames + " of "
						+ GameParameter.MaxNumOfExpGames + " games");

			}

		}

	}

	public Widget getPanel() {
		return main_panel;
	}

	public void startNewGame() {
		GameParameter.game.setNew();
		round=0;
		changeGameState();
		for (Button b : game_buttons) {
			b.setEnabled(true);
		}
		setTimeToZero();
		

	}

	private void goToLastPage() {
		// GameParameter.FinalScore += scoreOverRound;
		PageFinalQuestionary fq = new PageFinalQuestionary();
		fq.loadPage(w);
		/*
		 * if (GameParameter.NumOfExperiment >=
		 * GameParameter.MaxNumOfExperiment) {
		 * 
		 * }else{ PageSetExperiment se = new PageSetExperiment(w);
		 * se.loadPage(); }
		 */
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
	
	private boolean isNotFinish(){
		if(maxRounds < round){
				return false;
			}
		return true;
	}

}
