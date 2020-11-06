package unsw.gloriaromanus.Controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StartController {
    
    public void initialize() {
        titleImage.setImage(new Image((new File("images/Title.png")).toURI().toString()));
        romanImage.setImage(new Image((new File("images/ExampleLegion.png")).toURI().toString()));
    }
    @FXML
    ImageView titleImage;

    @FXML
    ImageView romanImage;

    @FXML
    private Button newGame;
    
    @FXML
    private Button loadGame;

    @FXML
	private void handleNewGame(ActionEvent event) {
        return;
    }
    @FXML
	private void handleLoadGame(ActionEvent event) {
        return;
	}
}
