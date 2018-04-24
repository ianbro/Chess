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
import javafx.scene.layout.VBox;

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
	
	public void renderGrave() {
		this.piecesContainer.getChildren().clear();
		HashMap<Class<? extends Piece>, ArrayList<Piece>> grave = this.parentGameScreen.game.getCapturedPieces(this.team);
		for (Class<? extends Piece> deadGroupClass : grave.keySet()) {
			for (int i = 0; i < grave.get(deadGroupClass).size(); i ++) {
				ImageView imageFrame = this.getGraveCategoryImage(deadGroupClass);
				this.piecesContainer.getChildren().add(imageFrame);
			}
		}
	}
	
	private ImageView getGraveCategoryImage(Class<? extends Piece> pieceType) {
		String pieceImagePath = "/resources/themes/" + this.parentGameScreen.theme + "/pieces/";
		if (this.team == TeamColor.WHITE)
			pieceImagePath += "white/";
		else if (this.team == TeamColor.BLACK)
			pieceImagePath += "black/";
		else
			System.err.println("Could not find a piece set for the team: " + this.team.toString());
		
		if (Pawn.class.equals(pieceType))
			pieceImagePath += "pawn.png";
		else if (King.class.equals(pieceType))
			pieceImagePath += "king.png";
		else if (Queen.class.equals(pieceType))
			pieceImagePath += "queen.png";
		else if (Bishop.class.equals(pieceType))
			pieceImagePath += "bishop.png";
		else if (Knight.class.equals(pieceType))
			pieceImagePath += "knight.png";
		else if (Rook.class.equals(pieceType))
			pieceImagePath += "rook.png";
		else
			System.err.println("Could not find a " + pieceType.getSimpleName() + " for the team: " + this.team.toString());
		
		Image image = new Image(this.getClass().getResourceAsStream(pieceImagePath));
		ImageView imageFrame = new ImageView(image);
		imageFrame.setFitWidth(75);
		imageFrame.setPreserveRatio(true);
		return imageFrame;
	}
}
