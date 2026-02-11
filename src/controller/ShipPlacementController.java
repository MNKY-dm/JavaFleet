package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import model.Board;
import model.Cell;
import model.Ship;
import model.entity.Player;
import type.Coordinate;
import type.GameState;
import type.Orientation;

import javafx.scene.control.Button;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class ShipPlacementController implements Initializable {
    private Player currentPlayer;
    private Ship currentShip;
    private Cell currentCell;
    private Orientation currentShipOrientation;
    private ArrayList<Coordinate> previewCoordinates;
    private boolean previewIsValid;

    @FXML
    private GridPane myBoardGrid;

    @FXML
    private Label CurrentPlayer;

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

        refreshShipsList();
        initializeGridPane(myBoardGrid, currentPlayer.getMyBoard());
    }

    @FXML
    private void initializeGridPane(GridPane gridPane, Board board) {
//        System.out.println("Initializing shipPlacementGridPane");
        CurrentPlayer.setText(GameManager.getInstance().getGame().getCurrentPlayer().getName());
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
        try {

            if (previewCoordinates != null && !previewCoordinates.isEmpty()) { // Vérifie si des coordonnées de preview existent
                for (Coordinate coordinate : previewCoordinates) {
                    if (board.isValidCoordinates(coordinate.getX(),  coordinate.getY())) {
                        if (board.getCell(coordinate.getX(),  coordinate.getY()).isOccupied()) {
                            cellsButtons[coordinate.getX()][coordinate.getY()].setStyle("-fx-background-color: #615C5C !important; -fx-border-color: #000; -fx-border-width: 0.5;"); // Remettre la couleur de base pour chaque case de la preview déjà active
                        } else {
                            cellsButtons[coordinate.getX()][coordinate.getY()].setStyle("-fx-background-color: #87CEEB !important; -fx-border-color: #000; -fx-border-width: 0.5;"); // Remettre la couleur de base pour chaque case de la preview déjà active
                        }
                    }
                }
                previewCoordinates.clear(); // Vider la liste une fois que les couleurs ont été réinitialisées

            }
            boolean isValid = board.canPlaceShip(currentShip, x, y, currentShipOrientation); // Vérifie si on peut placer le bateau sur ces coordonnées
            Coordinate[] positions = board.calculatePositions(currentShip, x, y, currentShipOrientation);

            previewCoordinates = new ArrayList<>(Arrays.asList(positions));

            boolean previewPlacable = true;
            for (Coordinate coordinate : previewCoordinates) {
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
//        System.out.println("refreshShipsList() pour : " + currentPlayer.getName());
//        System.out.println("VBox children avant clear : " + shipsVbox.getChildren().size());
        shipsVbox.getChildren().clear(); // Bien vider la liste des bateaux lorsque la liste doit être initialisée
        for (Ship ship : currentPlayer.getShips()) {
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
//        if (previewCoordinates != null && !previewCoordinates.isEmpty()) { // Vérifie si des coordonées de preview existent
//
//            for (Coordinate coordinate : previewCoordinates) {
//                if (board.isValidCoordinates(coordinate.getX(),  coordinate.getY())) {
//                    cellsButtons[coordinate.getX()][coordinate.getY()].setStyle("-fx-background-color: #87CEEB !important; -fx-border-color: #000; -fx-border-width: 0.5;"); // Remettre la couleur de base pour chaque case de la preview déjà active
//                }
//            }
//            previewCoordinates.clear(); // Vider la liste une fois que les couleurs ont été réintialisées
//        }

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
//        System.out.println("Bouton OK cliqué");
        if (previewIsValid) {
//            System.out.println("Bateau placé : " + ship.getShipType());
            currentPlayer.getMyBoard().placeShip(ship, currentCell.getX(), currentCell.getY(), currentShipOrientation); // Placer le bateau
            currentShip = null; // Déselectionner le bateau
            updateGridPane();
            refreshShipsList(); // Rafraichir la liste des bateaux non placés
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

    @FXML
    private void confirmButtonClicked() {
        if (!currentPlayer.getMyBoard().areAllShipsPlaced()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Placement des bateaux en attente");
            alert.setContentText("Tous les bateaux n'ont pas été placés !");
            alert.showAndWait();
        } else  {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Passer à la suite ?");
            alert.setContentText("Voulez-vous passer au tour suivant ?");

            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.isPresent()) {
                if (answer.get() == ButtonType.OK) {
                    System.out.println("Prochain setup turn.");
                    try {
                        if (GameManager.getInstance().getGame().nextSetupTurn()) {
                            GameManager.getInstance().setWindowHeight(940);
                            GameManager.getInstance().loadScene(GameManager.getInstance().getGame().getGameState());
                        }
                        currentPlayer = controller.GameManager.getInstance().getGame().getCurrentPlayer();
                        refreshShipsList();
                        previewCoordinates.clear();
                        myBoardGrid.getChildren().clear();          // vider la grille
                        // éventuellement réinitialiser cellsButtons
                        // cellsButtons = new Button[10][10];
                        initializeGridPane(myBoardGrid, currentPlayer.getMyBoard());

                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                } else {
                    System.out.println("Prochain setup turn annulé.");
                }
            }
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
}
