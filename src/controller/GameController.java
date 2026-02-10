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

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private Player currentPlayer;
    private Player opponentPlayer;
    private Cell currentCell;
    private boolean targetIsValid;

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
    private Button[][] oppsCellsButtons = new Button[10][10];

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

        setCurrentPlayer(GameManager.getInstance().getGame().getCurrentPlayer());
        setOpponentPlayer(GameManager.getInstance().getGame().getPlayers()[1]);

        System.out.println("Current Player: " + currentPlayer);
        System.out.println("Opps Player: " + opponentPlayer);
        System.out.println("myBoard children = " + myBoard.getChildren().size());

        updateCurrentPlayer();

        initializeMyGridPane(myBoard, currentPlayer.getMyBoard());
        initializeOppsGridPane(opponentBoard, opponentPlayer.getMyBoard());
    }

    @FXML
    private void initializeMyGridPane(GridPane gridPane, Board board) {
        System.out.println("Initializing MyGridPane");
        System.out.println("My Board: " + board);
        myNameLabel.setText(currentPlayer.getName());
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
                    onMyGridCellClicked(cell.getX(), cell.getY());
                });
                cellsButtons[x][y] = cellButton;
                gridPane.add(cellButton, x, y); // Ajoute le bouton à la cellule
            }
        }
        System.out.println("myBoard children = " + myBoard.getChildren().size());
    }

    @FXML
    private void initializeOppsGridPane(GridPane gridPane, Board board) {
        System.out.println("Initializing OppsGridPane");
        System.out.println("Opp's board : " + board);
        opponentNameLabel.setText(opponentPlayer.getName());
        for (int y = 0 ; y < board.getHeight() ; y++) {
            for (int x = 0 ; x < board.getWidth() ; x++) {
                Cell cell = board.getCell(x, y);

                Button cellButton = new Button(); // Crée un bouton
                cellButton.setPrefSize(50, 50);

                cellButton.setStyle("-fx-background-color: #87CEEB !important; " + "-fx-border-color: #000 !important; " + "-fx-border-width: 0.5 !important;");


                cellButton.setOnAction(event -> {
//                    System.out.println("Cellule cliquée : (" + cell.getX() + ", " + cell.getY() + ")");
                    onOppsGridCellClicked(cell.getX(), cell.getY());
                });
                oppsCellsButtons[x][y] = cellButton;
                gridPane.add(cellButton, x, y); // Ajoute le bouton à la cellule
            }
        }
        System.out.println("oppsBoard children = " + opponentBoard.getChildren().size());
    }

    @FXML
    private void updateMyGridPane() {
        System.out.println("Updating MyGridPane");

        Board board = currentPlayer.getMyBoard();

        System.out.println("Current player = " + currentPlayer);
        System.out.println("Current player's board = " + board);

        myNameLabel.setText(currentPlayer.getName());

        for (int y = 0 ; y < board.getHeight() ; y++) {
            for (int x = 0 ; x < board.getWidth() ; x++) {
                Cell cell = board.getCell(x, y);

                Button boatButton = cellsButtons[x][y]; // Utilise le bouton existant
                if (cell.isHit()) { // Si un bateau est placé
                    boatButton.setStyle("-fx-background-color: #f03d3d !important; -fx-border-color: #000 !important; -fx-border-width: 0.5 !important;"); // Le colore en gris/marron
                } else if (cell.hasBeenAttacked()) {
                    boatButton.setStyle("-fx-background-color: #f0d23d !important; -fx-border-color: #000 !important; -fx-border-width: 0.5 !important;"); // Le colore en gris/marron
                } else if (cell.isOccupied()) {
                    boatButton.setStyle("-fx-background-color: #615C5C !important; -fx-border-color: #000 !important; -fx-border-width: 0.5 !important;"); // Le colore en gris/marron
                } else {
                    boatButton.setStyle("-fx-background-color: #87CEEB !important; -fx-border-color: #000 !important; -fx-border-width: 0.5 !important;"); // Le colore en gris/marron
                }
            }
        }
    }

    @FXML
    private void updateOppsGridPane() {
        System.out.println("Updating OppsGridPane");

        Board board = opponentPlayer.getMyBoard();
        System.out.println("Opp's board : " + board);
        System.out.println("Opp's board 2 : " + currentPlayer.getOpponentBoard());

        System.out.println("Opps player = " + opponentPlayer);
        opponentNameLabel.setText(opponentPlayer.getName());

        for (int y = 0 ; y < board.getHeight() ; y++) {
            for (int x = 0 ; x < board.getWidth() ; x++) {
                Cell cell = board.getCell(x, y);

                Button boatButton = oppsCellsButtons[x][y]; // Utilise le bouton existant
                if (cell.isHit()) { // Si un bateau est placé
                    boatButton.setStyle("-fx-background-color: #f03d3d !important; -fx-border-color: #000 !important; -fx-border-width: 0.5 !important;"); // Le colore en gris/marron
                } else if (cell.hasBeenAttacked()) {
                    boatButton.setStyle("-fx-background-color: #f0d23d !important; -fx-border-color: #000 !important; -fx-border-width: 0.5 !important;"); // Le colore en gris/marron
                } else {
                    boatButton.setStyle("-fx-background-color: #87CEEB !important; -fx-border-color: #000 !important; -fx-border-width: 0.5 !important;"); // Le colore en gris/marron
                }
            }
        }
    }

    private void setCellPreview(Cell cell) {
        if (currentCell != null) {
            if (currentCell.isHit()) {
                oppsCellsButtons[currentCell.getX()][currentCell.getY()].setStyle("-fx-background-color: #f03d3d !important; -fx-border-color: #000; -fx-border-width: 0.5;"); // Remettre la couleur de base pour chaque case de la preview déjà active
                System.out.println("Oooooops");
            } else if (currentCell.hasBeenAttacked()) {
                oppsCellsButtons[currentCell.getX()][currentCell.getY()].setStyle("-fx-background-color: #f0d23d !important; -fx-border-color: #000; -fx-border-width: 0.5;"); // Remettre la couleur de base pour chaque case de la preview déjà active
            } else {
                oppsCellsButtons[currentCell.getX()][currentCell.getY()].setStyle("-fx-background-color: #87CEEB !important; -fx-border-color: #000; -fx-border-width: 0.5;"); // Remettre la couleur de base pour chaque case de la preview déjà active
            }
        }
        currentCell = cell;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Le joueur a sorti sa longue vue.");
        if (currentCell.isHit()) {
            oppsCellsButtons[currentCell.getX()][currentCell.getY()].setStyle("-fx-background-color: #420000 !important; -fx-border-color: #000; -fx-border-width: 0.5;"); // Remettre la couleur de base pour chaque case de la preview déjà active
            alert.setContentText("Vous avez déjà touché un bateau ici.");
            alert.showAndWait();
        } else if (currentCell.hasBeenAttacked()) {
            oppsCellsButtons[currentCell.getX()][currentCell.getY()].setStyle("-fx-background-color: #615900 !important; -fx-border-color: #000; -fx-border-width: 0.5;"); // Remettre la couleur de base pour chaque case de la preview déjà active
            alert.setContentText("Vous avez déjà tiré dans l'eau ici.");
            alert.showAndWait();
        } else {
            oppsCellsButtons[currentCell.getX()][currentCell.getY()].setStyle("-fx-background-color: #2980b9 !important; -fx-border-color: #000; -fx-border-width: 0.5;"); // Remettre la couleur de base pour chaque case de la preview déjà active
//            alert.setContentText("Le brouillard vous empêche de voir ici. Voulez vous tirer ici ? Si oui, cliquez sur TIRER.");
        }
    }

    private void updateCurrentPlayer() {
        Game game = GameManager.getInstance().getGame();
        Player gameCurrent = game.getCurrentPlayer();

        // currentPlayer = joueur dont c'est le tour
        this.currentPlayer = gameCurrent;

        // opponentPlayer = l'autre
        if (gameCurrent == game.getPlayers()[0]) {
            this.opponentPlayer = game.getPlayers()[1];
        } else {
            this.opponentPlayer = game.getPlayers()[0];
        }
    }

    private void onMyGridCellClicked(int x, int y) {
        updateOppsGridPane();
        currentCell = currentPlayer.getMyBoard().getCell(x, y);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        updateMyGridPane();
        System.out.println("Clicked (" + x + "," + y + ")");
        System.out.println("isOccupied = " + currentCell.isOccupied());
        System.out.println("Style du bouton = " + cellsButtons[x][y].getStyle());
        System.out.println("Joueur courant = " + currentPlayer.getName());
//        System.out.println(currentCell.toString());
        if (currentCell.isOccupied()) {
            System.out.println("Type de bateau : " + currentCell.getShip().getShipType());
            alert.setTitle("Type de bateau");
            if (currentCell.getShip().isSunk()) {
                alert.setContentText("Ce bateau était un " + currentCell.getShip().getShipType() + "\nIl est probablement mieux là où il est... R.I.P.");
            } else if (currentCell.isHit()) {
                alert.setContentText("Ce bateau est un " + currentCell.getShip().getShipType() + "\nCette partie du bateau n'est pas très en forme..." + "Il lui reste " + currentCell.getShip().getHealth() + " points de vie");
            } else {
                 alert.setContentText("Ce bateau est un " + currentCell.getShip().getShipType() + "\nTout va bien ici.");
            }
        }
        else {
            alert.setTitle("Le joueur a sorti sa longue vue.");
            if (currentCell.hasBeenAttacked()) {
                alert.setContentText("L'adversaire a l'air d'en avoir après l'eau : une explosion a eu lieu ici.");
            } else {
                alert.setContentText("L'eau est calme.");
            }
        }
        alert.showAndWait();
    }

    private void onOppsGridCellClicked(int x, int y) {
        setCellPreview(opponentPlayer.getMyBoard().getCell(x, y));
    }

    @FXML
    private void okButtonClicked() {
        if (currentCell.hasBeenAttacked()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acharnement.");
            alert.setContentText("Vous vous acharnez sur une case qui a déjà été attaquée. Passez votre chemin.");
            alert.showAndWait();
        } else {
            try {
                if (GameManager.getInstance().getGame().attack(currentCell.getX(), currentCell.getY()) != null) {
                    updateCurrentPlayer();
                    updateMyGridPane();
                    updateOppsGridPane();
                }
            } catch (Exception e) {
                System.err.println(" ! ! ! Erreur dans nextTurn ! ! ! : " + e.getMessage());
            }
        }
        currentCell = null;
    }

    @FXML
    private void cancelButtonClicked() {
        currentCell = null;
        updateOppsGridPane();
        updateMyGridPane();
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public void setOpponentPlayer(Player player) {
        this.opponentPlayer = player;
    }
}
