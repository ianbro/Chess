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
	 * The initial for this color
	 */
	public String initial;
	
	private TeamColor(String _initial) {
		this.initial = _initial;
	}
}
