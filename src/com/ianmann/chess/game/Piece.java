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
	public Square location;
	
	/**
	 * The initial for this piece type.
	 */
	public abstract String initial();

	/**
	 * Returns a list of possible paths that this piece can take.
	 * @return
	 */
	public abstract ArrayList<MovementPath> getPaths();
	
	public Piece(TeamColor _team) {
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
}
