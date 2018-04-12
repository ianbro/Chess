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
public class Rook extends Piece {

	/**
	 * @param _team
	 */
	public Rook(Game _game, TeamColor _team) {
		super(_game, _team);
	}

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#getPaths()
	 */
	@Override
	public ArrayList<MovementPath> getPaths() {
		ArrayList<MovementPath> paths = new ArrayList<MovementPath>();
		
		MovementPath pathForward = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathForward.build(Direction.FORWARD, this.game.getBoard().height);
		if (pathForward.finish(false))
			paths.add(pathForward);
		
		MovementPath pathRight = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathRight.build(Direction.RIGHT, this.game.getBoard().width);
		if (pathRight.finish(false))
			paths.add(pathRight);
		
		MovementPath pathBackward = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathBackward.build(Direction.BACKWARD, this.game.getBoard().height);
		if (pathBackward.finish(false))
			paths.add(pathBackward);
		
		MovementPath pathLeft = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathLeft.build(Direction.LEFT, this.game.getBoard().width);
		if (pathLeft.finish(false))
			paths.add(pathLeft);
		
		return paths;
	}

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#initial()
	 */
	@Override
	public String initial() {
		return "R";
	}

}
