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
		
		return paths;
	}

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#initial()
	 */
	@Override
	public String initial() {
		return "K";
	}

}