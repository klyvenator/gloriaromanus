package unsw.gloriaromanus.View;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import unsw.gloriaromanus.Controller.*;
import unsw.gloriaromanus.Model.*;

public class PlayGloriaRomanus{

  private static GloriaRomanusController controller;
  private Scene scene;
  private Stage stage;
  private String title;

  public PlayGloriaRomanus(Stage stage, List<String> listOfFactionNames, List<Faction> facList, StartScreen startScreen, int year) throws IOException {
    this.stage = stage;

    controller = new GloriaRomanusController();
    controller.setFactionList(listOfFactionNames);
    controller.setLoadGameFactionList(facList);
    controller.setStartScreen(startScreen);
    controller.setYear(year);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
    loader.setController(controller);

    Parent root = loader.load();
    //System.out.print("hello world" + listOfFactionNames);
    scene = new Scene(root);
  }

  public void startGame() throws IOException{
    String path = "sound/haloTheme.mp3";
    Media media = new Media(new File(path).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media); 
    mediaPlayer.setOnEndOfMedia(new Runnable() {
        @Override
        public void run() {
            mediaPlayer.seek(Duration.ZERO);
        }
    });
    mediaPlayer.play();
    // set up the stage
    stage.setTitle("Gloria Romanus");
    stage.setWidth(800);
    stage.setHeight(700);
    stage.setScene(scene);
    stage.show();
  }

  public void stop() {
    controller.terminate();
  }

  public GloriaRomanusController getController(){
    return controller;
  }
  
}
