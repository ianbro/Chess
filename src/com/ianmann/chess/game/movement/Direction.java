/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 */
package com.ianmann.chess.game.movement;

import java.util.HashMap;

/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 *
 */
public enum Direction {
	FORWARD, RIGHT, BACKWARD, LEFT;
	
	/**
	 * Gets the orientation relative to _orientation according to
	 * _direction.
	 * @param _orientation
	 * @param _direction
	 * @return
	 */
	public Orientation exact(Orientation _orientation) {
		return _orientation.relativeOrientations.get(this);
	}
}
