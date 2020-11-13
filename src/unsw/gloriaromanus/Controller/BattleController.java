package unsw.gloriaromanus.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import unsw.gloriaromanus.Model.Army;
import unsw.gloriaromanus.Model.BattleResolver;
import unsw.gloriaromanus.View.MainMapScreen;

public class BattleController {

    @FXML
    private Button testButton;

    private MainMapScreen mainMap;

    @FXML
    private Label human;

    @FXML
    private Label enemy;

    private Army attackerArmy; // humanArmy
    private Army defenderArmy; // enemyArmy

    @FXML
    private TextArea battleMessages;
    
    public BattleController(MainMapScreen mainMap) {
        this.mainMap = mainMap;
        this.attackerArmy = null;
        this.defenderArmy = null;
    }

    @FXML
    public void initialize() {
        testButton.setText("Hello there!\nGo back");
        human.setText("human");
        enemy.setText("enemy");
        startBattle();
    }

    @FXML
    public void goBack(ActionEvent event) {
        mainMap.start();
    }

    public void setHumanArmy(Army human) {
        this.attackerArmy = human;
    }

    public void setEnemyArmy(Army enemy) {
        this.defenderArmy = enemy;
    }

    private void startBattle() {
        BattleResolver resolver = new BattleResolver(attackerArmy, defenderArmy);
        battleMessages.appendText("Starting battle!\n");
    }
}