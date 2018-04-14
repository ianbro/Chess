/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 9, 2018
 */
package com.ianmann.chess.game;

import java.util.ArrayList;
import java.util.HashMap;

import com.ianmann.chess.game.movement.Orientation;
import com.ianmann.chess.game.movement.Square;
import com.ianmann.chess.game.pieces.Bishop;
import com.ianmann.chess.game.pieces.King;
import com.ianmann.chess.game.pieces.Knight;
import com.ianmann.chess.game.pieces.Pawn;
import com.ianmann.chess.game.pieces.Queen;
import com.ianmann.chess.game.pieces.Rook;

/**
 * Represents the board for a game. This contains the grid of squares
 * in the game as a linked matrix (each square is connected to its
 * neighbors).
 *
 * @author Ian
 * Created: Apr 9, 2018
 *
 */
public class Board {
	
	/**
	 * The game by which this board is controled.
	 */
	public final Game game;
	
	/**
	 * The starting positions of pieces on each team. This data structure has keys
	 * of teams that map to the maps of piece classes and a list of where each
	 * class of piece goes.
	 */
	private HashMap<TeamColor, HashMap<Class<? extends Piece>, ArrayList<Square>>> startingPositions = new HashMap<TeamColor, HashMap<Class<? extends Piece>, ArrayList<Square>>>();
	
	/**
	 * Map of squares contained in this board. Each key is the coordinate
	 * in the format "x-y" which map to the corresponding Square object.
	 * These squares are linked to each-other as well through the
	 * neighbor attributes.
	 * @return
	 */
	public final HashMap<String, Square> squares = new HashMap<String, Square>();
	
	/**
	 * Number of squares in the x (east/west) direction on this board.
	 */
	public final int width;
	
	/**
	 * Number of squares in the y (north/south) direction on this board.
	 */
	public final int height;

	/**
	 * <p>
	 * The root square in the board. All other squares are connected
	 * to this square through the neighbor attributes such as
	 * {@link Square#northNeighbor} or {@link Square#eastNeighbor}.
	 * </p>
	 * <p>
	 * This root square is the north west corner square. To get the
	 * other squares, go through the neigbor attributes in each square
	 * like a linked list. For example, to get 3 squares down on the
	 * board and 2 squares over to the right on the board, call
	 * </p>
	 * <p>
	 * {@code this.rootSquare.southNeighbor.southNeighbor.eastNeighbor.eastNeighbor}
	 * </p>
	 */
	public Square rootSquare;
	
	/**
	 * Instantiate with [_width] squares wide and [_height] squares
	 * high.
	 * @param _width
	 * @param _height
	 */
	public Board(Game _game, int _width, int _height) {
		this.game = _game;
		this.width = _width;
		this.height = _height;
		this.build();
		initStart();
	}
	
	/**
	 * Instantiates a board with the default dimensions of x and y.
	 */
	public Board(Game _game) {
		this.game = _game;
		this.width = 8;
		this.height = 8;
		this.build();
		initStart();
	}
	
	/**
	 * Sets the starting positions of the teams using the map in
	 * {@link Board#startingPositions}.
	 */
	@SuppressWarnings("serial")
	private void initStart() {
		this.startingPositions.put(TeamColor.WHITE, new HashMap<Class<? extends Piece>, ArrayList<Square>>()
		{{
			put(Rook.class, new ArrayList<Square>(){{
				add(squares.get("0-0"));
				add(squares.get("7-0"));
			}});
			put(Knight.class, new ArrayList<Square>(){{
				add(squares.get("1-0"));
				add(squares.get("6-0"));
			}});
			put(Bishop.class, new ArrayList<Square>(){{
				add(squares.get("2-0"));
				add(squares.get("5-0"));
			}});
			put(Queen.class, new ArrayList<Square>(){{
				add(squares.get("3-0"));
			}});
			put(King.class, new ArrayList<Square>(){{
				add(squares.get("4-0"));
			}});
			put(Pawn.class, new ArrayList<Square>(){{
				add(squares.get("0-1"));
				add(squares.get("1-1"));
				add(squares.get("2-1"));
				add(squares.get("3-1"));
				add(squares.get("4-1"));
				add(squares.get("5-1"));
				add(squares.get("6-1"));
				add(squares.get("7-1"));
			}});
		}});
		this.startingPositions.put(TeamColor.BLACK, new HashMap<Class<? extends Piece>, ArrayList<Square>>()
		{{
			put(Rook.class, new ArrayList<Square>(){{
				add(squares.get("0-7"));
				add(squares.get("7-7"));
			}});
			put(Knight.class, new ArrayList<Square>(){{
				add(squares.get("1-7"));
				add(squares.get("6-7"));
			}});
			put(Bishop.class, new ArrayList<Square>(){{
				add(squares.get("2-7"));
				add(squares.get("5-7"));
			}});
			put(Queen.class, new ArrayList<Square>(){{
				add(squares.get("3-7"));
			}});
			put(King.class, new ArrayList<Square>(){{
				add(squares.get("5-4"));
			}});
			put(Pawn.class, new ArrayList<Square>(){{
				add(squares.get("0-6"));
				add(squares.get("1-6"));
				add(squares.get("2-6"));
				add(squares.get("3-6"));
				add(squares.get("4-4"));
				add(squares.get("5-6"));
				add(squares.get("6-6"));
				add(squares.get("7-6"));
			}});
		}});
	}
	
	/**
	 * <p>
	 * Creates all squares in this board. This is done by creating
	 * each row one row at a time.
	 * </p>
	 */
	private void build() {
		/*
		 * This method builds each row one at a time. for each row,
		 * the root square (first square in the row on the west side)
		 * is built. Then the row is built to the east.
		 * 
		 * If the current row square is null, then this is the very
		 * first iteration and so the created row root is set to this
		 * instances rootSquare attribute.
		 */
		System.out.println("Creating board...");
		Square currentRowSquareRoot = null;
		for (int yCount = 0; yCount < this.height; yCount ++) {
			/*
			 * Build root square for this row.
			 */
			if (currentRowSquareRoot == null) {
				currentRowSquareRoot = new Square(this);
				this.rootSquare = currentRowSquareRoot;
			} else {
				currentRowSquareRoot = currentRowSquareRoot.build(Orientation.SOUTH);
				if (currentRowSquareRoot == null) {
					System.err.println("Could not create row " + ((int) (yCount + 1)));
					return;
				}
			}
			
			/*
			 * Build east of the root to get row.
			 */
			int widthBuilt = 0;
			if (currentRowSquareRoot.getNorthNeighbor() != null) {
				widthBuilt = 1 + currentRowSquareRoot.buildEast(this.width - 1, currentRowSquareRoot.getNorthNeighbor().getRow());
			} else {
				widthBuilt = 1 + currentRowSquareRoot.buildEast(this.width - 1, null);
			}
			
			/*
			 * Ensure that row was completed.
			 */
			if (widthBuilt == this.width - 1) {
				System.err.println("Could only create " + widthBuilt + " of " + this.width + " squares in row " + ((int) (yCount + 1)));
				return;
			} else {
				System.out.println("Created " + widthBuilt + " of " + this.width + " squares in row " + ((int) (yCount + 1)));
			}
		}
		System.out.println("Done creating board.");
	}
	
	/**
	 * Returns a string representation of this board. This prints out
	 * every square with its coordinates and piece.
	 */
	public String toBoardString() {
		String str = "";
		Square currentSquare = this.rootSquare;
		while (currentSquare != null) {
			str = str + currentSquare.rowString();
			if (currentSquare.getSouthNeighbor() == null) {
				for (int i = 0; i < currentSquare.getRow().size(); i ++) {
					str = str + "|--------";
				}
				str = str + "|\n";
			}
			currentSquare = currentSquare.getSouthNeighbor();
		}
		return str;
	}
	
	/**
	 * Places the piece in a spot on the baord where it is set to
	 * start according to {@link Board#startingPositions}.
	 * @param _piece
	 * @return
	 */
	public boolean spawnPiece(Piece _piece) {
		ArrayList<Square> locations = this.startingPositions.get(_piece.team).get(_piece.getClass());
		for (Square square : locations) {
			if (!square.hasPiece()) {
				square.spawnPiece(_piece);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Moves a piece from the square at _fromCoordinate to the square at _toCoordinate.
	 * This will return true if the piece was successfully moved or false if not. If
	 * either of the squares are not found, the from square doesn't' contain a piece
	 * or the to square has a piece of the same team as that in to square, nothing
	 * will happen and false will be returned.
	 * @param _fromCoordinates
	 * @param _toCoordinates
	 * @return
	 */
	public boolean movePiece(String _fromCoordinates, String _toCoordinates) {
		Square fromSquare = this.squares.get(_fromCoordinates);
		Square toSquare = this.squares.get(_toCoordinates);
		if (fromSquare.hasPiece())
			if (!fromSquare.getPiece().canMove(toSquare)) return false;
		if (fromSquare == null || toSquare == null)
			return false;
		if (!fromSquare.hasPiece())
			return false;
		if (toSquare.hasPiece(fromSquare.getPiece().team))
			return false;

		return toSquare.placePiece(fromSquare.getPiece());
	}
	
	/**
	 * Moves a piece from _fromSquare to _toSquare. This will return true if the piece
	 * was successfully moved or false if not. If either of the squares are not found,
	 * the from square doesn't' contain a piece or the to square has a piece of the same
	 * team as that in to square, nothing will happen and false will be returned.
	 * @param _fromCoordinates
	 * @param _toCoordinates
	 * @return
	 */
	public boolean movePiece(Square _fromSquare, Square _toSquare) {
		if (_fromSquare.hasPiece())
			if (!_fromSquare.getPiece().canMove(_toSquare)) return false;
		if (_fromSquare == null || _toSquare == null)
			return false;
		if (!_fromSquare.hasPiece())
			return false;
		if (_toSquare.hasPiece(_fromSquare.getPiece().team))
			return false;

		return _toSquare.placePiece(_fromSquare.getPiece());
	}
}
