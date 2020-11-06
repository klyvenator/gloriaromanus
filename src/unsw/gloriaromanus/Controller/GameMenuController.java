package unsw.gloriaromanus.Controller;

import java.io.File;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameMenuController {
    private String[] factions = { "Romans", "Gauls", "Thracians", "Celtic Britons", "Greeks", "Egyptians" };
    
    @FXML
    public void initialize() {
        titleImage.setImage(new Image((new File("images/Title.png")).toURI().toString()));
    }

    @FXML
    private ImageView titleImage;

    @FXML
    private ComboBox chooseFaction;

    public void setData(){

        chooseFaction.getItems().clear();
        
        chooseFaction.getItems().addAll(
            "Romans", 
            "Gauls", 
            "Thracians", 
            "Celtic Britons", 
            "Greeks", 
            "Egyptians" 
        );
    }
 
}
