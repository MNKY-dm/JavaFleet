package controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Board;
import model.Cell;
import model.Game;

import java.net.URL;
import java.util.ResourceBundle;


public class GameController implements Initializable {

    @FXML
    private HBox centerHBox;  // Le HBox qui contient les 2 grilles
    @FXML
    private GridPane playerBoardGrid;
    @FXML
    private GridPane opponentBoardGrid;

    public void initialize(URL location, ResourceBundle resources){
        // Initialiser la partie
        System.out.println("Initialising GameController");
        Game game = new Game("Player 1", "Player 2");

        // Récupérer le plateau de chaque joueur
        Board playerBoard = game.getPlayers()[0].getMyBoard();
        Board opponentBoard = game.getPlayers()[1].getMyBoard();

        // Créer des boutons dans les cellules de chaque plateau
        initializeGridPane(playerBoardGrid, playerBoard);
        initializeGridPane(opponentBoardGrid, opponentBoard);

        // Rendre les GridPane flexibles horizontalement
        HBox.setHgrow(playerBoardGrid, Priority.ALWAYS);
        HBox.setHgrow(opponentBoardGrid, Priority.ALWAYS);

        // Rendre les grilles carrées
        playerBoardGrid.prefWidthProperty().bind(playerBoardGrid.heightProperty());
        opponentBoardGrid.prefWidthProperty().bind(opponentBoardGrid.heightProperty());

    }

    private void initializeGridPane(GridPane gridPane, Board board) {
        for (int y = 0 ; y < board.getHeight() ; y++) {
            for (int x = 0 ; x < board.getWidth() ; x++) {
                Cell cell = board.getCell(x, y);

                Button cellButton = new Button(); // Crée un bouton
                cellButton.setPrefSize(40, 40);
                cellButton.setStyle("-fx-background-color: #87CEEB !important; " + "-fx-border-color: #000 !important; " + "-fx-border-width: 0.5 !important;");

                cellButton.setOnAction(event -> {
                    System.out.println("Cellule cliquée : (" + cell.getX() + ", " + cell.getY() + ")");
                });
                gridPane.add(cellButton, x, y); // Ajoute le bouton à la cellule
            }
        }
    }
}
