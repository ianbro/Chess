package com.ianmann.chess.gui;

import com.ianmann.chess.game.Piece;
import com.ianmann.chess.game.TeamColor;
import com.ianmann.chess.game.pieces.Bishop;
import com.ianmann.chess.game.pieces.King;
import com.ianmann.chess.game.pieces.Knight;
import com.ianmann.chess.game.pieces.Pawn;
import com.ianmann.chess.game.pieces.Queen;
import com.ianmann.chess.game.pieces.Rook;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Class that represents the display of a piece. Depending on the piece type, a different
 * picture will be used to represent the piece.
 * @author ian
 *
 */
public class PieceDisplay extends AnchorPane {
	
	/**
	 * The square container that contains this square.
	 */
	public final BoardSquareContainer parentContainerBoard;
	
	/**
	 * The piece data that this display represents. This is used to decide
	 * which piece to show.
	 */
	private final Piece backend;
	
	/**
	 * The frame that contains the image used to display this piece.
	 */
	private ImageView imageFrame;
	
	/**
	 * The image that represents and displays this piece.
	 */
	public Image image;
	
	public PieceDisplay(BoardSquareContainer _containerBoard, Piece _backend) {
		this.backend = _backend;
		this.parentContainerBoard = _containerBoard;
		
		this.render();
	}
	
	/**
	 * Adds the image that represents this piece to the anchor pane.
	 */
	private void render() {
		String pieceImagePath = "/resources/themes/" + this.parentContainerBoard.gameScreen.theme + "/pieces/";
		if (this.backend.team == TeamColor.WHITE)
			pieceImagePath += "white/";
		else if (this.backend.team == TeamColor.BLACK)
			pieceImagePath += "black/";
		else
			System.err.println("Could not find a piece set for the team: " + this.backend.team.toString());
		
		if (Pawn.class.isInstance(this.backend))
			pieceImagePath += "pawn.png";
		else if (King.class.isInstance(this.backend))
			pieceImagePath += "king.png";
		else if (Queen.class.isInstance(this.backend))
			pieceImagePath += "queen.png";
		else if (Bishop.class.isInstance(this.backend))
			pieceImagePath += "bishop.png";
		else if (Knight.class.isInstance(this.backend))
			pieceImagePath += "knight.png";
		else if (Rook.class.isInstance(this.backend))
			pieceImagePath += "rook.png";
		else
			System.err.println("Could not find a " + this.backend.getClass().getSimpleName() + " for the team: " + this.backend.team.toString());
		
		this.image = new Image(this.getClass().getResourceAsStream(pieceImagePath));
		this.imageFrame = new ImageView(this.image);
		this.imageFrame.setFitWidth(this.parentContainerBoard.squareSize);
		this.imageFrame.setPreserveRatio(true);
		this.getChildren().add(this.imageFrame);
	}
}
