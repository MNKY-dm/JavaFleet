package controller;
import javafx.fxml.FXML;
import javafx.scene.layout.Priority;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;


public class GameController {

    @FXML
    private HBox centerHBox;  // Le HBox qui contient les 2 grilles
    @FXML
    private GridPane playerBoardGrid;
    @FXML
    private GridPane opponentBoardGrid;

    public void initialize(URL location, ResourceBundle resources) {
        // Rendre les GridPane flexibles HORIZONTALEMENT seulement
        HBox.setHgrow(playerBoardGrid, Priority.ALWAYS);
        HBox.setHgrow(opponentBoardGrid, Priority.ALWAYS);

        // Rendre les grilles carrées
        playerBoardGrid.prefWidthProperty().bind(playerBoardGrid.heightProperty());
        opponentBoardGrid.prefWidthProperty().bind(opponentBoardGrid.heightProperty());
    }
}
