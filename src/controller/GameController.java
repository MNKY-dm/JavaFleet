package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Board;
import model.Cell;
import model.Game;
import model.entity.Player;
import type.Coordinate;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private Player currentPlayer;
    private Player opponentPlayer;
    private Cell currentCell;
    private Coordinate previewTarget;

    @FXML
    private Label myNameLabel;

    @FXML
    private Label opponentNameLabel;

    @FXML
    private GridPane opponentBoard;

    @FXML
    private GridPane myBoard;

    @FXML
    private Button[][] cellsButtons = new Button[10][10];

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label myHealthLabel;

    @FXML
    private Label myNbShipsLabel;

    @FXML
    private Label oppsNbShipsLabel;

    public void initialize(URL location, ResourceBundle resources){
        System.out.println("Initializing GameController");

        currentPlayer = GameManager.getInstance().getGame().getCurrentPlayer();

        System.out.println("Current Player: " + currentPlayer);

        initializeMyGridPane(myBoard, currentPlayer.getMyBoard());
    }

    @FXML
    private void initializeMyGridPane(GridPane gridPane, Board board) {
        System.out.println("Initializing MyGridPane");
        util.MadeUpFunctions.appendLabel(myNameLabel, currentPlayer.getName());
        for (int y = 0 ; y < board.getHeight() ; y++) {
            for (int x = 0 ; x < board.getWidth() ; x++) {
                Cell cell = board.getCell(x, y);

                Button cellButton = new Button(); // Crée un bouton
                cellButton.setPrefSize(50, 50);
                if (cell.isOccupied()) { // Si un bateau est placé
                    cellButton.setStyle("-fx-background-color: #615C5C !important; -fx-border-color: #000 !important; -fx-border-width: 0.5 !important;"); // Le colore en gris/marron
                } else {
                    cellButton.setStyle("-fx-background-color: #87CEEB !important; " + "-fx-border-color: #000 !important; " + "-fx-border-width: 0.5 !important;");
                }

                cellButton.setOnAction(event -> {
//                    System.out.println("Cellule cliquée : (" + cell.getX() + ", " + cell.getY() + ")");
                    onGridCellClicked(cell.getX(), cell.getY());
                });
                cellsButtons[x][y] = cellButton;
                gridPane.add(cellButton, x, y); // Ajoute le bouton à la cellule
            }
        }
    }

    @FXML
    private void updateMyGridPane() {
        Board board = currentPlayer.getMyBoard();

        // Commencer par supprimer le preview courant
        if (previewTarget != null) { // Vérifie si des coordonées de preview existent

            if (board.isValidCoordinates(previewTarget.getX(),  previewTarget.getY())) {
                cellsButtons[previewTarget.getX()][previewTarget.getY()].setStyle("-fx-background-color: #87CEEB !important; -fx-border-color: #000; -fx-border-width: 0.5;"); // Remettre la couleur de base pour chaque case de la preview déjà active
            }

            previewTarget = null; // Vider la liste une fois que les couleurs ont été réintialisées
        }

        for (int y = 0 ; y < board.getHeight() ; y++) {
            for (int x = 0 ; x < board.getWidth() ; x++) {
                Cell cell = board.getCell(x, y);

                if (cell.isOccupied()) { // Si un bateau est placé
                    Button boatButton = cellsButtons[x][y]; // Utilise le bouton existant
                    boatButton.setStyle("-fx-background-color: #615C5C !important; -fx-border-color: #000 !important; -fx-border-width: 0.5 !important;"); // Le colore en gris/marron
                }
            }
        }
    }

    private void setCellPreview(Cell cell) {
        cellsButtons[cell.getX()][cell.getY()].setStyle("-fx-background-color: #615C5C !important; -fx-border-color: #000; -fx-border-width: 0.5;");
    }

    private void onGridCellClicked(int x, int y) {
        cellsButtons[currentCell.getX()][currentCell.getY()].setStyle("-fx-background-color: #87CEEB !important; -fx-border-color: #000; -fx-border-width: 0.5;");
        currentCell = currentPlayer.getMyBoard().getCell(x, y);
        setCellPreview(currentCell);
    }

    @FXML
    private void okButtonClicked() {

    }

    @FXML
    private void cancelButtonClicked() {

    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }
}
