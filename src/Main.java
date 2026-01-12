import controller.GameManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameManager.getInstance().initialize(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}