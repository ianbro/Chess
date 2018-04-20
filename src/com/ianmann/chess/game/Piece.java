/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 */
package com.ianmann.chess.game;

import java.util.ArrayList;

import com.ianmann.chess.game.movement.MovementPath;
import com.ianmann.chess.game.movement.Orientation;
import com.ianmann.chess.game.movement.Direction;
import com.ianmann.chess.game.movement.Square;
import com.ianmann.chess.game.pieces.King;
import com.ianmann.chess.game.pieces.Pawn;

/**
 * <p>
 * Represents a piece on the board. This also provides
 * logic and restrictions for where this piece can move.
 * </p>
 *
 * @author Ian
 * Created: Apr 8, 2018
 *
 */
public abstract class Piece {
	
	/**
	 * The team to which this piece belongs.
	 */
	public final TeamColor team;
	
	/**
	 * The board that this piece is playing on.
	 */
	public final Board board;
	
	/**
	 * The current location of this piece on the board.
	 */
	protected Square location;
	
	/**
	 * Returns the location of this piece on the board.
	 * @return
	 */
	public Square getLocation() {
		return location;
	}

	/**
	 * Sets the location of this piece on the board.
	 * @param _location The location that this piece will move.
	 */
	public void markMovedTo(Square _location) {
		this.location = _location;
	}
	
	/**
	 * Sets the location of this piece on the board for the first time.
	 * @param _location
	 */
	public void markSpawned(Square _location) {
		this.location = _location;
	}

	/**
	 * The initial for this piece type.
	 */
	public abstract String initial();

	/**
	 * Returns a list of possible paths that this piece can take.
	 * @return
	 */
	public abstract ArrayList<MovementPath> getPaths();
	
	/**
	 * Initialize this pieces board and team.
	 * @param _board
	 * @param _team
	 */
	public Piece(Board _board, TeamColor _team) {
		this.board = _board;
		this.team = _team;
	}
	
	/**
	 * Returns the orientation of this piece according to the
	 * information in the game object.
	 * @return
	 */
	public Orientation getOrientation() {
		return this.board.game.getOrientations().get(this.team);
	}
	
	/**
	 * Determines whether or not this piece can move to the given destination.
	 * This is done by looping through all the paths found in {@link this#getPaths()}
	 * and looking to see if any of those paths contain _destination as a
	 * possible square that this piece can land on.
	 * @param _destination
	 * @return
	 */
	public boolean canMove(Square _destination) {
		for (MovementPath path : this.getPaths()) {
			if (path.containsDestination(_destination) ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determines whether or not this piece can attack the given _piece. This is
	 * done by just seeing if this piece can move to the square of _piece.
	 * @param _piece
	 * @return
	 */
	public boolean canAttack(Piece _piece) {
		return this.canMove(_piece.location);
	}
	
	/**
	 * Determines if this piece could attack the given square if an enemy piece were
	 * in that square. This is different than {@link this#canAttack(Piece)} because
	 * canAttack evaluates based on where the piece that it's attacking currently is.
	 * This method, however, simply determines if it is currently in range of attacking
	 * a square. This is specifically useful for pawns as this simulates there being a
	 * piece in _location to trigger it's diagonal attack.
	 * @param _location
	 * @return
	 */
	public boolean couldAttack(Square _location) {
		return this.canMove(_location);
	}
	
	/**
	 * Returns a string representation of this piece. This returns the initials for
	 * it's color and piece type.
	 */
	public String toString() {
		return this.team.initial + this.initial();
	}
	
	/**
	 * Copies this piece but doesn't spawn this piece anywhere. The _board
	 * is the copy of the board that this piece will go on.
	 * @return
	 */
	public abstract Piece copy(Board _board);
	
	/**
	 * Loops through each path in _paths and, if the king would still
	 * be in check if that move is made, then that path is removed.
	 * @param _paths
	 */
	protected void evaluateKingInCheck(ArrayList<MovementPath> _paths) {
		if (this.board.isSimulation)
			return;
		ArrayList<MovementPath> toRemove = new ArrayList<MovementPath>();
		for (MovementPath path : _paths) {
			ArrayList<Square> removeFromPath = new ArrayList<Square>();
			for (Square square : path) {
				if (path.containsDestination(square)) {
					Board simulatedBoard = this.board.simulateMove(this.location, square);
					King thisPiecesKing = simulatedBoard.getKing(this.team);
					if (thisPiecesKing.isInCheckAssumeSimulation(simulatedBoard))
						removeFromPath.add(square);
				}
			}
			path.removeAll(removeFromPath);
			if (path.size() == 0) {
				toRemove.add(path);
			}
		}
		_paths.removeAll(toRemove);
	}
}
