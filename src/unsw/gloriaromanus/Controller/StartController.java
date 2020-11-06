package unsw.gloriaromanus.Controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import unsw.gloriaromanus.View.*;


public class StartController {

    @FXML
    public void initialize() {
        titleImage.setImage(new Image((new File("images/Title.png")).toURI().toString()));
        romanImage.setImage(new Image((new File("images/ExampleLegion.png")).toURI().toString()));
    }
    @FXML
    private ImageView titleImage;

    @FXML
    private ImageView romanImage;

    @FXML
    private Button newGame;
    
    @FXML
    private Button loadGame;

    private GameMenu gameMenu;

    @FXML
	private void handleNewGame(ActionEvent event) {
        gameMenu.start();
    }
    public void setNewGame(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
    }

    @FXML
	private void handleLoadGame(ActionEvent event) {
        return;
	}
}
