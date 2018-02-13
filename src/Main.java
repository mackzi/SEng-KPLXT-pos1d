

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
        AnchorPane page = loader.load();
        primaryStage.setTitle("Particle Swarm Optimization - 1D");
        primaryStage.setScene(new Scene(page));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
