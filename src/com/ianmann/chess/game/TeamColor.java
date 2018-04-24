/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 */
package com.ianmann.chess.game;

/**
 * <p>
 * Designator to represent a color on the board.
 * </p>
 *
 * @author Ian
 * Created: Apr 8, 2018
 *
 */
public enum TeamColor {
	
	/**
	 * <p>
	 * Represents the black team in a game.
	 * </p>
	 */
	BLACK("B"),
	
	/**
	 * <p>
	 * Represents the white team in a game.
	 * </p>
	 */
	WHITE("W");
	
	/**
	 * The initial for this color (either 'W' or 'B').
	 */
	public String initial;
	
	private TeamColor(String _initial) {
		this.initial = _initial;
	}
	
	/**
	 * Returns the opponent team of this color.
	 * @return
	 */
	public TeamColor oponent() {
		if (this == TeamColor.WHITE) {
			return TeamColor.BLACK;
		} else {
			return TeamColor.WHITE;
		}
	}
}
