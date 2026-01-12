import controller.GameManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GameManager gameManager = GameManager.getInstance();
        gameManager.initialize(primaryStage);

        primaryStage.setWidth(1000);
        primaryStage.setHeight(900);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(900);

        primaryStage.setTitle("JavaFleet");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}