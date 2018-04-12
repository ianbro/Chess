/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 */
package com.ianmann.chess.game.pieces;

import java.util.ArrayList;

import com.ianmann.chess.game.Game;
import com.ianmann.chess.game.Piece;
import com.ianmann.chess.game.TeamColor;
import com.ianmann.chess.game.movement.MovementPath;
import com.ianmann.chess.game.movement.Square;
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
	public Pawn(Game _game, TeamColor _team) {
		super(_game, _team);
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

		MovementPath pathForward = new MovementPath(this.location, this.getOrientation(), false);
		pathForward.build(Direction.FORWARD, 1);
		paths.add(pathForward);
		
		if (!this.hasMoved) {
			MovementPath pathForwardDouble = new MovementPath(this.location, this.getOrientation(), false);
			pathForwardDouble.build(Direction.FORWARD, 2);
			paths.add(pathForwardDouble);
		}
		
		MovementPath pathForwardDiagRight = new MovementPath(this.location, this.getOrientation(), false);
		pathForwardDiagRight.buildDiagonal(Direction.FORWARD, Direction.RIGHT, 1);
		if (pathForwardDiagRight.getLast().hasPiece() && !pathForwardDiagRight.getLast().hasPiece(this.team)) {
			paths.add(pathForwardDiagRight);
		}
		
		MovementPath pathForwardDiagLeft = new MovementPath(this.location, this.getOrientation(), false);
		pathForwardDiagRight.buildDiagonal(Direction.FORWARD, Direction.LEFT, 1);
		if (pathForwardDiagLeft.getLast().hasPiece() && !pathForwardDiagLeft.getLast().hasPiece(this.team)) {
			paths.add(pathForwardDiagLeft);
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
