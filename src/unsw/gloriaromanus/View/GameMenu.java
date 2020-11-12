package unsw.gloriaromanus.View;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

import unsw.gloriaromanus.Controller.*;

public class GameMenu{
    private Stage stage;
	private String title;
	private GameMenuController controller;
	private Scene scene;
	

	public GameMenu(Stage stage, StartScreen startScreen) throws IOException {
		this.stage = stage;
		this.title = "Choose Game";
		
		controller = new GameMenuController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GameMenu.fxml"));
        loader.setController(controller);

        Parent root = loader.load();
        scene = new Scene(root);
		controller.setData();
		controller.setStartScreen(startScreen);
	}
	
	public void start() {
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * @return the controller
	 */
	public GameMenuController getController() {
		return controller;
	}

}
