/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 */
package com.ianmann.chess.game.movement;

import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.LinkedList;

import com.ianmann.chess.game.TeamColor;
import com.ianmann.chess.game.pieces.Rook;

/**
 * <p>
 * Represents a collection of {@link Square}s that a piece can
 * move through to get to it's destination.
 * </p>
 *
 * @author Ian
 * Created: Apr 8, 2018
 *
 */
public class MovementPath extends LinkedList<Square> {
	
	/**
	 * The starting point of this path.
	 */
	private Square root;
	
	/**
	 * As this bath is being built, each time a square is added, it is set as this attribute.
	 * This represents the square that the next build will build off of. This way, it doesn't
	 * keep building off the root. We can't use {@link this#getLast()} because it starts as
	 * {@link this#root} but root isn't added in this lists items so it can't be retrieved from
	 * {@link this#getLast()}.
	 */
	private Square currentBuild;
	
	/**
	 * The forward orientation of this path.
	 */
	private Orientation orientation;
	
	/**
	 * Determines if this path can still be added to. If a path cannot be build, this is set
	 * to false so that later calls to {@link this#build(Direction, int)} don't accidentally
	 * build on to an invalid path. Nothing will be added to this path and this path will be
	 * completely wiped if this is set to false.
	 */
	private boolean isValid = true;
	
	/**
	 * <p>
	 * Represents whether or not this path is a continuous path
	 * (movement can end at any point along this path) or a
	 * destination path (movement can only end at the end of this
	 * path).
	 * </p>
	 * <p>
	 * If true, this is a continuous path, otherwise, this is a
	 * destination path.
	 * </p>
	 */
	public final boolean isContinuous;
	
	/**
	 * Determines whether or not this move is a castle maneuver by the king and a rook.
	 */
	public final boolean isCastle;
	
	/**
	 * The rook that the king following this castle maneuver will castle with.
	 */
	private Rook castlingRook;
	
	/**
	 * The square that the rook will be placed on if the king actually performs this
	 * castling move.
	 */
	private Square castlingRookDestination;
	
	/**
	 * Orientation that the king following this castle maneuver would move.
	 */
	private Orientation castlingOrientation;
	
	/**
	 * The team that this path is relative to. This is used to ensure that the path does
	 * not allow the piece following it to end on squares with pieces of their own team.
	 */
	private TeamColor team;
	
	/**
	 * Instantiate an abstraction of a path of movement that a
	 * game piece can take. This specific path will represent a castling maneuver between
	 * a King and a Rook.
	 * @param _root
	 * @param _orientation
	 * @param _isContinuous Represents whether or not this path is a continuous path
	 * (movement can end at any point along this path) or a
	 * destination path (movement can only end at the end of this
	 * path).
	 * @param _team The team that this path is relative to.
	 */
	public MovementPath(Square _root, Orientation _orientation, boolean _isContinuous, TeamColor _team) {
		this.root = _root;
		this.currentBuild = _root;
		this.orientation = _orientation;
		this.isContinuous = _isContinuous;
		this.team = _team;
		this.isCastle = false;
		this.castlingRook = null;
	}
	
	/**
	 * Instantiate an abstraction of a path of movement that a
	 * game piece can take.
	 * @param _root
	 * @param _orientation
	 * @param _isContinuous Represents whether or not this path is a continuous path
	 * (movement can end at any point along this path) or a
	 * destination path (movement can only end at the end of this
	 * path).
	 * @param _team The team that this path is relative to.
	 */
	public MovementPath(Square _root, Orientation _orientation, TeamColor _team, Orientation _castlingOrientation) {
		this.root = _root;
		this.currentBuild = _root;
		this.orientation = _orientation;
		this.isContinuous = false;
		this.team = _team;
		this.isCastle = true;
		this.castlingOrientation = _castlingOrientation;
	}
	
	/**
	 * Returns the rook that is castling with the king.
	 * @return
	 */
	public Rook getCastlingRook() {
		return this.castlingRook;
	}
	
	/**
	 * Returns the destination where the castling rook will end up going if this path
	 * is followed by the king.
	 * @return
	 */
	public Square getCastlingRookDestination() {
		return this.castlingRookDestination;
	}
	
	/**
	 * <p>
	 * Adds up to [_length] number of squares in the given two direction of this square.
	 * The number of squares that were added is returned. There may not be _length
	 * number of squares in this direction due to the edge of the board.
	 * </p>
	 * <p>
	 * The number returned skips will only include squares along the diagonal path, and
	 * not the vertical/horizontal path squares.
	 * </p>
	 * @param length
	 * @return
	 */
	public int buildDiagonal(Direction _sideWays, Direction _verticle, int _length) {
		if (!this.isValid) return _length;
		
		int count = 0;
		for (; count < _length; count ++) {
			Square neighbor = this.currentBuild.neighbor(this.orientation, _sideWays);
			if (neighbor == null) {
				if (!this.isContinuous) {
					this.isValid = false;
					this.clear();
				}
				return count;
			}
			Square diagNeighbor = neighbor.neighbor(this.orientation, _verticle);
			if (diagNeighbor == null) {
				if (!this.isContinuous) {
					this.isValid = false;
					this.clear();
				}
				return count;
			}

			this.currentBuild = diagNeighbor;
			this.add(diagNeighbor);
		}
		return count;
	}
	
	/**
	 * Adds up to [_length] number of squares in the given direction of this square.
	 * The number of squares that were added is returned. There may not be _length
	 * number of squares in this direction due to the edge of the board.
	 * @param length
	 * @return
	 */
	public int build(Direction _direction, int _length) {
		if (!this.isValid) return _length;
		
		int count = 0;
		for (; count < _length; count ++) {
			Square neighbor = this.currentBuild.neighbor(this.orientation, _direction);
			if (neighbor == null) {
				if (!this.isContinuous) {
					this.isValid = false;
					this.clear();
				}
				return count;
			}
			
			this.currentBuild = neighbor;
			this.add(neighbor);
		}
		return count;
	}
	
	public boolean buildCastleMove() {
		int kingMoveLength = 0;
		if (this.castlingOrientation == Orientation.EAST) {
			// Assuming Queen starts on the left.
			kingMoveLength = 2;
			Square castlingRookSquare = this.root
					.neighbor(this.castlingOrientation)
					.neighbor(this.castlingOrientation)
					.neighbor(this.castlingOrientation);
			this.castlingRook = (Rook) castlingRookSquare.getPiece();
			this.castlingRookDestination = this.root
					.neighbor(this.castlingOrientation);
		} else {
			// Assuming Queen starts on the left.
			kingMoveLength = 3;
			Square castlingRookSquare = this.root
					.neighbor(this.castlingOrientation)
					.neighbor(this.castlingOrientation)
					.neighbor(this.castlingOrientation)
					.neighbor(this.castlingOrientation);
			if (!Rook.class.isInstance(castlingRookSquare.getPiece())) {
				System.out.println(this.root);
				System.out.println("not rook");
			}
			this.castlingRook = (Rook) castlingRookSquare.getPiece();
			this.castlingRookDestination = this.root
					.neighbor(this.castlingOrientation)
					.neighbor(this.castlingOrientation);
		}
		if (this.castlingRook == null || this.castlingRook.hasMoved()) return false;
		
		for (int i = 0; i < kingMoveLength; i ++) {
			this.currentBuild = this.currentBuild.neighbor(this.castlingOrientation);
			if (this.currentBuild.hasPiece())
				return false;
		}
		
		this.add(this.currentBuild);
		return this.finish(false);
	}
	
	/**
	 * Does last second checks along the path assuming that no more squares will
	 * be added to the path. This mainly evaluates the ability for the piece along
	 * this path to jump over pieces.
	 * @param _canJump Determines whether or not the piece following this path may
	 * jump over pieces in the way.
	 */
	public boolean finish(boolean _canJump) {
		if (!_canJump) {
			for (int i = 0; i < this.size(); i ++) {
				Square square = this.get(i);
				if (this.isContinuous) {
					/*
					 * This piece can use this path but only until the first piece that
					 * it encounters. If that piece is it's own team, then it must stop
					 * in the node before that piece in this path. Otherwise, it can
					 * move to that pieces square (and take that piece) but can move no
					 * further.
					 */
					if (square.hasPiece()) {
						int startRemovingIndex = i;
						int origSize = this.size();
						if (square.getPiece().team != this.team)
							startRemovingIndex ++;
						for (; startRemovingIndex < origSize; startRemovingIndex ++) {
							this.removeLast();
						}
					}
				} else {
					/*
					 * Can't use this path if there's a piece in the way of this path.
					 * The end square can only be moved to if it's not occupied by its
					 * own team.
					 * 
					 * This is assuming that this path is not continuous (piece may only
					 * move to the last square in the path.
					 */
					if (!this.getLast().equals(square) && square.hasPiece())
						this.clear();
					else if (this.getLast().equals(square) && square.hasPiece(this.team))
						this.clear();
				}
			}
		} else {
			/*
			 * Check to make sure that the end of this piece isn't its own team. The
			 * squares in-between can be ignored because _canJump is true so it is
			 * assumed this piece can jump over those pieces.
			 */
			if (this.size() > 0 && this.getLast().hasPiece(this.team))
				this.clear();
		}
		return this.size() > 0;
	}
	
	/**
	 * Determines whether or not this path includes a destination point that is the
	 * given _square. Note that just because this path might pass through this square
	 * does not always mean that the piece following this path can end on that square.
	 * For example, knights can only end their turn at the end of their path, not
	 * anywhere within.
	 * @param _square
	 * @return
	 */
	public boolean containsDestination(Square _square) {
		if (!this.isContinuous) {
			return this.getLast().equals(_square);
		}
		if (this.isContinuous) {
			return this.contains(_square);
		}
		return false;
	}
}
