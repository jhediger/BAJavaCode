package com.ba.marketUI.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
@SuppressWarnings({ "rawtypes", "deprecation" })
public class BAWebApp implements EntryPoint, ValueChangeHandler {

	/********************************************
	 * Parameters to initial games
	 * 
	 */
	// fix
	private Integer timeSteps = 7;
	private Integer max_tokens = 30;
	private int numCategories = 3; // high, medium, low
	private boolean negativeValues = true;

	// TODO: find out how to use:
	// make anything with the speeds..
	private boolean changingChoices = true;
	private boolean reOptimized = false;

	// variable
	private int numPriceLevels = 1;// num of different prices
	private int valueVariation = 3;
	private int numOptions = 2; // to decide how many buttons the game has
	private int maxTimePerRound = 7;

	//private VerticalPanel NavPanel = new VerticalPanel();
	//private Label introduction = new Label();

	private static final int REFRESH_INTERVAL = 1000; // ms

	/**
	 * Create a remote service proxy to talk to the server-side Store service.
	 */
	private ComClientInterfaceAsync dataStoreService = (ComClientInterfaceAsync) GWT
			.create(ComClientInterface.class);

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

		loadWelcomePage();

	}

	private void loadWelcomePage() {
	
		final VerticalPanel welcomePanel = new VerticalPanel();
		final HorizontalPanel description = new HorizontalPanel();

		String text = "Today many people are using a lot of bandwidth with their phones in their everyday live. Some of them perform just simple things like reading the newspaper or browsing through some websites. Others want to do important tasks like writing an urgent email. The Bandwidth Game is based on the assumption that we won\u0027t have enough bandwidth within the near future and thus it needs an effective allocation in case of a shortage in Bandwidth. The game is built to analyze how a potential user acts in this new market environment. It consists of buttons, counters for the rounds, the token, the score and the time, a label that shows the actual task category as well as an underlying market mechanism. It depends on the input parameters if the game has two, three, four, five or six buttons. Each button shows the speed in KB/s, a value in $ and a price in tokens. The player has to choose one of the buttons of which he thinks it is the best choice in each round. If the player hasn\u0027t got enough tokens to pay for the speed of a specific button then the button is not enable. The goal of the game is to spend all tokens during six rounds and to try to score the maximum.  The time counter is an additional difficulty that ensures that a user is forced to choose a button. In case no button is chosen within the maximal time, a mechanism automatically chooses the fourth button with the speed of 0 KB/s. The actual task category is chosen randomly by the underlying mechanism, in order to give a real touch to the game. It also computes speeds, values and prices depending on the actual task category.";

		final Button button = new Button("Start Game");
		button.setStyleName("startGameButton");

		button.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				initialGameLayout();
			}
		});
		
		//Label ti = new Label(titel);
		Label te = new Label(text);
		
		//ti.addStyleName("titel");
		te.addStyleName("text");

		Image i= new Image();
		i.setUrl("images/screenshot4.png");
		
		
		//welcomePanel.add(namePrompt);
		//
		description.add(te);
		description.add(i);
		
		
		welcomePanel.addStyleName("main_panel");
		//welcomePanel.add(ti);
		welcomePanel.add(description);
		welcomePanel.add(button);

		RootPanel.get("mUI").clear();
		RootPanel.get("mUI").add(welcomePanel);
	

	}

	private void initialGameLayout() {

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
		RootPanel.get("mUI").clear();
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
						game.changeState(numOptions - 1);
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
		rounds_left_t.setText("Rounds");
		token_t.setText("Tokens");
		score_t.setText("Score");

		time.setText("0s/7s");

		rounds_left.setText("0/" + timeSteps.toString());
		token.setText("0/" + max_tokens.toString());
		score.setText("$0");

		game = new Game(numCategories, numOptions, numPriceLevels, max_tokens,
				valueVariation, negativeValues, changingChoices, reOptimized,
				timeSteps);

		storeInitialData();
		category.setText("Task Category: " + game.getCurrentCategoryAsString()
				+ " Importance");

		double[] speeds = game.getCurrentSpeeds();
		ArrayList<Double> values = game.getCurrentValues();
		ArrayList<Integer> prices = game.getCurrentPrices();

	
		
		
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
	void storeData(String button) {

		String message = "data"+ game.getCurrentStat().toString();
		message = message + "rounds: " + game.getCurrentRound() + " " + countTime
				+ " " + "budget" + (max_tokens - game.getTokensLeft()) + " "
				+ game.getCurrentScore() + " ";

		message = message + "values:";
		for (double a : game.getCurrentValues()) {
			message = message + a + " ";
		}
		message = message + "prices:";
		for (int b : game.getCurrentPrices()) {
			message = message + b + " ";
		}
		message = message + "speeds:";
		for (double d : game.getCurrentSpeeds()) {
			message = message + String.valueOf(d) + " ";
		}
		message = message + " " + "option:" + button;

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

		int cC;
		int rO;
		int nV;
		if(changingChoices==true){
			cC=1;
		}else{
			cC=0;
		}
		if(reOptimized==true){
			rO=1;
		}else{
			rO=0;
		}
		if(negativeValues==true){
			nV=1;
		}else{
			nV=0;
		}
		String message ="pL|VV|NumOption|maxTimePerRound|timeSteps|cat|changingChoices|reOptimized|negValue initialData: " + numPriceLevels + " "
				+ valueVariation + " " + numOptions + " " + maxTimePerRound + category +" "
				+ " " + timeSteps + " "+cC + " "+rO + " "+ nV;
		message= message + "val"+ game.getallValue() + " price " +game.getallPrices();

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
					+ timeSteps.toString());
			token.setText(game.getTokensLeft().toString() + "/"
					+ max_tokens.toString());

			if (game.getCurrentScore().toString().length() > 5) {
				game.setCurrentScoreUP();
		
			} 
			score.setText("$" + game.getCurrentScore().toString());
			

			category.setText("Task Category: "
					+ game.getCurrentCategoryAsString() + " Importance");

			double[] speeds = game.getCurrentSpeeds();
			ArrayList<Double> values = game.getCurrentValues();
			ArrayList<Integer> prices = game.getCurrentPrices();

			for (int i = 0; i < game_buttons.size(); i++) {
				game_buttons.get(i).setHTML(
						"Speed: " + String.valueOf(speeds[i]) + " KB/s"
								+ "<br>Value: $" + values.get(i).toString()
								+ "<br>Price: " + prices.get(i).toString());
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
				game.setCurrentScoreUP();
			} 
				score.setText("$" + game.getCurrentScore().toString());
			
			rounds_left.setText(game.getCurrentRound().toString() + "/"
					+ timeSteps.toString());

			for (Button b : game_buttons) {
				b.setEnabled(false);
			}

			start_new_game.setEnabled(true);
			storeDataFinalRound();

		}

	}

	@Override
	public void onValueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}