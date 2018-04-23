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
	 * The team that currently can go on the current turn. The
	 * opponent pieces cannot move at all on this turn.
	 */
	private TeamColor currentTurnTeam = TeamColor.WHITE;

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
	 * Returns the live pieces for this team.
	 * @return
	 */
	public ArrayList<Piece> getLivePieces(TeamColor _team) {
		return this.board.getLivePieces(_team);
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
		this.board = new Board(this);
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
		Rook r1 = new Rook(this.board, _team);
		this.board.spawnPiece(r1);

		Rook r2 = new Rook(this.board, _team);
		this.board.spawnPiece(r2);
		
		Knight n1 = new Knight(this.board, _team);
		this.board.spawnPiece(n1);
		
		Knight n2 = new Knight(this.board, _team);
		this.board.spawnPiece(n2);
		
		Bishop b1 = new Bishop(this.board, _team);
		this.board.spawnPiece(b1);
		
		Bishop b2 = new Bishop(this.board, _team);
		this.board.spawnPiece(b2);
		
		Queen q = new Queen(this.board, _team);
		this.board.spawnPiece(q);
		
		King k = new King(this.board, _team);
		this.board.spawnPiece(k);
		
		for (int i = 0; i < this.board.width; i ++) {
			Pawn p = new Pawn(this.board, _team);
			this.board.spawnPiece(p);
		}
	}
	
	/**
	 * Takes the given piece off the board and places them in the grave for its team.
	 * @param _piece
	 * @return
	 */
	public boolean capturePiece(Piece _piece) {
		ArrayList<Piece> livePieces = this.getLivePieces(_piece.team);
		ArrayList<Piece> grave = this.capturedPieces.get(_piece.team);
		
		if (livePieces.contains(_piece)) {
			_piece.location.markPieceRemoved(_piece);
			_piece.markMovedTo(null);
			grave.add(_piece);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Evaluates and performs a move. This moves the piece from
	 * the square in _fromSquare to the square in _toSquare. If either
	 * of these are null or if the _fromSquare has no piece of the
	 * current teams, then nothing will happen. If the move was
	 * successful, the current marker for the current team to move will
	 * be set to the next team to go.
	 * @param _from
	 * @param _to
	 */
	public void takeTurn(Square _from, Square _to) {
		boolean turnHappened = false;
		if (_from == null || _to == null)
			return;
		if (!_from.hasPiece(this.currentTurnTeam))
			return;
		turnHappened = this.getBoard().movePiece(_from, _to);
		if (turnHappened) {
			this.currentTurnTeam = this.currentTurnTeam.oponent();
		}
	}
}
