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
public enum Orientation {
	NORTH, EAST, SOUTH, WEST;
	
	/**
	 * The orientations relative to each one according to the direction
	 * in each corresponding key.
	 */
	public HashMap<Direction, Orientation> relativeOrientations = new HashMap<Direction, Orientation>();
	
	/**
	 * Set the relative directions for each directions according to
	 * the directions in {@link Direction}.
	 */
	public static void initRelativeOrientation() {
		NORTH.relativeOrientations.put(Direction.FORWARD, Orientation.NORTH);
		NORTH.relativeOrientations.put(Direction.RIGHT, Orientation.EAST);
		NORTH.relativeOrientations.put(Direction.BACKWARD, Orientation.SOUTH);
		NORTH.relativeOrientations.put(Direction.LEFT, Orientation.WEST);
		
		EAST.relativeOrientations.put(Direction.FORWARD, Orientation.EAST);
		EAST.relativeOrientations.put(Direction.RIGHT, Orientation.SOUTH);
		EAST.relativeOrientations.put(Direction.BACKWARD, Orientation.WEST);
		EAST.relativeOrientations.put(Direction.LEFT, Orientation.NORTH);

		SOUTH.relativeOrientations.put(Direction.FORWARD, Orientation.SOUTH);
		SOUTH.relativeOrientations.put(Direction.RIGHT, Orientation.WEST);
		SOUTH.relativeOrientations.put(Direction.BACKWARD, Orientation.NORTH);
		SOUTH.relativeOrientations.put(Direction.LEFT, Orientation.EAST);

		WEST.relativeOrientations.put(Direction.FORWARD, Orientation.WEST);
		WEST.relativeOrientations.put(Direction.RIGHT, Orientation.NORTH);
		WEST.relativeOrientations.put(Direction.BACKWARD, Orientation.EAST);
		WEST.relativeOrientations.put(Direction.LEFT, Orientation.SOUTH);
	}
}
