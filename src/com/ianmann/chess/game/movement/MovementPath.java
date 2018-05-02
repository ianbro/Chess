/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 */
package com.ianmann.chess.game.movement;

import java.util.LinkedList;

import com.ianmann.chess.game.TeamColor;
import com.ianmann.chess.game.pieces.King;
import com.ianmann.chess.game.pieces.Pawn;
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
	 * Represents whether or not this move is an en-passent move by a pawn. The pawn following
	 * this movement path is the one doing the capture.
	 */
	public final boolean isEnPassent;
	
	/**
	 * Represents whether or not this move is a double move by a pawn on it's first move.
	 */
	public final boolean isPawnLeap;
	
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
	 * The pawn that the pawn following this MovementPath will capture if it takes this path.
	 */
	private Pawn pawnToCaptureEnPassent;
	
	/**
	 * The team that this path is relative to. This is used to ensure that the path does
	 * not allow the piece following it to end on squares with pieces of their own team.
	 */
	private TeamColor team;
	
	/**
	 * <p>
	 * Instantiate an abstraction of a path of movement that a game piece can take. This
	 * specific path will represent an en-passent maneuver to capture a pawn trying to
	 * pass the pawn following this path.
	 * </p>
	 * <p>
	 * The team for this {@link MovementPath} will be set to that of the piece on _root.
	 * </p>
	 * @param _root The location that the pawn being passed lies. This pawn is the one performing
	 * the capture and en-passent maneuver.
	 * @param _orientation The orientation of the pawn on _root (the one performing the en-passent
	 * maneuver.
	 * @param _pawnToCaptureEnPassent The pawn that tried to pass the pawn on _root. This is
	 * the one that will be captured en-passent.
	 */
	public MovementPath(Square _root, Orientation _orientation, Pawn _pawnToCaptureEnPassent) {
		this.root = _root;
		this.currentBuild = _root;
		this.orientation = _orientation;
		this.team = _root.getPiece().team.oponent();
		this.isContinuous = false;
		this.isCastle = false;
		this.isPawnLeap = false;
		this.isEnPassent = true;
		this.pawnToCaptureEnPassent = _pawnToCaptureEnPassent;
	}
	
	/**
	 * Instantiate an abstraction of a path of movement that a
	 * game piece can take. This constructor is for a pawns leap move as its first move.
	 * @param _root
	 * @param _orientation
	 * @param _isContinuous Represents whether or not this path is a continuous path
	 * (movement can end at any point along this path) or a
	 * destination path (movement can only end at the end of this
	 * path).
	 * @param _isPawnLeap Denotes that this move is a leap move as a pawns first move going two spaces.
	 * @param _team The team that this path is relative to.
	 */
	public MovementPath(Square _root, Orientation _orientation, boolean _isContinuous, boolean _isPawnLeap, TeamColor _team) {
		this.root = _root;
		this.currentBuild = _root;
		this.orientation = _orientation;
		this.isContinuous = _isContinuous;
		this.isPawnLeap = _isPawnLeap;
		this.team = _team;
		this.isCastle = false;
		this.isEnPassent = false;
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
	public MovementPath(Square _root, Orientation _orientation, boolean _isContinuous, TeamColor _team) {
		this.root = _root;
		this.currentBuild = _root;
		this.orientation = _orientation;
		this.isContinuous = _isContinuous;
		this.isPawnLeap = false;
		this.team = _team;
		this.isCastle = false;
		this.isEnPassent = false;
	}
	
	/**
	 * Instantiate an abstraction of a path of movement that a
	 * game piece can take. This specific path will represent a castling maneuver between
	 * a King and a Rook.
	 * @param _root
	 * @param _orientation
	 * @param _team The team that this path is relative to.
	 * @param _castlingOrientation The orientation in which the king will move in this castle maneuver.
	 */
	public MovementPath(Square _root, Orientation _orientation, TeamColor _team, Orientation _castlingOrientation) {
		this.root = _root;
		this.currentBuild = _root;
		this.orientation = _orientation;
		this.isContinuous = false;
		this.isEnPassent = false;
		this.isPawnLeap = false;
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
	 * Returns the pawn that will be captured en-passent by the pawn on {@link this#root}.
	 * @return
	 */
	public Pawn getPawnToCaptureEnPassent() {
		return this.pawnToCaptureEnPassent;
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
	
	/**
	 * Adds the castle move for a king in both the left and right direction. Each direction
	 * is checked to make sure that the rook in that direction has not moved yet and that
	 * there are no pieces in between blocking the castle. Checks based on wether or not the
	 * king is moving into check will be run later by {@link King#evaluateMovesIntoCheck(java.util.ArrayList)}
	 * after all {@link MovementPath}s have been added. It is also assumed in this method that
	 * the king is not moving out of check, so that will have to be checked before calling
	 * this method.
	 * @return
	 */
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
	 * Build this path as if it represents an en-passent move. This just sets the destination for the
	 * offensive pawn (the one doing the move).
	 * @return
	 */
	public boolean buildEnPassentMove() {
		// Setting it to the neighbor of pawnToCaptureEnPassent in the forward direction... but
		// the forward direction is relative to this.orientation which is actually the orientation
		// of this.pawnToCaptureEnPassent's oponent so it's backward to this.pawnToCaptureEnPassent
		// instead of forward
		this.add(this.pawnToCaptureEnPassent.getLocation().neighbor(this.orientation, Direction.FORWARD));
		return this.finish(true);
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
	 * If this movement path is a leap by a pawn (moving 2 spaces at once), then this looks for
	 * any opposing pawns that it tried to pass via the leap. If there are any, it alerts that
	 * pawn that this pawn is trying to pass it so that it knows that it can take this pawn
	 * via en-passent.
	 */
	private void alertPassedPawnsOfEnPassent() {
		if (this.isPawnLeap) {
			Square eastNeighbor = this.getLast().neighbor(Orientation.EAST);
			if (eastNeighbor.hasPiece(this.team.oponent()) && Pawn.class.isInstance(eastNeighbor.getPiece()))
				((Pawn) eastNeighbor.getPiece()).addEnPassentMove((Pawn) this.getLast().getPiece());
			
			Square westNeighbor = this.getLast().neighbor(Orientation.WEST);
			if (westNeighbor.hasPiece(this.team.oponent()) && Pawn.class.isInstance(westNeighbor.getPiece()))
				((Pawn) westNeighbor.getPiece()).addEnPassentMove((Pawn) this.getLast().getPiece());
		}
	}
	
	/**
	 * Certain moves trigger events such as a castle move triggers the swapping places of the
	 * king and the rook. These events are things that wouldn't normally happen on a regular
	 * move. This performs those special events and is meant to be called after a piece uses
	 * this {@link MovementPath} to move along.
	 */
	public void performSpecialEvents() {
		if (this.isCastle)
			this.getCastlingRookDestination().placePiece(this.getCastlingRook()); // Move the rook in it's correct castle place.
		if (this.isPawnLeap)
			this.alertPassedPawnsOfEnPassent();
		if (this.isEnPassent) {
			if (this.getPawnToCaptureEnPassent().board.isSimulation) {
				/*
				 * If the board is a simulation, we don't want to use capturePiece() as that will effect the actual
				 * state of the game. However, this is a simuation and so the game object should not worry about this
				 * board. So it shouldn't perform logic on this board in case it also manipulates the game state. So
				 * instead, we run the logic of removing the piece from the board here but not on the game object. This
				 * way the logic is separated from the game object.
				 * @TODO: Abstract this out to a method on the Board object.
				 */
				this.getPawnToCaptureEnPassent().getLocation().markPieceRemoved(this.getPawnToCaptureEnPassent());
				this.getPawnToCaptureEnPassent().markMovedTo(null);
				this.getPawnToCaptureEnPassent().board.getLivePieces(this.team.oponent()).remove(this.getPawnToCaptureEnPassent());
			}
			else // It's not a simulation so the capturing will effect the game state.
				this.getPawnToCaptureEnPassent().board.game.capturePiece(this.getPawnToCaptureEnPassent());
		}
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
