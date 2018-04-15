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
import com.ianmann.chess.game.movement.Direction;
import com.ianmann.chess.game.movement.MovementPath;
import com.ianmann.chess.game.movement.Square;

/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 *
 */
public class King extends Piece {

	/**
	 * @param _team
	 */
	public King(Game _game, TeamColor _team) {
		super(_game, _team);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#getPaths()
	 */
	/**
	 * This king can move to all adjacent squares unless the square
	 * would put him in check.
	 */
	@Override
	public ArrayList<MovementPath> getPaths() {
		ArrayList<MovementPath> paths = new ArrayList<MovementPath>();
		
		MovementPath pathForward = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathForward.build(Direction.FORWARD, 1);
		if (pathForward.finish(false))
			paths.add(pathForward);
		
		MovementPath pathRight = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathRight.build(Direction.RIGHT, 1);
		if (pathRight.finish(false))
			paths.add(pathRight);
		
		MovementPath pathBackward = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathBackward.build(Direction.BACKWARD, 1);
		if (pathBackward.finish(false))
			paths.add(pathBackward);
		
		MovementPath pathLeft = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathLeft.build(Direction.LEFT, 1);
		if (pathLeft.finish(false))
			paths.add(pathLeft);
		
		MovementPath pathForwardRight = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathForwardRight.buildDiagonal(Direction.FORWARD, Direction.RIGHT, 1);
		if (pathForwardRight.finish(false))
			paths.add(pathForwardRight);
		
		MovementPath pathForwardLeft = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathForwardLeft.buildDiagonal(Direction.FORWARD, Direction.LEFT, 1);
		if (pathForwardLeft.finish(false))
			paths.add(pathForwardLeft);
		
		MovementPath pathBackwardRight = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathBackwardRight.buildDiagonal(Direction.BACKWARD, Direction.RIGHT, 1);
		if (pathBackwardRight.finish(false))
			paths.add(pathBackwardRight);
		
		MovementPath pathBackwardLeft = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathBackwardLeft.buildDiagonal(Direction.BACKWARD, Direction.LEFT, 1);
		if (pathBackwardLeft.finish(false))
			paths.add(pathBackwardLeft);
		
		/*
		 * Stack overflow exception because evaluating other kings can move.
		 */
		evaluateMovesIntoCheck(paths);
		
		return paths;
	}
	
	/**
	 * Does the same thing as {@link this#getPaths()} except this
	 * method doesn't check for moving into check. This is specifically
	 * used when checking if the enemy king would be moving into check
	 * against this king.
	 * @return
	 */
	public ArrayList<MovementPath> getPathsIgnoreMoveIntoCheck() {
		ArrayList<MovementPath> paths = new ArrayList<MovementPath>();
		
		MovementPath pathForward = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathForward.build(Direction.FORWARD, 1);
		if (pathForward.finish(false))
			paths.add(pathForward);
		
		MovementPath pathRight = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathRight.build(Direction.RIGHT, 1);
		if (pathRight.finish(false))
			paths.add(pathRight);
		
		MovementPath pathBackward = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathBackward.build(Direction.BACKWARD, 1);
		if (pathBackward.finish(false))
			paths.add(pathBackward);
		
		MovementPath pathLeft = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathLeft.build(Direction.LEFT, 1);
		if (pathLeft.finish(false))
			paths.add(pathLeft);
		
		MovementPath pathForwardRight = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathForwardRight.buildDiagonal(Direction.FORWARD, Direction.RIGHT, 1);
		if (pathForwardRight.finish(false))
			paths.add(pathForwardRight);
		
		MovementPath pathForwardLeft = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathForwardLeft.buildDiagonal(Direction.FORWARD, Direction.LEFT, 1);
		if (pathForwardLeft.finish(false))
			paths.add(pathForwardLeft);
		
		MovementPath pathBackwardRight = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathBackwardRight.buildDiagonal(Direction.BACKWARD, Direction.RIGHT, 1);
		if (pathBackwardRight.finish(false))
			paths.add(pathBackwardRight);
		
		MovementPath pathBackwardLeft = new MovementPath(this.location, this.getOrientation(), false, this.team);
		pathBackwardLeft.buildDiagonal(Direction.BACKWARD, Direction.LEFT, 1);
		if (pathBackwardLeft.finish(false))
			paths.add(pathBackwardLeft);
		
		return paths;
	}
	
	/**
	 * For each square that this king can move to, this loops through each piece and determines
	 * if that piece can attack that square. If so, the path that contains that square is removed
	 * from the _paths list because it is not a valid path then. The king can't move into check.
	 * @param _paths
	 */
	public void evaluateMovesIntoCheck(ArrayList<MovementPath> _paths) {
		ArrayList<MovementPath> toRemove = new ArrayList<MovementPath>();
		for (MovementPath path : _paths) {
			for (Piece enemy : this.game.getLivePieces(this.team.oponent())) {
				try {
					if (enemy.couldAttack(path.getLast())) {
						toRemove.add(path);
						break;
					}
				} catch (StackOverflowError e) {
					// There is a piece and it's the other king. But it's in range.
					toRemove.add(path);
					break;
				}
			}
		}
		_paths.removeAll(toRemove);
	}
	
	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#couldAttack(com.ianmann.chess.game.movement.Square)
	 */
	/**
	 * @Override Uses {@link this#getPathsIgnoreMoveIntoCheck()} to
	 * bet the paths where this piece can move. This doesn't make
	 * checks for moving into check since that doesn't matter in this
	 * case.
	 */
	public boolean couldAttack(Square _location) {
		ArrayList<MovementPath> paths = this.getPathsIgnoreMoveIntoCheck();
		for (MovementPath path : paths) {
			if (path.containsDestination(_location) ) {
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
		return "K";
	}

}