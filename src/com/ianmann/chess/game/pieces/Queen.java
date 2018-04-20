/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 */
package com.ianmann.chess.game.pieces;

import java.util.ArrayList;

import com.ianmann.chess.game.Board;
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
public class Queen extends Piece {

	/**
	 * @param _team
	 */
	public Queen(Board _board, TeamColor _team) {
		super(_board, _team);
	}

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#getPaths()
	 */
	@Override
	public ArrayList<MovementPath> getPaths() {
ArrayList<MovementPath> paths = new ArrayList<MovementPath>();
		
		MovementPath pathForward = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathForward.build(Direction.FORWARD, this.board.height);
		if (pathForward.finish(false))
			paths.add(pathForward);
		
		MovementPath pathRight = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathRight.build(Direction.RIGHT, this.board.width);
		if (pathRight.finish(false))
			paths.add(pathRight);
		
		MovementPath pathBackward = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathBackward.build(Direction.BACKWARD, this.board.height);
		if (pathBackward.finish(false))
			paths.add(pathBackward);
		
		MovementPath pathLeft = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathLeft.build(Direction.LEFT, this.board.width);
		if (pathLeft.finish(false))
			paths.add(pathLeft);
		
		MovementPath pathForwardRight = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathForwardRight.buildDiagonal(Direction.FORWARD, Direction.RIGHT, this.board.height);
		if (pathForwardRight.finish(false))
			paths.add(pathForwardRight);
		
		MovementPath pathBackwardRight = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathBackwardRight.buildDiagonal(Direction.BACKWARD, Direction.RIGHT, this.board.width);
		if (pathBackwardRight.finish(false))
			paths.add(pathBackwardRight);
		
		MovementPath pathBackwardLeft = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathBackwardLeft.buildDiagonal(Direction.BACKWARD, Direction.LEFT, this.board.height);
		if (pathBackwardLeft.finish(false))
			paths.add(pathBackwardLeft);
		
		MovementPath pathForwardLeft = new MovementPath(this.location, Orientation.NORTH, true, this.team);
		pathForwardLeft.buildDiagonal(Direction.FORWARD, Direction.LEFT, this.board.width);
		if (pathForwardLeft.finish(false))
			paths.add(pathForwardLeft);
		
		this.evaluateKingInCheck(paths);
		
		return paths;
	}

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#initial()
	 */
	@Override
	public String initial() {
		return "Q";
	}

	/**
	 * Returns a copy of this king. This new king is not spawned
	 * on any square though.
	 */
	public Piece copy(Board _board) {
		Queen newQueen = new Queen(_board, this.team);
		return newQueen;
	}

}