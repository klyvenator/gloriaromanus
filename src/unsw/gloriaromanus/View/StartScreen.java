package unsw.gloriaromanus.View;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.gloriaromanus.Controller.*;

public class StartScreen {

    private Stage stage;
    private String title;
    private StartController controller;
    private Scene scene;
    
    public StartScreen(Stage stage) throws IOException {
        this.stage = stage;
        this.title = "Start Menu";
        controller = new StartController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Start.fxml"));
        loader.setController(controller);

        Parent root = loader.load();
        scene = new Scene(root);
    }
    
    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    public Stage getStage() {
        return stage;
    }
    public StartController getController() {
        return controller;
    }
}
