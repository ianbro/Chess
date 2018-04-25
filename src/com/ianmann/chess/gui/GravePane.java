package com.ianmann.chess.gui;

import java.util.ArrayList;
import java.util.HashMap;

import com.ianmann.chess.game.Piece;
import com.ianmann.chess.game.TeamColor;
import com.ianmann.chess.game.pieces.Bishop;
import com.ianmann.chess.game.pieces.King;
import com.ianmann.chess.game.pieces.Knight;
import com.ianmann.chess.game.pieces.Pawn;
import com.ianmann.chess.game.pieces.Queen;
import com.ianmann.chess.game.pieces.Rook;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Wrapper class for a grave pane. This controls the fxml that shows the grave for
 * a certain team.
 * @author ian
 *
 */
public class GravePane {

	/**
	 * The team that this pane shows dead pieces for.
	 */
	private final TeamColor team;
	
	/**
	 * The {@link GameScreen} that this grave is contained in.
	 */
	public final GameScreen parentGameScreen;
	
	/**
	 * The actual {@link VBox} that this wrapper object controls.
	 */
	public final VBox value;
	
	/**
	 * The pane that actually contains the list of dead pieces.
	 */
	private VBox piecesContainer;
	
	/**
	 * Instantiate a {@link GravePane} that shows the grave for a given _team in the game
	 * of _parentGameScreen.
	 * @param _parentGameScreen
	 * @param _team
	 */
	public GravePane(GameScreen _parentGameScreen, TeamColor _team, VBox _pane) {
		this.parentGameScreen = _parentGameScreen;
		this.team = _team;
		this.value = _pane;
		this.initialize();
	}
	
	/**
	 * Initializes the elements in this wrappers {@link VBox}.
	 */
	private void initialize() {
		this.piecesContainer = (VBox) this.value.lookup("#listPieces" + this.team.initial);
		
		this.renderGrave();
	}
	
	/**
	 * Draws the grave according to which pieces are dead. The grave will be drawn with a picture of each type
	 * of piece that is dead and a quantity next to it representing how many of that piece type is dead.
	 */
	public void renderGrave() {
		this.piecesContainer.getChildren().clear();
		
		HashMap<Class<? extends Piece>, Integer> graveStatsByPieceClass = new HashMap<Class<? extends Piece>, Integer>();
		
		/*
		 * Tally up all the dead pieces according to their piece class. Put this
		 * tally into graveStatsByPieceClass.
		 */
		HashMap<Class<? extends Piece>, ArrayList<Piece>> grave = this.parentGameScreen.game.getCapturedPieces(this.team);
		for (Class<? extends Piece> deadGroupClass : grave.keySet()) {
			for (int i = 0; i < grave.get(deadGroupClass).size(); i ++) {
				if (graveStatsByPieceClass.containsKey(deadGroupClass))
					graveStatsByPieceClass.put(deadGroupClass, ((Integer) graveStatsByPieceClass.get(deadGroupClass)) + 1);
				else
					graveStatsByPieceClass.put(deadGroupClass, 1);
			}
		}
		
		/*
		 * Loop through graveStatsByPieceClass and draw the HBox that displays the death stat for
		 * each piece class.
		 */
		for (Class<? extends Piece> deadGroupClass : graveStatsByPieceClass.keySet()) {
			int deathTally = graveStatsByPieceClass.get(deadGroupClass);
			HBox statDisplayContainer = this.getDeadPieceTally(deadGroupClass, deathTally);
			this.piecesContainer.getChildren().add(statDisplayContainer);
		}
	}
	
	/**
	 * Designs the stat display for a given piece type (_deadGroupClass). The number of dead pieces of this
	 * type (_deathTally) is displayed right next to an image of that piece type.
	 * @param _deadGroupClass
	 * @param _deathTally
	 * @return
	 */
	private HBox getDeadPieceTally(Class<? extends Piece> _deadGroupClass, int _deathTally) {
		ImageView pieceTypeImageView = PieceDisplay.getDisplay(_deadGroupClass, this.team, this.parentGameScreen.theme, 75);
		Label tallyLabel = new Label(String.valueOf(_deathTally));
		tallyLabel.setFont(Font.font("Gruppo", 64.0));
		tallyLabel.setTextFill(Color.WHITE);
		tallyLabel.setTextAlignment(TextAlignment.CENTER);
		HBox statDisplayContainer = new HBox(pieceTypeImageView, tallyLabel);
		return statDisplayContainer;
	}
}
