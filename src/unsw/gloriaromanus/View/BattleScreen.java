package unsw.gloriaromanus.View;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.gloriaromanus.Controller.BattleController;
import unsw.gloriaromanus.Model.Army;

public class BattleScreen {

    private Stage stage;
    private Scene scene;
    private String title;
    private BattleController controller;

    public BattleScreen(Stage stage, PlayGloriaRomanus mainMap) throws IOException {
        this.stage = stage;
        this.title = "*** Battle Mode ***";
        controller = new BattleController(mainMap);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("battle.fxml"));
        loader.setController(controller);

        Parent root = loader.load();
        scene = new Scene(root);
    }

    public void start(Army human, Army enemy, String humanProvince, String targetProvince) {
        controller.setHumanArmy(human);
        controller.setEnemyArmy(enemy);
        controller.reset();
        controller.setProvinceNames(humanProvince, targetProvince);
        controller.setArmyBindings();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
}