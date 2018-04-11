/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 */
package com.ianmann.chess.game.movement;

import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * <p>
 * Represents a collection of {@link Square}s that a piece can
 * move through to get to it's destination.
 * </p>
 *
 * @author Ian
 * Created: Apr 8, 2018
 *
 */
public class MovementPath extends LinkedList<Square> {
	
	/**
	 * The starting point of this path.
	 */
	private Square root;
	
	/**
	 * The forward orientation of this path.
	 */
	private Orientation orientation;
	
	/**
	 * <p>
	 * Represents whether or not this path is a continuous path
	 * (movement can end at any point along this path) or a
	 * destination path (movement can only end at the end of this
	 * path).
	 * </p>
	 * <p>
	 * If true, this is a continuous path, otherwise, this is a
	 * destination path.
	 * </p>
	 */
	private boolean isContinuous;
	
	/**
	 * Determines whether or not this path is diagonal. If
	 * this is true, then only squares along the diagonal
	 * path are considered points on this path, and not
	 * the vertical/horizontal path squares.
	 */
	private boolean isDiagonal;
	
	/**
	 * Instantiate an abstraction of a path of movement that a
	 * game piece can take.
	 * @param _root
	 */
	public MovementPath(Square _root, Orientation _orientation, boolean _isCountinuous, boolean _isDiagonal) {
		this.root = _root;
		this.orientation = _orientation;
		this.isContinuous = _isCountinuous;
		this.isDiagonal = _isDiagonal;
	}
	
	/**
	 * Adds up to [_length] number of squares to the north of this square.
	 * The number of squares that were added is returned. There may not be _length
	 * number of squares in this direction due to the edge of the board.
	 * @param length
	 * @return
	 */
	public int buildNorth(int _length) {
		int count = 0;
		for (; count < _length; count ++) {
			if (this.root.getNorthNeighbor() == null) {
				return count;
			}

			this.add(this.root.getNorthNeighbor());
		}
		return count;
	}
	
	/**
	 * Adds up to [_length] number of squares to the east of this square.
	 * The number of squares that were added is returned. There may not be _length
	 * number of squares in this direction due to the edge of the board.
	 * @param length
	 * @return
	 */
	public int buildEast(int _length) {
		int count = 0;
		for (; count < _length; count ++) {
			if (this.root.getEastNeighbor() == null) {
				return count;
			}

			this.add(this.root.getEastNeighbor());
		}
		return count;
	}
	
	/**
	 * Adds up to [_length] number of squares to the west of this square.
	 * The number of squares that were added is returned. There may not be _length
	 * number of squares in this direction due to the edge of the board.
	 * @param length
	 * @return
	 */
	public int buildWest(int _length) {
		int count = 0;
		for (; count < _length; count ++) {
			if (this.root.getWestNeighbor() == null) {
				return count;
			}

			this.add(this.root.getWestNeighbor());
		}
		return count;
	}
	
	/**
	 * Adds up to [_length] number of squares to the south of this square.
	 * The number of squares that were added is returned. There may not be _length
	 * number of squares in this direction due to the edge of the board.
	 * @param length
	 * @return
	 */
	public int buildSouth(int _length) {
		int count = 0;
		for (; count < _length; count ++) {
			if (this.root.getSouthNeighbor() == null) {
				return count;
			}

			this.add(this.root.getSouthNeighbor());
		}
		return count;
	}
	
	/**
	 * Adds up to [_length] number of squares in the given direction of this square.
	 * The number of squares that were added is returned. There may not be _length
	 * number of squares in this direction due to the edge of the board.
	 * @param length
	 * @return
	 */
	public int build(Direction _direction, int _length) {
		int count = 0;
		for (; count < _length; count ++) {
			Square neighbor = this.root.neighbor(this.orientation, _direction);
			if (neighbor == null) {
				return count;
			}

			this.add(neighbor);
		}
		return count;
	}
}
