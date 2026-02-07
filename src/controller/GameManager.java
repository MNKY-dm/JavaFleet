package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Game;
import type.GameState;

import java.io.IOException;

public class GameManager {
    private static GameManager gameManager;
    private Game game;
    private Stage primaryStage;
    private Scene currentScene;
    private GameState gameState;


    public void initialize(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Game game = new Game("PLAYER1", "PLAYER2");
        setGame(game);

        try {
            loadScene(GameState.SETUP);
        } catch (IOException e) {
            System.err.println("Erreur dans loadScene : ");
            e.printStackTrace();
        }
    }

    @FXML
    public void loadScene(GameState gameState) throws IOException {
        String title;

        FXMLLoader loader;
        if (gameState == GameState.SETUP) {
            loader = new FXMLLoader(getClass().getResource("/view/shipPlacement.fxml"));
            title = "JavaFleet - Disposition de la flotte";
        } else if (gameState == GameState.PLAYING) {
            loader = new FXMLLoader(getClass().getResource("/view/game.fxml"));
            title = "JavaFleet - Partie";
        } else {
            loader = new FXMLLoader(getClass().getResource("/view/gameOver.fxml"));
            title = "JavaFleet - Game Over";
        }
        Parent root = loader.load();
        Object controllerObj = loader.getController();
        if (controllerObj instanceof ShipPlacementController controller) {
            controller.setCurrentPlayer(game.getCurrentPlayer());
            System.out.println(game.getCurrentPlayer().toString());
        } else if (controllerObj instanceof GameController controller) {
            controller.setCurrentPlayer(game.getPlayers()[1]);
            System.out.println("1 current player  : " + game.getCurrentPlayer());
        }

        Scene scene = new Scene(root);

        primaryStage.setWidth(1000);
        primaryStage.setHeight(900);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(900);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static GameManager getInstance() {
        if (gameManager == null) {
            gameManager = new GameManager();
        }
        return gameManager;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
