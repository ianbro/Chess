package com.ianmann.chess.gui;

import java.util.ArrayList;
import java.util.HashMap;

import com.ianmann.chess.game.Board;
import com.ianmann.chess.game.Game;
import com.ianmann.chess.game.movement.Square;

import javafx.scene.image.Image;
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
	 * A list of squares in this board.
	 */
	private ArrayList<SquarePane> squares;
	
	/**
	 * The back-end representation of this game board.
	 */
	public final Board backend;
	
	/**
	 * The game that this board display is for.
	 */
	public final Game game;
	
	/**
	 * Designates where the user has selected to move from. This is set when they click a
	 * piece on the board that belongs to them.
	 */
	private SquarePane fromSelection;
	
	/**
	 * Designates where the user has selected to move to. This is set when they click a
	 * square on the board after selecting a square to move from and that square is a
	 * valid destination for the piece on fromSelection.
	 */
	private SquarePane toSelection;
	
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
	public BoardSquareContainer(Game _game, String _theme, Board _backend) {
		this.game = _game;
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
		this.squares = new ArrayList<SquarePane>();
		for (Square square : this.backend.squares.values()) {
			SquarePane squareDisplay = new SquarePane(this, square);
			GridPane.setConstraints(squareDisplay, square.x, square.y);
			this.getChildren().add(squareDisplay);
			this.squares.add(squareDisplay);
		}
		System.out.println(this.game.getBoard().toBoardString());
	}
	
	/**
	 * Loops through each square in this board and draws it again. This is to refresh the
	 * board after each move.
	 */
	private void refreshSquares() {
		for (SquarePane square : this.squares) {
			square.render();
		}
		System.out.println(this.game.getBoard().toBoardString());
	}
	
	/**
	 * <p>
	 * Takes a square and decides if it is going to be the square that a move will originate
	 * from or a square that a move will end at.
	 * </p>
	 * <p>
	 * If {@link this#fromSelection} has not been set yet or if _displayClicked has a piece of
	 * the current players color, then _displayClicked will be set to {@link this#fromSelection}.
	 * </p>
	 * <p>
	 * Otherwise, if {@link this#fromSelection} has been set and _displayClicked is a valid move
	 * for the piece in {@link this#fromSelection}, then it is set to _displayClicked. If this
	 * was done successful, the move is done where @link this#fromSelection} is used as the
	 * original square and {@link this#fromSelection} is used as the destination square.
	 * </p>
	 * <p>
	 * If the move was successful, then the squares on the board are re-drawn, the squares pointed
	 * to by {@link this#toSelection} and {@link this#fromSelection} are reset to null and the next
	 * turn is started.
	 * </p>
	 * @param _displayClicked
	 */
	public void inputSquareClick(SquarePane _displayClicked) {
		if (this.fromSelection == null || _displayClicked.backend.hasPiece(this.game.getCurrentTurnTeam())) {
			if (!_displayClicked.backend.hasPiece(this.game.getCurrentTurnTeam()))
				return;
			
			if (_displayClicked.backend.hasPiece()) {
				this.fromSelection = _displayClicked;
			}
		} else {
			if (!this.fromSelection.backend.getPiece().canMove(_displayClicked.backend))
				return;
			
			this.toSelection = _displayClicked;
			boolean turnSuccessful = this.game.takeTurn(this.fromSelection.backend, this.toSelection.backend);
			if (turnSuccessful) {
				this.refreshSquares();
				this.fromSelection = null;
				this.toSelection = null;
				this.game.beginNextTurn();
			}
		}
	}
}
