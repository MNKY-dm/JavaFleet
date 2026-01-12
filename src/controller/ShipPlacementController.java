package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import model.Board;
import model.Cell;
import model.Ship;
import model.entity.Player;
import type.Orientation;

import javafx.scene.control.Button;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ShipPlacementController implements Initializable {
    private Player currentPlayer;
    private Ship currentShip;
    private Orientation currentShipOrientation;
    private ArrayList<Ship> ships;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button[][] cellsButtons = new Button[10][10];

    @FXML
    private VBox shipsVbox;

    @FXML
    private Button okButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Button rotateButton;

    @FXML
    private Label selectedShipLabel;

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing ShipPlacementController");
        ships = GameManager.getInstance().getGame().getCurrentPlayer().getShips();

        refreshShipsList();
        initializeGridPane(gridPane, currentPlayer.getMyBoard());
    }

    private void initializeGridPane(GridPane gridPane, Board board) {
        System.out.println("Initializing shipPlacementGridPane");
        for (int y = 0 ; y < board.getHeight() ; y++) {
            for (int x = 0 ; x < board.getWidth() ; x++) {
                Cell cell = board.getCell(x, y);

                Button cellButton = new Button(); // Crée un bouton
                cellButton.setPrefSize(40, 40);
                cellButton.setStyle("-fx-background-color: #87CEEB !important; " + "-fx-border-color: #000 !important; " + "-fx-border-width: 0.5 !important;");

                cellButton.setOnAction(event -> {
                    System.out.println("Cellule cliquée : (" + cell.getX() + ", " + cell.getY() + ")");
                    onGridCellClicked(cell.getX(), cell.getY());
                });
                cellsButtons[x][y] = cellButton;
                gridPane.add(cellButton, x, y); // Ajoute le bouton à la cellule
            }
        }
    }

    public void refreshShipsList() {
        System.out.println(" Nettoyage de la liste de bateaux. ");
        shipsVbox.getChildren().clear(); // Bien vider la liste des bateaux lorsque la liste doit être initialisée
        for (Ship ship : ships) {
            System.out.println("Boucle sur la liste des bateaux");
            System.out.println("Bateau : " + ship.getShipType());
            System.out.println("Positions : " + Arrays.toString(ship.getPositions()));

            if (ship.getPositions()[0] == null) {
                System.out.println("Bateau non-placé : " + ship.getShipType());
                Label shipLabel = new Label(ship.getShipType());
                System.out.println(ship.getShipType());
                shipLabel.setOnMouseClicked(event -> {
                    onShipLabelClicked(ship, shipLabel);
                });
                this.shipsVbox.getChildren().add(shipLabel);

            }
        }
        System.out.println("Tous les bateaux sont placés dans la VBox.");
    }

    private void onShipLabelClicked(Ship ship, Label shipLabel) {
        if (this.selectedShipLabel == null) {
            shipLabel.setStyle("-fx-background-color: #CCCCCC; -fx-padding: 5;");
            selectedShipLabel = shipLabel;
        } else {
            selectedShipLabel.setStyle("-fx-background-color: transparent;");
            System.out.println("Réinitialisation du style du shipLabel sélectionné précédent");
            selectedShipLabel = shipLabel;
            shipLabel.setStyle("-fx-background-color: #CCCCCC; -fx-padding: 5;");
        }
        currentShip = ship;
        currentShipOrientation = Orientation.HORIZONTAL;
    }

    private void onGridCellClicked(int x, int y) {
        if (currentShip != null) {
            setShipPlacementPreview(x, y);
        } else {
            System.out.println("Sélectionnez un bateau d'abord");
        }
    }

    @FXML
    private void rotateShip() {
        if (currentShipOrientation == Orientation.HORIZONTAL) {
            currentShipOrientation = Orientation.VERTICAL;
        } else {
            currentShipOrientation = Orientation.HORIZONTAL;
        }
    }

    // Getters

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Ship getCurrentShip() {
        return currentShip;
    }

    public Orientation getCurrentShipOrientation() {
        return currentShipOrientation;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    // Setters

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setCurrentShip(Ship currentShip) {
        this.currentShip = currentShip;
    }

    public void setCurrentShipOrientation(Orientation currentShipOrientation) {
        this.currentShipOrientation = currentShipOrientation;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }
}
