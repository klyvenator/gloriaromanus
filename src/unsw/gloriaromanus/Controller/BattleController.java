package unsw.gloriaromanus.Controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
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
    
    @FXML
    private TextField input;

    private DoubleProperty conn;
    @FXML
    private Label output;

    @FXML
    private Circle humanUnit;

    @FXML
    private Button startBattleButton;

    public BattleController(MainMapScreen mainMap) {
        this.mainMap = mainMap;
        this.attackerArmy = null;
        this.defenderArmy = null;
        conn = new SimpleDoubleProperty(30);
    }

    @FXML
    public void initialize() {
        testButton.setText("Hello there!\nGo back");
        human.setText("human");
        enemy.setText("enemy");
        output.setText("Enter text");
        // input.textProperty().bindBidirectional(output.textProperty());
        conn.bindBidirectional(humanUnit.radiusProperty());
    }

    public void reset() {
        battleMessages.clear();
    }

    @FXML
    public void goBack(ActionEvent event) {
        mainMap.start();
    }

    @FXML
    public void handleInput(ActionEvent event) {
        try {
            conn.setValue(Double.parseDouble(input.getText()));
        } catch (Exception e) {
            conn.setValue(30);
        }
    }

    public void setHumanArmy(Army human) {
        this.attackerArmy = human;
    }

    public void setEnemyArmy(Army enemy) {
        this.defenderArmy = enemy;
    }

    @FXML
    private void startBattle(ActionEvent event) {
        battleMessages.appendText("Starting battle!\n");
        BattleResolver resolver = new BattleResolver(attackerArmy, defenderArmy, battleMessages);
        resolver.startBattle();
        battleMessages.appendText("Battle finished!\n");
    }

}