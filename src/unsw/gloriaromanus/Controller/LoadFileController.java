package unsw.gloriaromanus.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import unsw.gloriaromanus.View.*;
import unsw.gloriaromanus.Model.*;

public class LoadFileController {

    private StartScreen startScreen;
    private File folder = new File("src/unsw/gloriaromanus/SaveFiles");
    private List<String> fileNames;
    private String fileToLoad;

    @FXML
    private ImageView titleImage;

    @FXML
    private ImageView romanImage;

    @FXML
    private Button nextButton;
    
    @FXML
    private Button backButton;


    @FXML
    private ComboBox<String> chooseFile = new ComboBox<String>();

    @FXML
    public void initialize() {
        titleImage.setImage(new Image((new File("images/Title.png")).toURI().toString()));
        romanImage.setImage(new Image((new File("images/ExampleLegion.png")).toURI().toString()));
        setData();
    }

    @FXML
	private void handleNextButton(ActionEvent event) {
        fileToLoad = chooseFile.getValue();
        LoadFile newLoadFile = new LoadFile(fileToLoad);
        List<Faction> facList = newLoadFile.getFactionList();
    }

    @FXML
	private void handleBackButton(ActionEvent event) {
        startScreen.start();
    }

    @FXML
    private void handleFileSelection(ActionEvent event) {
        fileToLoad = chooseFile.getValue();
    }

    public void setStartScreen(StartScreen startScreen){
        this.startScreen = startScreen;
    }

    private void setData(){
        fileNames = new ArrayList<String>();
        for (File fileEntry : folder.listFiles()) {
            fileNames.add(fileEntry.getName());
        }
        chooseFile.getItems().addAll(fileNames);
    }
}
