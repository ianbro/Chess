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
public class Bishop extends Piece {
	
	/**
	 * @param _team
	 */
	public Bishop(Game _game, TeamColor _team) {
		super(_game, _team);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#getPaths()
	 */
	@Override
	public ArrayList<MovementPath> getPaths() {
		ArrayList<MovementPath> paths = new ArrayList<MovementPath>();
		
		MovementPath pathForwardRight = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathForwardRight.buildDiagonal(Direction.FORWARD, Direction.RIGHT, this.game.getBoard().height);
		if (pathForwardRight.finish(false))
			paths.add(pathForwardRight);
		
		MovementPath pathBackwardRight = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathBackwardRight.buildDiagonal(Direction.BACKWARD, Direction.RIGHT, this.game.getBoard().width);
		if (pathBackwardRight.finish(false))
			paths.add(pathBackwardRight);
		
		MovementPath pathBackwardLeft = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathBackwardLeft.buildDiagonal(Direction.BACKWARD, Direction.LEFT, this.game.getBoard().height);
		if (pathBackwardLeft.finish(false))
			paths.add(pathBackwardLeft);
		
		MovementPath pathForwardLeft = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathForwardLeft.buildDiagonal(Direction.FORWARD, Direction.LEFT, this.game.getBoard().width);
		if (pathForwardLeft.finish(false))
			paths.add(pathForwardLeft);
		
		return paths;
	}

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#initial()
	 */
	@Override
	public String initial() {
		return "B";
	}

}