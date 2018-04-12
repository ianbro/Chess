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
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Orientation.initRelativeOrientation();
		
		Game game = new Game();
		
		Piece testPiece = game.getBoard().squares.get("7-7").getPiece();
		
		Scanner s = new Scanner(System.in);
		while (true) {
			System.out.println(game.getBoard().toBoardString());
			System.out.println(testPiece.getPaths());
			String input = s.nextLine();
			if (input.equals("done")) { break; }
			String[] inputSquares = input.split(">");
			game.getBoard().movePiece(inputSquares[0], inputSquares[1]);
		}
		s.close();
	}

}
