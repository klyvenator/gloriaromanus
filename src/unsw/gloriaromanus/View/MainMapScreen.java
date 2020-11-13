package unsw.gloriaromanus.View;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.gloriaromanus.Controller.GloriaRomanusController;

public class MainMapScreen {
    private Stage stage;
	private String title;
	private Scene scene;
    private static GloriaRomanusController controller;
    
    public MainMapScreen(Stage stage) throws IOException {
		this.stage = stage;
		this.title = "Gloria Romanus";
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        // controller = loader.getController();
		controller = new GloriaRomanusController();
		loader.setController(controller);

		if (controller == null) {
			System.out.println("*** Controller is null ***");
		}
		
        Parent root = loader.load();
        scene = new Scene(root);
	}
	
	public void start() {
        stage.setTitle(title);
        stage.setWidth(800);
        stage.setHeight(700);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * @return the controller
	 */
	public GloriaRomanusController getController() {
		return controller;
	}
}