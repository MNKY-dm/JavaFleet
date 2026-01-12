package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import model.Board;
import model.Cell;
import model.Ship;
import model.entity.Player;
import type.Coordinate;
import type.Orientation;

import javafx.scene.control.Button;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ShipPlacementController implements Initializable {
    private Player currentPlayer;
    private Ship currentShip;
    private Cell currentCell;
    private Orientation currentShipOrientation;
    private ArrayList<Coordinate> previewCoordinates;
    private ArrayList<Ship> ships;
    private boolean previewIsValid;

    @FXML
    private GridPane myBoardGrid;

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
//        System.out.println("Initializing ShipPlacementController");
        currentPlayer = GameManager.getInstance().getGame().getCurrentPlayer();
        ships = currentPlayer.getShips();

        refreshShipsList();
        initializeGridPane(myBoardGrid, currentPlayer.getMyBoard());
    }

    @FXML
    private void initializeGridPane(GridPane gridPane, Board board) {
//        System.out.println("Initializing shipPlacementGridPane");
        for (int y = 0 ; y < board.getHeight() ; y++) {
            for (int x = 0 ; x < board.getWidth() ; x++) {
                Cell cell = board.getCell(x, y);

                Button cellButton = new Button(); // Crée un bouton
                cellButton.setPrefSize(50, 50);
                cellButton.setStyle("-fx-background-color: #87CEEB !important; " + "-fx-border-color: #000 !important; " + "-fx-border-width: 0.5 !important;");

                cellButton.setOnAction(event -> {
//                    System.out.println("Cellule cliquée : (" + cell.getX() + ", " + cell.getY() + ")");
                    onGridCellClicked(cell.getX(), cell.getY());
                });
                cellsButtons[x][y] = cellButton;
                gridPane.add(cellButton, x, y); // Ajoute le bouton à la cellule
            }
        }
    }

    // Méthode qui affiche une preview du bateau que l'on veut placer avant de confirmer
    private void setShipPlacementPreview(int x, int y) {
        Board board = currentPlayer.getMyBoard();
//        System.out.println("Dans setShipPlacementPreview, currentPlayer = " + currentPlayer);
        try {

            if (previewCoordinates != null && !previewCoordinates.isEmpty()) { // Vérifie si des coordonées de preview existent
//                System.out.println("preview déjà remplie, suppression en cours...");
                for (Coordinate coordinate : previewCoordinates) {
                    if (board.isValidCoordinates(coordinate.getX(),  coordinate.getY())) {
                        if (board.getCell(coordinate.getX(),  coordinate.getY()).isOccupied()) {
//                            System.out.println("Case occupée, retour marron");
                            cellsButtons[coordinate.getX()][coordinate.getY()].setStyle("-fx-background-color: #615C5C !important; -fx-border-color: #000; -fx-border-width: 0.5;"); // Remettre la couleur de base pour chaque case de la preview déjà active
                        } else {
                            cellsButtons[coordinate.getX()][coordinate.getY()].setStyle("-fx-background-color: #87CEEB !important; -fx-border-color: #000; -fx-border-width: 0.5;"); // Remettre la couleur de base pour chaque case de la preview déjà active
                        }
                    }
                }
                previewCoordinates.clear(); // Vider la liste une fois que les couleurs ont été réintialisées
//                System.out.println("preview : suppression effectué");

            }
            boolean isValid = board.canPlaceShip(currentShip, x, y, currentShipOrientation); // Vérifie si on peut placer le bateau sur ces coordonnées

//        System.out.println("Avant calculatePositions:");
//        System.out.println("  currentShip = " + currentShip);
//        System.out.println("  currentShip.getLength() = " + (currentShip != null ? currentShip.getLength() : "null"));
//        System.out.println("  x = " + x + ", y = " + y);
//        System.out.println("  currentShipOrientation = " + currentShipOrientation);
            Coordinate[] positions = board.calculatePositions(currentShip, x, y, currentShipOrientation);

    //        System.out.println("Après calculatePositions:");
    //        System.out.println("  positions.length = " + positions.length);
    //        for (int i = 0; i < positions.length; i++) {
    //            System.out.println("  positions[" + i + "] = " + positions[i]);
    //        }

            previewCoordinates = new ArrayList<>(Arrays.asList(positions));

//            System.out.println("preview inexistante, création en cours...");

            boolean previewPlacable = true;
            for (Coordinate coordinate : previewCoordinates) {
    //            System.out.println("coordonnée : " + coordinate.toString());
                if (board.isValidCoordinates(coordinate.getX(), coordinate.getY())) { // Vérifier uniquement si les coordonnées sont dans la grille
                    if (isValid) { // Si le placement est possible
                        cellsButtons[coordinate.getX()][coordinate.getY()].setStyle("-fx-background-color: #016908 !important; -fx-border-color: #000; -fx-border-width: 0.5;"); // Change la couleur de la case en vert
                    } else { // S'il est impossible
                        cellsButtons[coordinate.getX()][coordinate.getY()].setStyle("-fx-background-color: #BA0404 !important; -fx-border-color: #000; -fx-border-width: 0.5;"); // Change la couleur de la case en rouge
                        previewPlacable = false;
                    }
                }
            }
            previewIsValid = previewPlacable;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Impossible d'afficher un preview car certaines coordonnées sont en dehors de la grille.");

        }
    }

    public void refreshShipsList() {
//        System.out.println(" Nettoyage de la liste de bateaux. ");
        shipsVbox.getChildren().clear(); // Bien vider la liste des bateaux lorsque la liste doit être initialisée
        for (Ship ship : ships) {
//            System.out.println("Boucle sur la liste des bateaux");
//            System.out.println("Bateau : " + ship.getShipType());
//            System.out.println("Positions : " + Arrays.toString(ship.getPositions()));

            if (ship.getPositions()[0] == null) {
//                System.out.println("Bateau non-placé : " + ship.getShipType());
                Label shipLabel = new Label(ship.getShipType());
//                System.out.println(ship.getShipType());
                shipLabel.setOnMouseClicked(event -> {
                    onShipLabelClicked(ship, shipLabel);
                });
                this.shipsVbox.getChildren().add(shipLabel);

            }
        }
//        System.out.println("Tous les bateaux sont placés dans la VBox.");
    }

    @FXML
    private void updateGridPane() {
        Board board = currentPlayer.getMyBoard();

        // Commencer par supprimer le preview courant
        if (previewCoordinates != null && !previewCoordinates.isEmpty()) { // Vérifie si des coordonées de preview existent

            for (Coordinate coordinate : previewCoordinates) {
                if (board.isValidCoordinates(coordinate.getX(),  coordinate.getY())) {
                    cellsButtons[coordinate.getX()][coordinate.getY()].setStyle("-fx-background-color: #87CEEB !important; -fx-border-color: #000; -fx-border-width: 0.5;"); // Remettre la couleur de base pour chaque case de la preview déjà active
                }
            }
            previewCoordinates.clear(); // Vider la liste une fois que les couleurs ont été réintialisées
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

    private void onShipLabelClicked(Ship ship, Label shipLabel) {
        if (this.selectedShipLabel == null) {
            shipLabel.setStyle("-fx-background-color: #CCCCCC; -fx-padding: 5;");
            selectedShipLabel = shipLabel;
        } else {
            selectedShipLabel.setStyle("-fx-background-color: transparent;");
//            System.out.println("Réinitialisation du style du shipLabel sélectionné précédent");
            selectedShipLabel = shipLabel;
            shipLabel.setStyle("-fx-background-color: #CCCCCC; -fx-padding: 5;");
        }
        currentShip = ship;
        currentShipOrientation = Orientation.HORIZONTAL;
    }

    private void onGridCellClicked(int x, int y) {
        currentCell = currentPlayer.getMyBoard().getCell(x, y);
        if (currentShip != null) {
            setShipPlacementPreview(x, y);
        } else if (currentCell.isOccupied()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Type de bateau");
            alert.setContentText("Ce bateau est un " + currentCell.getShip().getShipType());
            alert.showAndWait();
        } else {
            System.out.println("Sélectionnez un bateau d'abord");
        }
    }

    @FXML
    private void okButtonClicked() {
        Ship ship = currentShip;
        System.out.println("Bouton OK cliqué");
        if (previewIsValid) {
            System.out.println("Bateau placé : " + ship.getShipType());
            currentPlayer.getMyBoard().placeShip(ship, currentCell.getX(), currentCell.getY(), currentShipOrientation); // Placer le bateau
            refreshShipsList(); // Rafraichir la liste des bateaux non placés
            currentShip = null; // Déselectionner le bateau
            updateGridPane();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Position invalide");
            alert.setContentText("Le bateau dépasse les limites de la grille");
            alert.showAndWait();
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
