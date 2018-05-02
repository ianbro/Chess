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
	public Pawn(Board _board, TeamColor _team) {
		super(_board, _team);
	}

	/**
	 * Designates whether or not this pawn as moved in the
	 * game yet. This is used to determine whether or not this
	 * pawn can move the double first move.
	 */
	private boolean hasMoved = false;
	
	/**
	 * The move that represents an "en-passent" move. This is set any time an enemy pawn
	 * uses its jump move (2 spaces at once for it's first move) in order to pass this
	 * pawn. That enemy pawn will trigger the adding of this move when it moves net to
	 * this pawn in the appropriate manner (2 squares at once). At the beginning of
	 * this pawns opponents turn, all en-passent moves for this pawns team will be removed
	 * because the right to capture en-passent will be forfeited (They didn't take the
	 * move when they had the chance).
	 */
	private ArrayList<MovementPath> enPassentMoves = new ArrayList<MovementPath>();
	
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
		this.enPassentMoves.clear();
	}
	
	public void addEnPassentMove(Pawn _oponent) {
		System.out.println("adding en-passent");
		MovementPath enPassentMove = new MovementPath(this.location, this.getOrientation(), _oponent);
		if (enPassentMove.buildEnPassentMove())
			this.enPassentMoves.add(enPassentMove);
	}
	
	public void clearEnPassentMoves() {
		this.enPassentMoves.clear();
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
			MovementPath pathForwardDouble = new MovementPath(this.location, this.getOrientation(), false, true, this.team);
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
		
		paths.addAll(this.enPassentMoves);
		
		this.evaluateKingInCheck(paths);
		
		return paths;
	}
	
	/**
	 * @Override Pawns may move forward one space if not blocked by any other piece. They may
	 * also attack diagonally to the right or left if able to but may not move diagonally if
	 * not attacking. If a pawn has not moved in the game yet, they may move forward 2 squares.
	 * This method does not include any movement except for the movement that would allow the
	 * pawn to take a piece.
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
		
		paths.addAll(this.enPassentMoves);
		
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

	/**
	 * Returns a copy of this king. This new king is not spawned
	 * on any square though.
	 */
	public Piece copy(Board _board) {
		Pawn newPawn = new Pawn(_board, this.team);
		newPawn.hasMoved = this.hasMoved;
		return newPawn;
	}

}
