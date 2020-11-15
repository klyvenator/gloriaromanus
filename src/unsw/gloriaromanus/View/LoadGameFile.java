package unsw.gloriaromanus.View;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.gloriaromanus.Controller.*;

public class LoadGameFile {
    
    private Stage stage;
    private String title;
    private LoadFileController controller;
    private Scene scene;

    public LoadGameFile(Stage stage, StartScreen startScreen) throws IOException {
        this.stage = stage;
        this.title = "Start Menu";
        controller = new LoadFileController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadFile.fxml"));
        loader.setController(controller);

        Parent root = loader.load();
        scene = new Scene(root);
        controller.setStartScreen(startScreen);
        controller.setStage(stage);
    }

    public void start() {
        controller.setData();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    public Stage getStage() {
        return stage;
    }
    public LoadFileController getController() {
        return controller;
    }
}
