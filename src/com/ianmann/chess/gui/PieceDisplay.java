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
	 * Returns the image that represents the given _pieceType for the given _team. The correct _theme is used
	 * to get the piece image and then it is sized according to _imageSize and returned.
	 * @param _pieceType
	 * @param _team
	 * @param _theme
	 * @param _imageSize
	 * @return
	 */
	public static ImageView getDisplay(Class<? extends Piece> _pieceType, TeamColor _team, String _theme, double _imageSize) {
		Image image;
		ImageView imageFrame;
		
		String pieceImagePath = "/resources/themes/" + _theme + "/pieces/";
		if (_team == TeamColor.WHITE)
			pieceImagePath += "white/";
		else if (_team == TeamColor.BLACK)
			pieceImagePath += "black/";
		else
			System.err.println("Could not find a piece set for the team: " + _team.toString());
		
		if (Pawn.class.equals(_pieceType))
			pieceImagePath += "pawn.png";
		else if (King.class.equals(_pieceType))
			pieceImagePath += "king.png";
		else if (Queen.class.equals(_pieceType))
			pieceImagePath += "queen.png";
		else if (Bishop.class.equals(_pieceType))
			pieceImagePath += "bishop.png";
		else if (Knight.class.equals(_pieceType))
			pieceImagePath += "knight.png";
		else if (Rook.class.equals(_pieceType))
			pieceImagePath += "rook.png";
		else
			System.err.println("Could not find a " + _pieceType.getSimpleName() + " for the team: " + _team.toString());
		
		image = new Image(PieceDisplay.class.getResourceAsStream(pieceImagePath));
		imageFrame = new ImageView(image);
		imageFrame.setFitWidth(_imageSize);
		imageFrame.setPreserveRatio(true);
		return imageFrame;
	}
	
	/**
	 * Adds the image that represents this piece to the anchor pane.
	 */
	private void render() {
		this.imageFrame = PieceDisplay.getDisplay(this.backend.getClass(), this.backend.team,
								this.parentContainerBoard.gameScreen.theme, this.parentContainerBoard.squareSize);
		this.getChildren().add(this.imageFrame);
	}
}
