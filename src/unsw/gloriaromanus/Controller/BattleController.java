package unsw.gloriaromanus.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import unsw.gloriaromanus.Model.Army;
import unsw.gloriaromanus.Model.BattleResolver;
import unsw.gloriaromanus.Model.Enums.BattleStatus;
import unsw.gloriaromanus.View.MainMapScreen;
import unsw.gloriaromanus.View.PlayGloriaRomanus;

public class BattleController {

    @FXML
    private Button testButton;

    private PlayGloriaRomanus mainMap;

    @FXML
    private Label human;

    @FXML
    private Label enemy;

    @FXML
    private Label humanFaction;

    @FXML
    private Label enemyFaction;

    private Army attackerArmy; // humanArmy
    private Army defenderArmy; // enemyArmy

    @FXML
    private TextArea battleMessages;

    private StringProperty conn;

    @FXML
    private Circle humanUnit;

    @FXML
    private Circle enemyUnit;

    @FXML
    private Rectangle humanHealth;

    @FXML
    private Rectangle enemyHealth;

    @FXML
    private Button startBattleButton;

    private IntegerProperty unitAhealth;
    private IntegerProperty unitBhealth;

    private ChangeListener<Number> armyAsize;
    private ChangeListener<Number> armyBsize;
    private ChangeListener<Number> unitAnumTroops;
    private ChangeListener<Number> unitBnumTroops;

    @FXML
    private Label armyHuman;

    @FXML
    private Label armyEnemy;

    private Timeline humanAttacks;
    private Timeline enemyAttacks;
    
    public BattleController(PlayGloriaRomanus mainMap) {
        this.mainMap = mainMap;
        this.attackerArmy = null;
        this.defenderArmy = null;
        conn = new SimpleStringProperty();

        humanAttacks = initializeTimeline();
        enemyAttacks = initializeTimeline();
    }

    @FXML
    public void initialize() {
        testButton.setText("Go back");
        
        human.setText("human");
        enemy.setText("enemy");
        
        humanFaction.setText("invader");
        enemyFaction.setText("defender");
    }

    private Timeline initializeTimeline() {
        Timeline ref = new Timeline();
        ref.setCycleCount(1);
        return ref;
    }
    public void reset() {
        battleMessages.clear();
        unitAhealth = new SimpleIntegerProperty();
        unitBhealth = new SimpleIntegerProperty();
        humanHealth.setWidth(attackerArmy.numAvailableUnits() * 15);
        enemyHealth.setWidth(defenderArmy.numAvailableUnits() * 15);
        armyAsize = null;
        armyBsize = null;
        

    }

    public void setProvinceNames(String humanProvince, String targetProvince) {
        human.setText(humanProvince);
        enemy.setText(targetProvince);
    }

    public void setFactionNames(String human, String enemy) {
        humanFaction.setText(human);
        enemyFaction.setText(enemy);
    }

    private String getLives(int x) {
        String lives = "";
        for (int i = 0; i < x; i++) {
            lives += "*";
        }
        return lives;
    }

    public void setArmyBindings() {
        /*
        conn.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                String oldNumber, String newNumber) {
                if (newNumber != null) {
                    armyHuman.setText(newNumber.toString());
                } else {
                    armyHuman.setText("Lol");
                }
            }
        });
        */

        armyAsize = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                Number oldNumber, Number newNumber) {
                armyHuman.setText(getLives(newNumber.intValue()));
            }
        };

        attackerArmy.addNumAvailableUnitsListener(armyAsize);

        armyHuman.setText(getLives(attackerArmy.numAvailableUnits()));
        
        armyBsize = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                Number oldNumber, Number newNumber) {
                String lives = "";
                for (int i = 0; i < newNumber.intValue(); i++) {
                    lives += "*";
                }
                armyEnemy.setText(lives);
            }
        };

        defenderArmy.addNumAvailableUnitsListener(armyBsize);

        armyEnemy.setText(getLives(defenderArmy.numAvailableUnits()));

        unitAnumTroops = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
            Number oldNumber, Number newNumber) {
                playAttackAnimation(enemyAttacks, humanUnit, oldNumber, newNumber);
            }
        };

        unitBnumTroops = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
            Number oldNumber, Number newNumber) {
                playAttackAnimation(humanAttacks, enemyUnit, oldNumber, newNumber);
            }
        };

    }

    private void playAttackAnimation(Timeline attackerAnimation, Circle defenderUnit, Number oldNumber, Number newNumber) {
        attackerAnimation.getKeyFrames().clear();
        KeyFrame kf1 = new KeyFrame(
            Duration.ZERO,
            new KeyValue(defenderUnit.radiusProperty(), oldNumber.intValue())
        );
        KeyFrame kf2 = new KeyFrame(
            Duration.millis(300),
            new KeyValue(defenderUnit.radiusProperty(), newNumber.intValue())
        );
        attackerAnimation.getKeyFrames().addAll(kf1, kf2);
        attackerAnimation.play();
    }
    public void removeArmyBindings() {
        attackerArmy.removeNumAvailableUnitsListener(armyAsize);
        defenderArmy.removeNumAvailableUnitsListener(armyBsize);
    }

    @FXML
    public void goBack(ActionEvent event) {
        try {mainMap.startGame();}
        catch (Exception e) {
            System.out.println("Couldn't return to main map");
        }
    }

    public void setHumanArmy(Army human) {
        this.attackerArmy = human;
    }

    public void setEnemyArmy(Army enemy) {
        this.defenderArmy = enemy;
    }

    @FXML
    private void startBattleAction(ActionEvent event) {
        startBattleButton.setVisible(false);
        startBattle();
    }

    private void startBattle() {
        battleMessages.appendText("Starting battle!\n");
        BattleResolver resolver = new BattleResolver(attackerArmy, defenderArmy, battleMessages);
        
        resolver.setArmyBindings(humanHealth.widthProperty(), enemyHealth.widthProperty());
        resolver.setUnitBindings(humanUnit.radiusProperty(), enemyUnit.radiusProperty());
        //resolver.setNumTroopsListeners(unitAnumTroops, unitBnumTroops);
        // setArmyBindings();

        resolver.startBattle();

        resolver.removeArmyBindings();
        
        removeArmyBindings();

        battleMessages.appendText("Battle finished!\n");

        if (resolver.getStatus() == BattleStatus.WIN_A) {
            battleMessages.appendText("Invader wins!\n");
        } else if (resolver.getStatus() == BattleStatus.WIN_B) {
            battleMessages.appendText("Defender wins!\n");
        }

        // TODO Remove defeated armies and units, transfer troops
    }

}