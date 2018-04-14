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
	 * @see com.ianmann.chess.game.Piece#markMovedTo(com.ianmann.chess.game.movement.Square)
	 */
	/**
	 * @Override
	 * Pawns cannot move more than one square at a time once they take their first move.
	 * This method will mark that this pawn has already moved and therefore may not move
	 * more than one square at a time in the future.
	 */
	public void markMovedTo(Square _location) {
		super.markMovedTo(_location);
		this.hasMoved = true;
	}

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#getPaths()
	 */
	/**
	 * @Override Pawns may move forward one space if not blocked by any other piece. They may
	 * also attack diagonally to the right or left if able to but may not move diagonally if
	 * not attacking. If a pawn has not moved in the game yet, they may move forward 2 squares.
	 */
	public ArrayList<MovementPath> getPaths() {
		// TODO Auto-generated method stub
		ArrayList<MovementPath> paths = new ArrayList<MovementPath>();

		MovementPath pathForward = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathForward.build(Direction.FORWARD, 1);
		if (pathForward.size() > 0 && pathForward.finish(false) && !pathForward.getLast().hasPiece())
			paths.add(pathForward);
		
		if (!this.hasMoved) {
			MovementPath pathForwardDouble = new MovementPath(this.location, this.getOrientation(), false, this.team);
			pathForwardDouble.build(Direction.FORWARD, 2);
			if (pathForwardDouble.size() > 0 && pathForwardDouble.finish(false) && !pathForwardDouble.getLast().hasPiece())
				paths.add(pathForwardDouble);
		}
		
		MovementPath pathForwardDiagRight = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathForwardDiagRight.buildDiagonal(Direction.FORWARD, Direction.RIGHT, 1);
		if (pathForwardDiagRight.size() > 0 && pathForwardDiagRight.getLast().hasPiece()
								&& !pathForwardDiagRight.getLast().hasPiece(this.team)) {
			if (pathForwardDiagRight.finish(false))
				paths.add(pathForwardDiagRight);
		}
		
		MovementPath pathForwardDiagLeft = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathForwardDiagLeft.buildDiagonal(Direction.FORWARD, Direction.LEFT, 1);
		if (pathForwardDiagLeft.size() > 0 && pathForwardDiagLeft.getLast().hasPiece() && !pathForwardDiagLeft.getLast().hasPiece(this.team)) {
			if (pathForwardDiagLeft.finish(false))
				paths.add(pathForwardDiagLeft);
		}
		
		return paths;
	}
	
	/**
	 * @Override Pawns may move forward one space if not blocked by any other piece. They may
	 * also attack diagonally to the right or left if able to but may not move diagonally if
	 * not attacking. If a pawn has not moved in the game yet, they may move forward 2 squares.
	 */
	public ArrayList<MovementPath> getPathsAsIfCouldAttack() {
		// TODO Auto-generated method stub
		ArrayList<MovementPath> paths = new ArrayList<MovementPath>();

		MovementPath pathForwardDiagRight = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathForwardDiagRight.buildDiagonal(Direction.FORWARD, Direction.RIGHT, 1);
		if (pathForwardDiagRight.size() > 0) {
			if (pathForwardDiagRight.finish(false))
				paths.add(pathForwardDiagRight);
		}
		
		MovementPath pathForwardDiagLeft = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathForwardDiagLeft.buildDiagonal(Direction.FORWARD, Direction.LEFT, 1);
		if (pathForwardDiagLeft.size() > 0) {
			if (pathForwardDiagLeft.finish(false))
				paths.add(pathForwardDiagLeft);
		}
		
		return paths;
	}
	
	/**
	 * @Override Uses {@link this#getPathsAsIfCouldAttack()} instead of the basic
	 * {@link this#getPaths()} for seeing the paths.
	 */
	public boolean couldAttack(Square _destination) {
		for (MovementPath path : this.getPathsAsIfCouldAttack()) {
			if (path.containsDestination(_destination) ) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#initial()
	 */
	@Override
	public String initial() {
		return "P";
	}

}
