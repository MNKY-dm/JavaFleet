package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
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
    private VBox shipsVbox;

    @FXML
    private Button okButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Button rotateButton;

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing ShipPlacementController");
        ships = GameManager.getInstance().getGame().getCurrentPlayer().getShips();

        refreshShipsList();
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
                this.shipsVbox.getChildren().add(shipLabel);
            }
        }
        System.out.println("Tous les bateaux sont placés dans la VBox.");
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
