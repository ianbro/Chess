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
	public Rook(TeamColor _team) {
		super(_team);
	}

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#getPaths()
	 */
	@Override
	public ArrayList<MovementPath> getPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ianmann.chess.game.Piece#initial()
	 */
	@Override
	public String initial() {
		return "R";
	}

}
