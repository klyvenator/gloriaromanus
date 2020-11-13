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
    startScreen.getController().setNewGame(gameMenu);

    // TO DO comment this to run the game
    startScreen.start();
    // TO DO UNcomment this to run game
    //startGame(stage);
    

  }
  public void startGame(Stage stage) throws IOException{
    FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
    Parent root = loader.load();
    controller = loader.getController();
    Scene scene = new Scene(root);

    // set up the stage
    stage.setTitle("Gloria Romanus");
    stage.setWidth(800);
    stage.setHeight(700);
    stage.setScene(scene);
    stage.show();
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