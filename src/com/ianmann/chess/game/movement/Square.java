/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 */
package com.ianmann.chess.game.movement;

import java.util.LinkedList;

import com.ianmann.chess.game.Board;
import com.ianmann.chess.game.Piece;
import com.ianmann.chess.game.TeamColor;

/**
 * <p>
 * Represents a square in the grid on the board.
 * </p>
 *
 * @author Ian
 * Created: Apr 8, 2018
 *
 */
public class Square {
	
	/**
	 * The board that contains this square.
	 */
	public final Board board;
	
	/**
	 * This squares horizontal (east/west) index in the board. This
	 * can also be considered the index of the column that this square
	 * lies in. This index is 0-indexed.
	 */
	public final int x;
	
	/**
	 * This squares vertical (north/south) index in the board. This
	 * can also be considered the index of the row that this square
	 * lies in. This index is 0-indexed.
	 */
	public final int y;

	/**
	 * Points to the {@link Square} to the west of this square.
	 * If this is null, then this is an edge piece on the west
	 * side of the board.
	 */
	private Square westNeighbor;
	
	/**
	 * @return the westNeighbor
	 */
	public Square getWestNeighbor() {
		return westNeighbor;
	}

	/**
	 * @return the eastNeighbor
	 */
	public Square getEastNeighbor() {
		return eastNeighbor;
	}

	/**
	 * @return the northNeighbor
	 */
	public Square getNorthNeighbor() {
		return northNeighbor;
	}

	/**
	 * @return the southNeighbor
	 */
	public Square getSouthNeighbor() {
		return southNeighbor;
	}

	/**
	 * Points to the {@link Square} to the east of this square.
	 * If this is null, then this is an edge piece on the east
	 * side of the board.
	 */
	private Square eastNeighbor;
	
	/**
	 * Points to the {@link Square} to the north of this square.
	 * If this is null, then this is an edge piece on the north
	 * side of the board.
	 */
	private Square northNeighbor;
	
	/**
	 * Points to the {@link Square} to the south of this square.
	 * If this is null, then this is an edge piece on the south
	 * side of the board.
	 */
	private Square southNeighbor;
	
	/**
	 * The piece that lies in this square.
	 */
	private Piece piece;
	
	/**
	 * Instantiates a {@link Square} object with an x and y of 0.
	 * @param x
	 * @param y
	 */
	public Square(Board _board) {
		this.x = 0;
		this.y = 0;
		this.board = _board;
		this.board.squares.put(this.coordinateString(), this);
	}
	
	/**
	 * Instantiates a {@link Square} object with the given x and
	 * y values.
	 * @param x
	 * @param y
	 */
	public Square(Board _board, int x, int y) {
		this.x = x;
		this.y = y;
		this.board = _board;
		this.board.squares.put(this.coordinateString(), this);
	}
	
	/**
	 * Returns the square in the specified direction in relation to
	 * the specified orientation.
	 * @param direction
	 * @return
	 */
	public Square neighbor(Orientation _orientation, Direction direction) {
		if (direction.exact(_orientation) == Orientation.NORTH) {
			return this.northNeighbor;
		}
		if (direction.exact(_orientation) == Orientation.EAST) {
			return this.eastNeighbor;
		}
		if (direction.exact(_orientation) == Orientation.SOUTH) {
			return this.southNeighbor;
		}
		if (direction.exact(_orientation) == Orientation.WEST) {
			return this.westNeighbor;
		}
		return null;
	}
	
	/**
	 * Returns the square in the specified orientation.
	 * @param direction
	 * @return
	 */
	public Square neighbor(Orientation _orientation) {
		if (_orientation == Orientation.NORTH) {
			return this.northNeighbor;
		}
		if (_orientation == Orientation.EAST) {
			return this.eastNeighbor;
		}
		if (_orientation == Orientation.SOUTH) {
			return this.southNeighbor;
		}
		if (_orientation == Orientation.WEST) {
			return this.westNeighbor;
		}
		return null;
	}
	
	/**
	 * @return the row that contains this square. This square
	 * is found in its correct index in the row that is returned.
	 */
	public LinkedList<Square> getRow() {
		LinkedList<Square> row = new LinkedList<>();
		row.add(this);
		
		Square currentSquare = this.westNeighbor;
		// load west side of this square into the list.
		while (currentSquare != null) {
			row.addFirst(currentSquare);
			currentSquare = currentSquare.westNeighbor;
		}
		
		currentSquare = this.eastNeighbor;
		// load east side of this square into the list.
		while (currentSquare != null) {
			row.addLast(currentSquare);
			currentSquare = currentSquare.eastNeighbor;
		}
		
		return row;
	}
	
	/**
	 * @return the column that contains this square. This square
	 * is found in its correct index in the column that is returned.
	 */
	public LinkedList<Square> getColumn() {
		LinkedList<Square> column = new LinkedList<>();
		column.add(this);
		
		Square currentSquare = this.northNeighbor;
		// load west side of this square into the list.
		while (currentSquare != null) {
			column.addFirst(currentSquare);
			currentSquare = currentSquare.northNeighbor;
		}
		
		currentSquare = this.southNeighbor;
		// load east side of this square into the list.
		while (currentSquare != null) {
			column.addLast(currentSquare);
			currentSquare = currentSquare.southNeighbor;
		}
		
		return column;
	}
	
	/**
	 * Adds a single square as the neighbor in the given direction.
	 * If that neighbor already exists, null is returned. The built
	 * square is also linked to its neighbors if necessary.
	 * @param _orientation
	 * @return
	 */
	public Square build(Orientation _orientation) {
		if (this.neighbor(_orientation) != null) {
			return null;
		}
		
		if (_orientation == Orientation.NORTH) {
			this.northNeighbor = new Square(this.board, this.x, this.y - 1);
			this.northNeighbor.southNeighbor = this;
			try {
				this.eastNeighbor.northNeighbor.westNeighbor = this.northNeighbor;
				this.northNeighbor.eastNeighbor = this.eastNeighbor.northNeighbor;
			} catch (NullPointerException e) {
				
			}
			try {
				this.westNeighbor.northNeighbor.eastNeighbor = this.northNeighbor;
				this.northNeighbor.westNeighbor = this.westNeighbor.northNeighbor;
			} catch (NullPointerException e) {
				
			}
			return this.northNeighbor;
		}
		if (_orientation == Orientation.EAST) {
			this.eastNeighbor = new Square(this.board, this.x + 1, this.y);
			this.eastNeighbor.westNeighbor = this;
			try {
				this.northNeighbor.eastNeighbor.southNeighbor = this.eastNeighbor;
				this.eastNeighbor.northNeighbor = this.northNeighbor.eastNeighbor;
			} catch (NullPointerException e) {
				
			}
			try {
				this.southNeighbor.eastNeighbor.northNeighbor = this.eastNeighbor;
				this.eastNeighbor.southNeighbor = this.southNeighbor.eastNeighbor;
			} catch (NullPointerException e) {
				
			}
			return this.eastNeighbor;
		}
		if (_orientation == Orientation.SOUTH) {
			this.southNeighbor = new Square(this.board, this.x, this.y + 1);
			this.southNeighbor.northNeighbor = this;
			try {
				this.eastNeighbor.southNeighbor.westNeighbor = this.southNeighbor;
				this.southNeighbor.eastNeighbor = this.eastNeighbor.southNeighbor;
			} catch (NullPointerException e) {
				
			}
			try {
				this.westNeighbor.southNeighbor.eastNeighbor = this.southNeighbor;
				this.southNeighbor.westNeighbor = this.westNeighbor.southNeighbor;
			} catch (NullPointerException e) {
				
			}
			return this.southNeighbor;
		}
		if (_orientation == Orientation.WEST) {
			this.westNeighbor = new Square(this.board, this.x - 1, this.y);
			this.westNeighbor.eastNeighbor = this;
			try {
				this.northNeighbor.westNeighbor.southNeighbor = this.westNeighbor;
				this.westNeighbor.northNeighbor = this.northNeighbor.westNeighbor;
			} catch (NullPointerException e) {
				
			}
			try {
				this.southNeighbor.westNeighbor.northNeighbor = this.westNeighbor;
				this.westNeighbor.southNeighbor = this.southNeighbor.westNeighbor;
			} catch (NullPointerException e) {
				
			}
			return this.westNeighbor;
		}
		return null;
	}
	
	/**
	 * <p>
	 * Adds [length] squares to the neighbor of this square to the east
	 * of this square. This is a recursive function decrementing
	 * length on each call to control the number of times the recursion
	 * happens.
	 * </p>
	 * <p>
	 * Returns the number of squares added. If there already exists a
	 * neighbor to the east of this square, nothing is added and 0 is
	 * returned. Same thing happens if length is <= 0 as this would
	 * cause an endless recursion.
	 * </p>
	 * <p>
	 * This method is used specifically by {@link Board} to build its
	 * grid. It builds out in the south and east direction.
	 * </p>
	 * @param length
	 * @param _northRow
	 * @return
	 */
	public int buildEast(int length, LinkedList<Square> _northRow) {
		if (length <= 0) {
			return 0;
		}
		
		this.eastNeighbor = new Square(this.board, this.x + 1, this.y);
		this.eastNeighbor.westNeighbor = this;
		if (_northRow != null) {
			this.eastNeighbor.northNeighbor = _northRow.get(this.eastNeighbor.x);
			_northRow.get(this.eastNeighbor.x).southNeighbor = this.eastNeighbor;
		}
		return 1 + this.eastNeighbor.buildEast(length - 1, _northRow);
	}
	
	/**
	 * Places the specified piece in this square, setting the
	 * piece attribute of this square. If there already exist
	 * a piece in this square, the specified _piece is not
	 * placed in this square and false is returned.
	 * @param _piece
	 * @return
	 */
	public boolean placePiece(Piece _piece) {
		if (this.hasPiece(_piece.team))
			return false;
		else if (this.hasPiece())
			this.board.game.capturePiece(this.getPiece());
		this.piece = _piece;
		if (this.piece.getLocation() != null)
			this.piece.getLocation().piece = null;
		this.piece.markMovedTo(this);
		return true;
	}
	
	/**
	 * <p>
	 * Places the specified piece in this square, setting the
	 * piece attribute of this square.
	 * </p>
	 * <p>
	 * Please not that this move is assumed to be valid. No checks will
	 * be run so as to avoid stack overflow errors.
	 * </p>
	 * <p>
	 * This simulates the move assuming this square is a copy on
	 * a copied board, not the original board. Therefore, the logic
	 * of tracking dead and live pieces is removed so it doesn't
	 * accidentally remove the actual piece.
	 * </p>
	 * @param _piece
	 * @return
	 */
	public boolean simulatePlacePiece(Piece _piece) {
		this.piece = _piece;
		if (this.piece.getLocation() != null)
			this.piece.getLocation().piece = null;
		this.piece.markMovedTo(this);
		return true;
	}
	
	/**
	 * <p>
	 * Marks the piece in this square (if it has one) as removed from the square. This
	 * is used specifically for capturing a piece. This simply marks this square as not
	 * having a piece anymore. The _piece is provided to make sure that the piece doing
	 * capture is not removed but the piece that is captured is removed.
	 * </p>
	 * <p>
	 * If _piece is the piece on this square currently, then it is removed from the square.
	 * Otherwise, nothing happens. null is also an option and will signify that it doesn't
	 * matter what piece is on this square, it will just kill it.
	 * </p>
	 * @return
	 */
	public boolean markPieceRemoved(Piece _piece) {
		if (!this.hasPiece()) return false;
		if (_piece == null || this.getPiece().equals(_piece)) {
			this.piece = null;
			return true;
		}
		return false;
	}
	
	/**
	 * Places the specified piece in this square, setting the
	 * piece attribute of this square. If there already exist
	 * a piece in this square, the specified _piece is not
	 * placed in this square and false is returned.
	 * </p>
	 * <p>
	 * This is different from {@link this#placePiece(Piece)}
	 * because it calls {@link Piece#markSpawned(Square)} instead of
	 * {@link Piece#markMovedTo(Square)} so that any movement
	 * logic is not called.
	 * </p>
	 * @param _piece
	 * @return
	 */
	public boolean spawnPiece(Piece _piece) {
		if (this.hasPiece()) { return false; }
		this.piece = _piece;
		if (this.piece.getLocation() != null)
			this.piece.getLocation().piece = null;
		this.piece.markSpawned(this);
		this.board.getLivePieces(_piece.team).add(_piece);
		return true;
	}
	
	/**
	 * Returns whether or not this square contains a piece.
	 * @return
	 */
	public boolean hasPiece() {
		return this.piece != null;
	}
	
	/**
	 * Returns whether or not this square contains a piece of the specified
	 * color. If it does have a piece, but of the other color, false is still
	 * returned.
	 * @return
	 */
	public boolean hasPiece(TeamColor _team) {
		if (this.piece == null) {
			return false;
		}
		return this.piece.team == _team;
	}
	
	/**
	 * Returns the piece in this square.
	 * @return
	 */
	public Piece getPiece() {
		return this.piece;
	}
	
	/**
	 * <p>
	 * Prints this square represented by dashes and
	 * pipe characters. It will look like so:
	 * </p>
	 * <p>
	 * |--------|<br>
	 * |   42   |<br>
	 * |   BK   |<br>
	 * |--------|
	 * </p>
	 * @return
	 */
	public String toSquareString() {
		String str = "|--------|\n";
		str = str +  "|   xy   |\n".replace("x", Integer.toString(this.x)).replace("y", Integer.toString(this.y));
		if (this.hasPiece()) {
			str = str + "|   cp   |\n".replace("c", this.piece.team.initial).replaceAll("p", this.piece.initial());
		} else {
			str = str + "|        |\n";
		}
		str = str +  "|--------|\n";
		return str;
	}
	
	/**
	 * Returns the coordinates of this square in its board
	 * in the format of "x-y".
	 * @return
	 */
	public String coordinateString() {
		return this.x + "-" + this.y;
	}
	
	/**
	 * <p>
	 * Prints this squares row as squares represented by dashes and
	 * pipe characters. It will look like so:
	 * </p>
	 * <p>
	 * |--------|--------|<br>
	 * |   42   |   52   |<br>
	 * |   BK   |        |<br>
	 * |--------|--------|<br>
	 * |   43   |   53   |<br>
	 * |   WP   |        |<br>
	 * |--------|--------|
	 * </p>
	 * @return
	 */
	public String rowString() {
		LinkedList<Square> thisRow = this.getRow();
		String str = "";
		for (Square square : thisRow) {
			str = str + "|--------";
		}
		str = str + "|\n";

		for (Square square : thisRow) {
			str = str + "|   xy   ".replace("x", Integer.toString(square.x)).replace("y", Integer.toString(square.y));
		}
		str = str + "|\n";
		
		for (Square square : thisRow) {
			if (square.hasPiece()) {
				str = str + "|   cp   ".replace("c", square.piece.team.initial).replaceAll("p", square.piece.initial());
			} else {
				str = str + "|        ";
			}
		}
		str = str + "|\n";

		return str;
	}
	
	/**
	 * Returns a string representation of this square's data. This simply prints
	 * out the coordinates for the square and the piece (if any) that is on it.
	 */
	public String toString() {
		return this.coordinateString() + ":" + this.getPiece();
	}
	
	/**
	 * Copies information in the _original square into this square.
	 * Any piece on _original will be copied and spawned onto the
	 * new square copy. It is assumed that this square is the copied
	 * simulated square.
	 * @param _original
	 */
	public void copyOriginalSquare(Square _original) {
		if (_original.hasPiece()) {
			Piece newPiece = _original.piece.copy(this.board);
			this.spawnPiece(newPiece);
		}
	}
}
