package unsw.gloriaromanus.Controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import unsw.gloriaromanus.View.*;


public class GameMenuController {
    private StartScreen startScreen;
    private String[] factions = { "Romans", "Gauls", "Thracians", "Celtic Britons", "Greeks", "Egyptians" };
    private int numPlayers = 0;
    private String p1,p2,p3,p4;

    @FXML
    private ImageView titleImage;
    @FXML
    private ImageView romanImage;

    @FXML
    private Button button4P;
    @FXML
    private Button button3P;
    @FXML
    private Button button2P;
    @FXML
    private Button backButton;
    @FXML
    private Button nextButton;
    @FXML
    private ComboBox<String> chooseFaction1 = new ComboBox<String>();
    @FXML
    private ComboBox<String> chooseFaction2 = new ComboBox<String>();
    @FXML
    private ComboBox<String> chooseFaction3 = new ComboBox<String>();
    @FXML
    private ComboBox<String> chooseFaction4 = new ComboBox<String>();

    @FXML
    public void initialize() {
        titleImage.setImage(new Image((new File("images/Title.png")).toURI().toString()));
        romanImage.setImage(new Image((new File("images/ExampleLegion.png")).toURI().toString()));
    }

    public void setData(){
        chooseFaction1.getItems().addAll(factions);
        chooseFaction2.getItems().addAll(factions);
        chooseFaction3.getItems().addAll(factions);
        chooseFaction4.getItems().addAll(factions);
        disableAllButtons();
    }

    @FXML
	private void handleP1faction(ActionEvent event) {
        p1 = chooseFaction1.getValue();
    }
    @FXML
	private void handleP2faction(ActionEvent event) {
        p2 = chooseFaction2.getValue();
    }
    @FXML
	private void handleP3faction(ActionEvent event) {
        p3 = chooseFaction3.getValue();
    }
    @FXML
	private void handleP4faction(ActionEvent event) {
        p4 = chooseFaction4.getValue();
    }
    
    @FXML
	private void handle2players(ActionEvent event) {
        if(numPlayers != 2){
            chooseFaction1.setVisible(true);
            chooseFaction2.setVisible(true);
            chooseFaction3.setVisible(false);
            chooseFaction4.setVisible(false);
            numPlayers = 2;
        }
    }
    @FXML
	private void handle3players(ActionEvent event) {
        if(numPlayers != 3){
            chooseFaction1.setVisible(true);
            chooseFaction2.setVisible(true);
            chooseFaction3.setVisible(true);
            chooseFaction4.setVisible(false);
            numPlayers = 3;
        }
    }
    @FXML
	private void handle4players(ActionEvent event) {
        if(numPlayers != 4){
            chooseFaction1.setVisible(true);
            chooseFaction2.setVisible(true);
            chooseFaction3.setVisible(true);
            chooseFaction4.setVisible(true);
            numPlayers = 4;
        }
    }
    @FXML
    private void handleBackButton(ActionEvent event){
        startScreen.start();
    }
    @FXML
    private void handleNextButton(ActionEvent event){
        
    }
    public void setStartScreen(StartScreen startScreen){
        this.startScreen = startScreen;
    }

    private void disableAllButtons(){
        chooseFaction1.setVisible(false);
        chooseFaction2.setVisible(false);
        chooseFaction3.setVisible(false);
        chooseFaction4.setVisible(false);
    }
}
