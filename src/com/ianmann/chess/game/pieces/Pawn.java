/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 */
package com.ianmann.chess.game.pieces;

import java.util.ArrayList;

import com.ianmann.chess.game.Piece;
import com.ianmann.chess.game.TeamColor;
import com.ianmann.chess.game.movement.MovementPath;
import com.ianmann.chess.game.movement.Direction;

/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 *
 */
public class Pawn extends Piece {
	
	/**
	 * @param _team
	 */
	public Pawn(TeamColor _team) {
		super(_team);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Designates whether or not this pawn as moved in the
	 * game yet. This is used to determine whether or not this
	 * pawn can move the double first move.
	 */
	private boolean hasMoved = false;

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#getPaths()
	 */
	@Override
	public ArrayList<MovementPath> getPaths() {
		// TODO Auto-generated method stub
		ArrayList<MovementPath> paths = new ArrayList<MovementPath>();

		MovementPath pathForward = new MovementPath(this.location, this.getOrientation(), false, false);
		pathForward.build(Direction.FORWARD, 1);
		paths.add(pathForward);
		
		if (this.hasMoved) {
			MovementPath pathForwardDouble = new MovementPath(this.location, this.getOrientation(), false, false);
			pathForwardDouble.build(Direction.FORWARD, 2);
			paths.add(pathForwardDouble);
		}
		
		return paths;
	}

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#initial()
	 */
	@Override
	public String initial() {
		return "P";
	}

}
