package unsw.gloriaromanus.View;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import unsw.gloriaromanus.Controller.*;

public class PlayGloriaRomanus{

  private static GloriaRomanusController controller;
  private Scene scene;
  private Stage stage;
  private String title;

  public PlayGloriaRomanus(Stage stage, List<String> listOfFactionNames) throws IOException {
    this.stage = stage;

    controller = new GloriaRomanusController();
    controller.setFactionList(listOfFactionNames);
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
  
}
