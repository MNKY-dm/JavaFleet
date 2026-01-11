import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/game.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

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