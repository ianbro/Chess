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
import com.ianmann.chess.game.movement.Orientation;

/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 *
 */
public class Knight extends Piece {

	/**
	 * @param _team
	 */
	public Knight(Game _game, TeamColor _team) {
		super(_game, _team);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#getPaths()
	 */
	@Override
	public ArrayList<MovementPath> getPaths() {
ArrayList<MovementPath> paths = new ArrayList<MovementPath>();
		
		MovementPath pathForwardRight = new MovementPath(this.location, Orientation.NORTH, false, this.team);
		pathForwardRight.build(Direction.FORWARD, 2);
		pathForwardRight.build(Direction.RIGHT, 1);
		if (pathForwardRight.finish(true))
			paths.add(pathForwardRight);
		
		MovementPath pathBackwardRight = new MovementPath(this.location, Orientation.NORTH, false, this.team);
		pathBackwardRight.build(Direction.BACKWARD, 2);
		pathBackwardRight.build(Direction.RIGHT, 1);
		if (pathBackwardRight.finish(true))
			paths.add(pathBackwardRight);
		
		MovementPath pathForwardLeft = new MovementPath(this.location, Orientation.NORTH, false, this.team);
		pathForwardLeft.build(Direction.FORWARD, 2);
		pathForwardLeft.build(Direction.LEFT, 1);
		if (pathForwardLeft.finish(true))
			paths.add(pathForwardLeft);
		
		MovementPath pathBackwardLeft = new MovementPath(this.location, Orientation.NORTH, false, this.team);
		pathBackwardLeft.build(Direction.BACKWARD, 2);
		pathBackwardLeft.build(Direction.LEFT, 1);
		if (pathBackwardLeft.finish(true))
			paths.add(pathBackwardLeft);
		
		MovementPath pathRightForward = new MovementPath(this.location, Orientation.NORTH, false, this.team);
		pathRightForward.build(Direction.RIGHT, 2);
		pathRightForward.build(Direction.FORWARD, 1);
		if (pathRightForward.finish(true))
			paths.add(pathRightForward);
		
		MovementPath pathRightBackward = new MovementPath(this.location, Orientation.NORTH, false, this.team);
		pathRightBackward.build(Direction.RIGHT, 2);
		pathRightBackward.build(Direction.BACKWARD, 1);
		if (pathRightBackward.finish(true))
			paths.add(pathRightBackward);
		
		MovementPath pathLeftForward = new MovementPath(this.location, Orientation.NORTH, false, this.team);
		pathLeftForward.build(Direction.LEFT, 2);
		pathLeftForward.build(Direction.FORWARD, 1);
		if (pathLeftForward.finish(true))
			paths.add(pathLeftForward);
		
		MovementPath pathLeftBackward = new MovementPath(this.location, Orientation.NORTH, false, this.team);
		pathLeftBackward.build(Direction.LEFT, 2);
		pathLeftBackward.build(Direction.BACKWARD, 1);
		if (pathLeftBackward.finish(true))
			paths.add(pathLeftBackward);
		
		return paths;
	}

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#initial()
	 */
	@Override
	public String initial() {
		return "N";
	}

}
