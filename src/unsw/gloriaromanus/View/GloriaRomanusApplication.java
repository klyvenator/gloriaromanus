package unsw.gloriaromanus.View;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import unsw.gloriaromanus.Controller.*;

public class GloriaRomanusApplication extends Application {

  private static GloriaRomanusController controller;
  private static MediaPlayer mediaPlayer;

  @Override
  public void start(Stage stage) throws IOException {
    // set up the scene
    StartScreen startScreen = new StartScreen(stage);
    GameMenu gameMenu = new GameMenu(stage,startScreen);
    LoadGameFile loadFile = new LoadGameFile(stage, startScreen);
    
    startScreen.getController().setNewGame(gameMenu);
    startScreen.getController().setLoadMenu(loadFile);
    String path = "sound/blueBird.mp3";
    Media media = new Media(new File(path).toURI().toString());
    mediaPlayer = new MediaPlayer(media); 
    mediaPlayer.setOnEndOfMedia(new Runnable() {
        @Override
        public void run() {
            mediaPlayer.seek(Duration.ZERO);
        }
    });
    mediaPlayer.setAutoPlay(true);
    startScreen.start();
    
  }
  /**
   * Stops and releases all resources used in application.
   */
  @Override
  public void stop() {
    controller.terminate();
  }

  /**
   * Opens and runs application.
   *
   * @param args arguments passed to this application
   */
  public static void main(String[] args) {

    Application.launch(args);
  }
}