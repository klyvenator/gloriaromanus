package unsw.gloriaromanus.View;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import unsw.gloriaromanus.Controller.BattleController;
import unsw.gloriaromanus.Model.Army;
import unsw.gloriaromanus.Model.Faction;

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

    public void start(Army human, Army enemy, String humanProvince, String targetProvince, Faction currFac, Faction enemyFac) {
        musicUtils.stopSound();
        musicUtils.playSound("battleMusic.mp3");
        controller.setHumanArmy(human);
        controller.setEnemyArmy(enemy);
        controller.reset();
        controller.setProvinceNames(humanProvince, targetProvince);
        controller.setFactionNames(currFac.getFactionName(), enemyFac.getFactionName());
        controller.setArmyBindings();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
}