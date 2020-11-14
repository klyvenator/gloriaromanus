package unsw.gloriaromanus.View;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import unsw.gloriaromanus.Controller.*;
import unsw.gloriaromanus.Model.*;

public class PlayGloriaRomanus{

  private static GloriaRomanusController controller;
  private Scene scene;
  private Stage stage;

  public PlayGloriaRomanus(Stage stage, List<String> listOfFactionNames, List<Faction> facList) throws IOException {
    this.stage = stage;

    controller = new GloriaRomanusController();
    controller.setFactionList(listOfFactionNames);
    controller.setFactionLoadGameFactionList(facList);
    
    FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
    loader.setController(controller);

    Parent root = loader.load();
    //System.out.print("hello world" + listOfFactionNames);
    scene = new Scene(root);
  }
  public void startGame() throws IOException{
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
  /**
   * Opens and runs application.
   *
   * @param args arguments passed to this application
   */
  public static void main(String[] args) {

    Application.launch(args);
  }
}
