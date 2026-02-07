package util;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MadeUpFunctions {

    // Fonction qui permet d'ajouter du texte dans un Label déjà rempli
    @FXML
    public static void appendLabel(Label label, String text){
        label.setText(label.getText() + text);
    }
}
