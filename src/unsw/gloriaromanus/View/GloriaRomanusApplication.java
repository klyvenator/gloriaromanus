package unsw.gloriaromanus.View;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import unsw.gloriaromanus.Controller.*;

public class GloriaRomanusApplication extends Application {

  private static GloriaRomanusController controller;

  @Override
  public void start(Stage stage) throws IOException {
    // set up the scene
    StartScreen startScreen = new StartScreen(stage);
    GameMenu gameMenu = new GameMenu(stage,startScreen);
    LoadGameFile loadFile = new LoadGameFile(stage, startScreen);
    
    startScreen.getController().setNewGame(gameMenu);
    startScreen.getController().setLoadMenu(loadFile);
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