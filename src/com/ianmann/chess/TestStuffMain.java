/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 9, 2018
 */
package com.ianmann.chess;

import java.util.Scanner;

import com.ianmann.chess.game.Board;
import com.ianmann.chess.game.Game;
import com.ianmann.chess.game.Piece;
import com.ianmann.chess.game.TeamColor;
import com.ianmann.chess.game.movement.Orientation;
import com.ianmann.chess.game.movement.Square;
import com.ianmann.chess.game.pieces.King;
import com.ianmann.chess.game.pieces.Knight;
import com.ianmann.chess.game.pieces.Pawn;

/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Apr 9, 2018
 *
 */
public class TestStuffMain {
	
	/**
	 * Designates whether or not pieces must move according to their rules. If this is
	 * false, there is not movement restrictions. For example, kings can move in check
	 * or a pawn can move 5 squares at once. This is simply set to false if you want to
	 * set up a scenario and move pieces around.
	 */
	public static boolean movementRestrictionsOn = true;
	
	public static Game game;
	public static Piece testPiece;

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Orientation.initRelativeOrientation();
		
		game = new Game();
		
		testPiece = game.getBoard().squares.get("7-7").getPiece();
		
		Scanner s = new Scanner(System.in);
		while (true) {
			try {
				System.out.println(game.getBoard().toBoardString());
				System.out.println(testPiece.getPaths());
				System.out.println("Conditions: ");
				System.out.println(game.teamIsInCheck(game.getCurrentTurnTeam()));
				System.out.println(game.teamIsInCheckMate(game.getCurrentTurnTeam()));
				System.out.println(game.teamIsInStaleMate(game.getCurrentTurnTeam()));
				String input = s.nextLine();
				
				if (!inputOptions(input))
					break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		s.close();
	}
	
	public static boolean inputOptions(String input) {
		switch (input) {
		case "no-restrict":
			movementRestrictionsOn = false;
			break;
		case "restrict":
			movementRestrictionsOn = true;
			break;
		case "done":
			return false;
		default:
			performMove(input);
			break;
		}
		return true;
	}
	
	public static void performMove(String input) {
		String[] inputSquares = input.split(">");
		if (game.getBoard().squares.get(inputSquares[0]).hasPiece())
			testPiece = game.getBoard().squares.get(inputSquares[0]).getPiece();
		Square fromSquare = game.getBoard().squares.get(inputSquares[0]);
		Square toSquare = game.getBoard().squares.get(inputSquares[1]);
		
		if (movementRestrictionsOn)
			game.takeTurn(fromSquare, toSquare);
		else
			toSquare.placePiece(fromSquare.getPiece());
	}

}
