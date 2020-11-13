package unsw.gloriaromanus.View;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import unsw.gloriaromanus.Controller.*;

public class PlayGloriaRomanus extends Application {

  private static GloriaRomanusController controller;

  @Override
  public void start(Stage stage) throws IOException {
    /*
    FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
    Parent root = loader.load();
    controller = loader.getController();
    Scene scene = new Scene(root);
    */

    MainMapScreen mapScreen = new MainMapScreen(stage);
    controller = mapScreen.getController();
    
    BattleScreen battleScreen = new BattleScreen(stage, mapScreen);
    
    controller.setBattleScreen(battleScreen);


    mapScreen.start();
    /*
    // set up the stage
    stage.setTitle("Gloria Romanus");
    stage.setWidth(800);
    stage.setHeight(700);
    stage.setScene(scene);
    stage.show();
    */
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
