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
	 * Instantiate an abstraction of a path of movement that a
	 * game piece can take.
	 * @param _root
	 * @param _orientation
	 * @param _isContinuous Represents whether or not this path is a continuous path
	 * (movement can end at any point along this path) or a
	 * destination path (movement can only end at the end of this
	 * path).
	 */
	public MovementPath(Square _root, Orientation _orientation, boolean _isContinuous) {
		this.root = _root;
		this.orientation = _orientation;
		this.isContinuous = _isContinuous;
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
	 * <p>
	 * Adds up to [_length] number of squares in the given two direction of this square.
	 * The number of squares that were added is returned. There may not be _length
	 * number of squares in this direction due to the edge of the board.
	 * </p>
	 * <p>
	 * The number returned skips will only include squares along the diagonal path, and
	 * not the vertical/horizontal path squares.
	 * </p>
	 * @param length
	 * @return
	 */
	public int buildDiagonal(Direction _sideWays, Direction _verticle, int _length) {
		int count = 0;
		for (; count < _length; count ++) {
			Square neighbor = this.root.neighbor(this.orientation, _sideWays);
			if (neighbor == null) {
				return count;
			}
			Square diagNeighbor = neighbor.neighbor(this.orientation, _verticle);
			if (diagNeighbor == null) {
				return count;
			}

			this.add(diagNeighbor);
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
	
	/**
	 * Determines whether or not this path includes a destination point that is the
	 * given _square. Note that just because this path might pass through this square
	 * does not always mean that the piece following this path can end on that square.
	 * For example, knights can only end their turn at the end of their path, not
	 * anywhere within.
	 * @param _square
	 * @return
	 */
	public boolean containsDestination(Square _square) {
		if (!this.isContinuous) {
			return this.getLast().equals(_square);
		}
		if (this.isContinuous) {
			return this.contains(_square);
		}
		return false;
	}
}
