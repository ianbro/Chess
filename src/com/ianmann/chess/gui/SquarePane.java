package com.ianmann.chess.gui;

import com.ianmann.chess.game.movement.Square;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Pane class that contains the display for a square on the board. This is just the front-end
 * representation of a square but it has a pointer to the {@link Square} object that represents
 * the back-end representation of the square.
 * @author ian
 *
 */
public class SquarePane extends AnchorPane {

	/**
	 * The frame on the square pane that contains the image of the square.
	 */
	private ImageView imageFrame;
	
	/**
	 * The image that is the actual display for the square.
	 */
	private Image image;
	
	/**
	 * The actual back-end object that represents this square. This {@link SquarePane}
	 * only represents the display but this {@link Square} object contains the data
	 * that is represented by this square.
	 */
	public final Square backend;
	
	/**
	 * The square container that contains this square.
	 */
	public final BoardSquareContainer parentContainer;
	
	/**
	 * Instantiates a pane that displays a single square. This sets the images
	 * in the square that make up the display.
	 * @param _theme
	 */
	public SquarePane(BoardSquareContainer _parentContainer, Square _backend) {
		this.parentContainer = _parentContainer;
		this.backend = _backend;
		
		this.setOnMouseClicked(new SquareClickedHandler(this));
		
		this.render();
	}
	
	/**
	 * Draws the corresponding piece that is in this square on the display for this
	 * square.
	 */
	public void render() {
		this.getChildren().clear();
		
		this.image = new Image(getClass().getResourceAsStream(
				"/resources/themes/" + this.parentContainer.theme + "/square.png"));
		this.imageFrame = new ImageView(this.image);
		this.imageFrame.setFitWidth(this.parentContainer.squareSize);
		this.imageFrame.setPreserveRatio(true);
		this.getChildren().add(this.imageFrame);
		
		if (this.backend.hasPiece()) {
			PieceDisplay pieceDisplay = new PieceDisplay(this.parentContainer, this.backend.getPiece());
			this.getChildren().add(pieceDisplay);
		}
	}
}

/**
 * Event Handler for clicking a square on the board. This is meant to be set
 * as the on click event for a {@link SquarePane}.
 * @author ian
 *
 */
class SquareClickedHandler implements EventHandler<Event> {
	
	private final SquarePane square;
	
	/**
	 * Instantiate an On-click handler for a {@link SquarePane}.
	 * @param _square
	 */
	public SquareClickedHandler(SquarePane _square) {
		this.square = _square;
	}

	/**
	 * @Override TODO implement this.
	 * @param event
	 */
	public void handle(Event event) {
		this.square.parentContainer.inputSquareClick(this.square);
	}
}
