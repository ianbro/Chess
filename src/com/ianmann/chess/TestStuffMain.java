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
import com.ianmann.chess.game.TeamColor;
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
		Game game = new Game();
		
		Scanner s = new Scanner(System.in);
		while (true) {
			System.out.println(game.getBoard());
			String input = s.nextLine();
			if (input.equals("done")) { break; }
			String[] inputSquares = input.split(">");
			Square from = game.getBoard().squares.get(inputSquares[0]);
			Square to = game.getBoard().squares.get(inputSquares[1]);
			to.placePiece(from.getPiece());
		}
		s.close();
	}

}
