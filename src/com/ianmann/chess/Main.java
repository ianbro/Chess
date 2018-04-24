package com.ianmann.chess;
	
import com.ianmann.chess.game.Game;
import com.ianmann.chess.game.movement.Orientation;
import com.ianmann.chess.gui.BoardSquareContainer;
import com.ianmann.chess.gui.GameScreen;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Orientation.initRelativeOrientation();
		
		Game game = new Game();
		
		try {
//			BoardSquareContainer root = new BoardSquareContainer(game, "development", game.getBoard());
			GameScreen root = new GameScreen(game, "development");
			Scene scene = new Scene(root.value);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
