package com.ianmann.chess.gui;

import java.io.IOException;
import java.net.URL;

import com.ianmann.chess.game.Game;
import com.ianmann.chess.game.TeamColor;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderRepeat;
import javafx.scene.layout.VBox;

public class GameScreen {
	
	/**
	 * The theme that this screen displays with.
	 */
	public final String theme;
	
	/**
	 * The game that this screen displays information for.
	 */
	public final Game game;
	
	/**
	 * The actual {@link BoarderPane} that this object wraps and controls.
	 */
	public final BorderPane value;
	
	/**
	 * URL that points to the game screen fxml file.
	 */
	private final URL fxmlFile = this.getClass().getResource("/com/ianmann/chess/gui/fxml/GameScreen.fxml");
	
	/**
	 * The board that this screen can display.
	 */
	private BoardSquareContainer boardDisplay;
	
	/**
	 * Display for the white team's grave.
	 */
	private GravePane whiteGraveDisplay;
	
	/**
	 * Display for the black team's grave.
	 */
	private GravePane blackGraveDisplay;
	
	/**
	 * Set and initialize the attributes of this controller
	 * @param _game
	 * @throws IOException If the screen could not be loaded.
	 */
	public GameScreen(Game _game, String _theme) throws IOException {
		this.game = _game;
		this.theme = _theme;
		
		this.value = FXMLLoader.load(this.fxmlFile);
		this.showBoard();
		this.showGraves();
	}
	
	/**
	 * Shows the board on the screen. If the board does not exist yet, it is created and
	 * initialized from this screen wrappers game.
	 */
	public void showBoard() {
		if (this.boardDisplay == null) {
			this.boardDisplay = new BoardSquareContainer(this);
			this.value.setCenter(this.boardDisplay);
		} else {
			this.boardDisplay.setVisible(true);
		}
	}
	
	/**
	 * Adds the black and white graves to the screen.
	 */
	private void showGraves() {
		if (this.whiteGraveDisplay == null) {
			this.whiteGraveDisplay = new GravePane(this, TeamColor.WHITE, (VBox) this.value.lookup("#whiteGraveDisplay"));
		} else {
			this.whiteGraveDisplay.value.setVisible(true);
		}
		
		if (this.blackGraveDisplay == null) {
			this.blackGraveDisplay = new GravePane(this, TeamColor.BLACK, (VBox) this.value.lookup("#blackGraveDisplay"));
		} else {
			this.blackGraveDisplay.value.setVisible(true);
		}
	}
	
	public void refreshGraves() {
		this.whiteGraveDisplay.renderGrave();
		this.blackGraveDisplay.renderGrave();
	}

}
