package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GameOverController implements Initializable  {
    private String winner;

    @FXML
    public Label winnerLabel;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing GameOverController");
        System.out.println("Winner is: " + winner);
        setWinner(GameManager.getInstance().getGame().getWinner());
        winnerLabel.setText(winner);
    }

    @FXML
    public void startAnotherGame() {
        GameManager.getInstance().startAnotherGame();
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
