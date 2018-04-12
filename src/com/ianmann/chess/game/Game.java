/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 */
package com.ianmann.chess.game;

import java.util.ArrayList;
import java.util.HashMap;

import com.ianmann.chess.game.movement.Direction;
import com.ianmann.chess.game.movement.Orientation;
import com.ianmann.chess.game.movement.Square;
import com.ianmann.chess.game.pieces.Bishop;
import com.ianmann.chess.game.pieces.King;
import com.ianmann.chess.game.pieces.Knight;
import com.ianmann.chess.game.pieces.Pawn;
import com.ianmann.chess.game.pieces.Queen;
import com.ianmann.chess.game.pieces.Rook;

/**
 * <p>
 * Represents a game of Chess. Objects of this type contain the
 * information and data for a complete game including players,
 * team mapping, pieces on the board, and other data.
 * </p>
 * <p>
 * This will act as the center piece of the program.
 * </p>
 * @author Ian
 * Created: Apr 8, 2018
 *
 */
public class Game {

	/**
	 * <p>
	 * Maps each team to the player that controls that team.
	 * </p>
	 */
	private HashMap<TeamColor, Player> teams;
	
	/**
	 * Maps each team to the direction that they are facing.
	 */
	private HashMap<TeamColor, Orientation> orientations = new HashMap<TeamColor, Orientation>()
	{{
		put (TeamColor.WHITE, Orientation.SOUTH);
		put (TeamColor.BLACK, Orientation.NORTH);
	}};
	
	/**
	 * The board that this game controls.
	 */
	private Board board;
	
	/**
	 * Map of each team and their pieces in play on the board. These
	 * pieces have not been captured by the other team yet.
	 */
	private HashMap<TeamColor, ArrayList<Piece>> livePieces = new HashMap<TeamColor, ArrayList<Piece>>()
	{{
		put(TeamColor.BLACK, new ArrayList<Piece>());
		put(TeamColor.WHITE, new ArrayList<Piece>());
	}};
	
	/**
	 * Map of each team and their pieces which have been captured. These
	 * pieces have been captured by the other team.
	 */
	private HashMap<TeamColor, ArrayList<Piece>> capturedPieces = new HashMap<TeamColor, ArrayList<Piece>>()
	{{
		put(TeamColor.BLACK, new ArrayList<Piece>());
		put(TeamColor.WHITE, new ArrayList<Piece>());
	}};

	/**
	 * @return the teams
	 */
	public HashMap<TeamColor, Player> getTeams() {
		return teams;
	}

	/**
	 * @return the orientations
	 */
	public HashMap<TeamColor, Orientation> getOrientations() {
		return orientations;
	}
	
	/**
	 * @see {@link Game#board}
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Instantiates a game object.
	 */
	public Game() {
		this.board = new Board();
		this.initTeam(TeamColor.WHITE);
		this.initTeam(TeamColor.BLACK);
	}
	
	public void initTeam(TeamColor _team) {
		this.spawnTeam(_team);
	}
	
	/**
	 * Places the initial pieces on the board for a given team.
	 * @param _team
	 */
	public void spawnTeam(TeamColor _team) {
		ArrayList<Piece> pieces = this.livePieces.get(_team);
		Rook r1 = new Rook(this, _team);
		pieces.add(r1);
		this.board.spawnPiece(r1);

		Rook r2 = new Rook(this, _team);
		pieces.add(r2);
		this.board.spawnPiece(r2);
		
		Knight n1 = new Knight(this, _team);
		pieces.add(n1);
		this.board.spawnPiece(n1);
		
		Knight n2 = new Knight(this, _team);
		pieces.add(n2);
		this.board.spawnPiece(n2);
		
		Bishop b1 = new Bishop(this, _team);
		pieces.add(b1);
		this.board.spawnPiece(b1);
		
		Bishop b2 = new Bishop(this, _team);
		pieces.add(b2);
		this.board.spawnPiece(b2);
		
		Queen q = new Queen(this, _team);
		pieces.add(q);
		this.board.spawnPiece(q);
		
		King k = new King(this, _team);
		pieces.add(k);
		this.board.spawnPiece(k);
		
		for (int i = 0; i < this.board.width; i ++) {
			Pawn p = new Pawn(this, _team);
			pieces.add(p);
			this.board.spawnPiece(p);
		}
	}
}