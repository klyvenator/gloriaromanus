package unsw.gloriaromanus.Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import unsw.gloriaromanus.View.*;

public class GameMenuController {

    public PlayGloriaRomanus play;

    private StartScreen startScreen;
    private String[] factions = { "Rome", "Gaul", "Thracian", "Celtic Briton", "Greek", "Egyptian" };
    private List<String> players;
    public List<String> finalPlayers;
    private int numPlayers = 0;
    public Stage stage;
    Alert alert = new Alert(AlertType.WARNING, "Please Choose Different Factions", ButtonType.OK);

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

    public void setData() {
        removeContent();
        chooseFaction1.getItems().addAll(factions);
        chooseFaction2.getItems().addAll(factions);
        chooseFaction3.getItems().addAll(factions);
        chooseFaction4.getItems().addAll(factions);
        disableAllButtons();
        players = new ArrayList<String>();
    }

    @FXML
    private void handle2players(ActionEvent event) {
        if (numPlayers != 2) {
            setData();
            chooseFaction1.setVisible(true);
            chooseFaction2.setVisible(true);
            chooseFaction3.setVisible(false);
            chooseFaction4.setVisible(false);
            numPlayers = 2;
        }
    }

    @FXML
    private void handle3players(ActionEvent event) {
        /*if (numPlayers != 3) {
            setData();
            chooseFaction1.setVisible(true);
            chooseFaction2.setVisible(true);
            chooseFaction3.setVisible(true);
            chooseFaction4.setVisible(false);
            numPlayers = 3;
        }*/
        Alert alert = new Alert(AlertType.WARNING, "To be implemented!", ButtonType.OK);
        alert.showAndWait(); 
    }

    @FXML
    private void handle4players(ActionEvent event) {
        /*if (numPlayers != 4) {
            setData();
            chooseFaction1.setVisible(true);
            chooseFaction2.setVisible(true);
            chooseFaction3.setVisible(true);
            chooseFaction4.setVisible(true);
            numPlayers = 4;
        }*/

        Alert alert = new Alert(AlertType.WARNING, "Please select an enemy faction", ButtonType.OK);
        alert.showAndWait(); 
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        startScreen.start();
    }

    @FXML
    private void handleNextButton(ActionEvent event) throws IOException {
        if( !chooseFaction1.getSelectionModel().isEmpty() ){ players.add(chooseFaction1.getValue()); }
        if( !chooseFaction2.getSelectionModel().isEmpty() ){ players.add(chooseFaction2.getValue()); }
        if( !chooseFaction3.getSelectionModel().isEmpty() ){ players.add(chooseFaction3.getValue()); }
        if( !chooseFaction4.getSelectionModel().isEmpty() ){ players.add(chooseFaction4.getValue()); }

        if(players.isEmpty()){
            System.out.println("empty list please add factions");
        }else{
            for(String s : players){
                if(Collections.frequency(players, s) != 1){
                    alert.showAndWait();
                    setData();
                }
            }
            finalPlayers = new ArrayList<String>();
            finalPlayers.addAll(players);
            // start Game
            //System.out.println(finalPlayers);
            play = new PlayGloriaRomanus(stage,finalPlayers, null, startScreen, 0);
            play.startGame();
        }
    }
    public void setStartScreen(StartScreen startScreen){
        this.startScreen = startScreen;
    }
    public List<String> getPlayers(){
        return finalPlayers;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setGameApplication(PlayGloriaRomanus play){
        this.play = play;
    }
    private void disableAllButtons(){
        chooseFaction1.setVisible(false);
        chooseFaction2.setVisible(false);
        chooseFaction3.setVisible(false);
        chooseFaction4.setVisible(false);
    }
    private void removeContent(){
        chooseFaction1.getItems().clear();
        chooseFaction2.getItems().clear();
        chooseFaction3.getItems().clear();
        chooseFaction4.getItems().clear();
    }
}
