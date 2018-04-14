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
	 * The game to which this piece belongs.
	 */
	public Game game;
	
	/**
	 * The team to which this piece belongs.
	 */
	public TeamColor team;
	
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
	public void spawn(Square _location) {
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
	
	public Piece(Game _game, TeamColor _team) {
		this.game = _game;
		this.team = _team;
	}
	
	/**
	 * Returns the orientation of this piece according to the
	 * information in the game object.
	 * @return
	 */
	public Orientation getOrientation() {
		return this.game.getOrientations().get(this.team);
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
}
