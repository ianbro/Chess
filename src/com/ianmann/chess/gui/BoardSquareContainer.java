package com.ianmann.chess.gui;

import java.util.HashMap;

import com.ianmann.chess.game.Board;
import com.ianmann.chess.game.movement.Square;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;

/**
 * <p>
 * Pane class that contains the display for the game board. This is just the front-end
 * representation of the board but it has a pointer to the {@link Board} object that
 * represents the back-end representation of the board.
 * </p>
 * <p>
 * This only contains the grid for the board and the squares.
 * </p>
 * @author ian
 *
 */
public class BoardSquareContainer extends GridPane {
	
	/**
	 * The theme of this board.
	 */
	public final String theme;
	
	/**
	 * The background image that is behind each square.
	 */
	private final Image backgroundImage;
	
	/**
	 * The width and height of each {@link SquarePane} in this board display.
	 */
	public final int squareSize;

	/**
	 * A key-value pair that contains pointers to each square that this board contains.
	 * Each key is a square's coordinate and it maps to the corresponding {@link SquarePane}
	 * at that coordinate.
	 */
	private HashMap<String, SquarePane> squares;
	
	/**
	 * The back-end representation of this game board.
	 */
	public final Board backend;
	
	/**
	 * <p>
	 * Instantiate a container for the board squares. For more information
	 * on this, see {@link BoardSquareContainer}.
	 * </p>
	 * <p>
	 * This sets the backend data, the background and the size of this board along.
	 * </p>
	 * @param _theme
	 * @param _backend
	 */
	public BoardSquareContainer(String _theme, Board _backend) {
		this.backend = _backend;
		this.theme = _theme;
		this.squareSize = 75;
		
		this.backgroundImage = new Image(this.getClass().getResourceAsStream(
				"/resources/themes/" + this.theme + "/boardBackground.jpg"));
		this.setBackground(new Background(new BackgroundImage(this.backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, true))));
		
		this.generateSquareDisplays();
	}
	
	/**
	 * Adds a display to this board pane for each square in this board pane's back-end.
	 */
	private void generateSquareDisplays() {
		this.squares = new HashMap<String, SquarePane>();
		for (Square square : this.backend.squares.values()) {
			SquarePane squareDisplay = new SquarePane(this, square);
			GridPane.setConstraints(squareDisplay, square.x, square.y);
			this.getChildren().add(squareDisplay);
			this.squares.put(square.coordinateString(), squareDisplay);
		}
	}
}
