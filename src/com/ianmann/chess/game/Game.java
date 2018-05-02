/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 8, 2018
 */
package com.ianmann.chess.game;

import java.util.ArrayList;
import java.util.HashMap;

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
	 * Maps each team to the direction that they are facing. If you switch these, make sure to switch
	 * the sides of the board that the teams spawn on in {@link this#board#startingPositions}.
	 */
	private HashMap<TeamColor, Orientation> orientations = new HashMap<TeamColor, Orientation>()
	{{
		put (TeamColor.WHITE, Orientation.NORTH);
		put (TeamColor.BLACK, Orientation.SOUTH);
	}};
	
	/**
	 * The board that this game controls.
	 */
	private Board board;
	
	/**
	 * Map of each team and their pieces which have been captured. These
	 * pieces have been captured by the other team.
	 */
	private HashMap<TeamColor, HashMap<Class<? extends Piece>, ArrayList<Piece>>> capturedPieces = new HashMap<TeamColor, HashMap<Class<? extends Piece>, ArrayList<Piece>>>()
	{{
		put(TeamColor.BLACK, new HashMap<Class<? extends Piece>, ArrayList<Piece>>());
		put(TeamColor.WHITE, new HashMap<Class<? extends Piece>, ArrayList<Piece>>());
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
	 * Returns the captured pieces for this team.
	 * @return
	 */
	public HashMap<Class<? extends Piece>, ArrayList<Piece>> getCapturedPieces(TeamColor _team) {
		return this.capturedPieces.get(_team);
	}
	
	/**
	 * @see {@link Game#board}
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Read only access to {@link this#currentTurnTeam}.
	 * @return
	 */
	public TeamColor getCurrentTurnTeam() {
		return this.currentTurnTeam;
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
		HashMap<Class<? extends Piece>, ArrayList<Piece>> grave = this.capturedPieces.get(_piece.team);
		
		if (livePieces.contains(_piece)) {
			_piece.location.markPieceRemoved(_piece);
			_piece.markMovedTo(null);
			if (grave.containsKey(_piece.getClass()))
				grave.get(_piece.getClass()).add(_piece);
			else {
				grave.put(_piece.getClass(), new ArrayList<Piece>(){{
					add(_piece);
				}});
			}
			livePieces.remove(_piece);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Determines whether or not the given team's king is in check.
	 * @return
	 */
	public boolean teamIsInCheck(TeamColor _team) {
		return this.board.getKing(_team).isInCheck();
	}
	
	/**
	 * <p>
	 * Determines whether or not the given team's king is in check-mate (They are
	 * in check and they have no valid move to make that would get their king out of
	 * check.
	 * </p>
	 * <p>
	 * A team may have no valid moves but not be in check mate. This is only the case if it
	 * is not currently their turn. If the other team moves so that this team then has
	 * a valid move, they will no longer be in stale mate and so this should always return
	 * false if the games current moving team is not _team.
	 * </p>
	 * @return
	 */
	public boolean teamIsInCheckMate(TeamColor _team) {
		if (!this.teamIsInCheck(_team) || this.currentTurnTeam != _team) return false;
		
		for (Piece piece : this.getLivePieces(_team)) {
			if (piece.getPaths().size() > 0) {
				return false;
			}
		}
		/* If the loop finishes, then no piece was found to have a valid path so this team is
		in check mate. */
		return true;
	}
	
	/**
	 * <p>
	 * Determines whether or not the given team has been put in stale-mate (Their
	 * king is not in check but they have no valid move to make.
	 * </p>
	 * <p>
	 * A team may have no valid moves but not be in stale mate. This is only the case if it
	 * is not currently their turn. If the other team moves so that this team then has
	 * a valid move, they will no longer be in stale mate and so this should always return
	 * false if the games current moving team is not _team.
	 * </p>
	 * @param _team
	 * @return
	 */
	public boolean teamIsInStaleMate(TeamColor _team) {
		if (this.currentTurnTeam != _team) return false;
		
		for (Piece piece : this.getLivePieces(_team)) {
			if (piece.getPaths().size() > 0) {
				return false;
			}
		}
		/* If the loop finishes, then no piece was found to have a valid path so this team is
		in stale mate. */
		return true;
	}
	
	/**
	 * <p>
	 * Evaluates and performs a move. This moves the piece from
	 * the square in _fromSquare to the square in _toSquare. If either
	 * of these are null or if the _fromSquare has no piece of the
	 * current teams, then nothing will happen. If the move was
	 * successful, the current marker for the current team to move will
	 * be set to the next team to go.
	 * </p>
	 * <p>
	 * Returns true if the move was successful; false otherwise.
	 * </p>
	 * @param _from
	 * @param _to
	 */
	public boolean takeTurn(Square _from, Square _to) {
		if (_from == null || _to == null)
			return false;
		if (!_from.hasPiece(this.currentTurnTeam))
			return false;
		boolean moveSuccessful = this.getBoard().movePiece(_from, _to);
		if (moveSuccessful)
			this.beginNextTurn();
		return moveSuccessful;
	}
	
	/**
	 * Does logic to begin a move such as setting the team that is currently going and
	 * checking win conditions.
	 */
	public void beginNextTurn() {
		this.currentTurnTeam = this.currentTurnTeam.oponent();
		for (int i = 0; i < this.getLivePieces(this.currentTurnTeam.oponent()).size(); i ++) {
			if (!Pawn.class.isInstance(this.getLivePieces(this.currentTurnTeam.oponent()).get(i)))
				continue;
			Pawn enemyPawn = (Pawn) this.getLivePieces(this.currentTurnTeam.oponent()).get(i);
			enemyPawn.clearEnPassentMoves();
		}
		if (this.teamIsInCheckMate(this.currentTurnTeam))
			System.out.println("Check mate!");
		else if (this.teamIsInStaleMate(this.currentTurnTeam))
			System.out.println("Stale mate!");
	}
}
