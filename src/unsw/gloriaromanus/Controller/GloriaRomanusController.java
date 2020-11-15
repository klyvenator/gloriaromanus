package unsw.gloriaromanus.Controller;

import unsw.gloriaromanus.Model.*;
import unsw.gloriaromanus.View.BattleScreen;
import unsw.gloriaromanus.View.StartScreen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.FeatureTable;
import com.esri.arcgisruntime.data.GeoPackage;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.internal.io.handler.request.ServerContextConcurrentHashMap.HashMapChangedEvent.Action;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyLayerResult;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol.HorizontalAlignment;
import com.esri.arcgisruntime.symbology.TextSymbol.VerticalAlignment;
import com.esri.arcgisruntime.data.Feature;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;

import org.json.JSONArray;
import org.json.JSONObject;

public class GloriaRomanusController{

  @FXML
  private MapView mapView;
  @FXML
  private TextField invading_province;
  @FXML
  private TextField opponent_province;
  @FXML
  private TextArea output_terminal;

  private ArcGISMap map;

  private Map<Town, Faction> provinceToOwningFactionMap;

  private Map<String, Integer> provinceToNumberTroopsMap;

  // private Map<String, List<Unit>> provinceToUnitListMap;
  private Map<String, Army> provinceToArmyMap;
  // private Map<String, Town> provinceToTownMap;

  private BattleScreen battleScreen;

  private String humanFaction;

  private Feature currentlySelectedHumanProvince;
  private Feature currentlySelectedEnemyProvince;
  private Feature currentlySelectedProvince;


  private FeatureLayer featureLayer_provinces;

  private List<String> factionNames;

  private List<Faction> currGameFactionList;

  private UnitFactory unitFactory;

  private List<Faction> loadGameFactionList = new ArrayList<Faction>();

  private StartScreen startScreen;

  @FXML
  private Label topBarFaction;
  @FXML
  private Label topBarGold;
  @FXML
  private Label topBarWealth;
  @FXML
  private Label pWProvinceName;
  @FXML
  private Label pWProvinceWealth;
  @FXML
  private ComboBox<String> pWRecruitList = new ComboBox<String>();
  @FXML
  private ListView<String> pWUnitList = new ListView<String>();
  @FXML
  private VBox provinceWindow;
  @FXML
  private ComboBox<String> pWTaxRate;
  @FXML
  private ListView<String> sWUnitList;


  // Secondary Window variables
  @FXML
  private VBox secondWindow;
  @FXML
  private Button sWConfirmButton;
  @FXML
  private Label sWOwnershipLabel;
  @FXML
  private Label sWProvinceName;
  @FXML
  private Label sWProvinceWealth;
  @FXML
  private Label sWFactionName;
  @FXML 
  private Label topBarYear;
  @FXML
  private VBox goalsWindow;
  @FXML
  private Label gWConditions;

  private String targetProvince;
  private boolean invadeMode;
  private boolean moveMode;

  // top right options window
  @FXML
  private VBox optionsWindow;
  @FXML 
  private MenuButton optionsButton;
  @FXML 
  private MenuItem saveGameButton;
  @FXML
  private MenuItem mainMenuButton;
  @FXML
  private TextField saveFileName;
  @FXML
  private Button saveGame;
  @FXML
  private Button saveGameBackButton;
  @FXML
  private Button goToMenuButton;
  private Goals goal;
  private int year;
  private int turnCount;
  private int numPlayers;



  @FXML
  private void initialize() throws JsonParseException, JsonMappingException, IOException {
    // TODO = you should rely on an object oriented design to determine ownership
    if( factionNames != null ){
      provinceToOwningFactionMap = getProvincesOwningToEachFaction(factionNames);
    }else if( loadGameFactionList != null){
      provinceToOwningFactionMap =  loadProvincesOwningToEachFaction(loadGameFactionList);
    }
    // this will fix the factionNames issue
    factionNames = new ArrayList<String>();
    for (Faction f: provinceToOwningFactionMap.values()){
      if (!factionNames.contains(f.getFactionName())) {
        factionNames.add(f.getFactionName());
      }
    }
    provinceToNumberTroopsMap = new HashMap<String, Integer>();
    Random r = new Random();
    for (Town town : provinceToOwningFactionMap.keySet()) {
      provinceToNumberTroopsMap.put(town.getTownName(), r.nextInt(500));
    } 
    // TODO = load this from a configuration file you create (user should be able to
    // select in loading screen)
    humanFaction = "Rome";

    currentlySelectedHumanProvince = null;
    currentlySelectedEnemyProvince = null;

    initializeProvinceLayers();


    // Kly's initialise code
    initialiseProvinceWindow();
    unitFactory = new UnitFactory();
    currentlySelectedProvince = null;
    targetProvince = null;
    invadeMode = false;
    moveMode = false;
    turnCount = 0;
    numPlayers = factionNames.size();
    goal = new Goals(provinceToOwningFactionMap.size());
    gWConditions.setText(goal.toString());
    if(year <= 1300){ year = 1300; }
    initialiseTopBar();

  }
  
  public void setBattleScreen(BattleScreen battleScreen) {
    this.battleScreen = battleScreen;
  }

  private void battleStuff() {
    String humanProvince = (String)currentlySelectedHumanProvince.getAttributes().get("name");
    String enemyProvince = (String)currentlySelectedEnemyProvince.getAttributes().get("name");
    
    // DEBUG
    Army a = new Army();
    Army b = new Army();
  
    Infantry infantry = new Infantry("Archers");
    a.addUnit(infantry);
  
    Artillery artillery = new Artillery("Catapults");
    b.addUnit(artillery);
  
    battleScreen.start(
      a,//provinceToArmyMap.get(humanProvince),
      b,//provinceToArmyMap.get(enemyProvince)
      "attacker",
      "home",
      null,
      null
    );
  }

/* USE SWINVADEBUTTON INSTEAD.


  @FXML
  public void clickedInvadeButton(ActionEvent e) throws IOException {
    if (currentlySelectedHumanProvince != null && currentlySelectedEnemyProvince != null){
      String humanProvince = (String)currentlySelectedHumanProvince.getAttributes().get("name");
      String enemyProvince = (String)currentlySelectedEnemyProvince.getAttributes().get("name");
      if (confirmIfProvincesConnected(humanProvince, enemyProvince)){
        // TODO = have better battle resolution than 50% chance of winning
        
        battleStuff();
        
        Random r = new Random();
        int choice = r.nextInt(2);
        if (choice == 0){
          // human won. Transfer 40% of troops of human over. No casualties by human, but enemy loses all troops
          int numTroopsToTransfer = provinceToNumberTroopsMap.get(humanProvince)*2/5;
          provinceToNumberTroopsMap.put(enemyProvince, numTroopsToTransfer);
          provinceToNumberTroopsMap.put(humanProvince, provinceToNumberTroopsMap.get(humanProvince)-numTroopsToTransfer);
          // TO DO uncomment
          //provinceToOwningFactionMap.put(enemyProvince, humanFaction);
          printMessageToTerminal("Won battle!");
        }
        else{
          // enemy won. Human loses 60% of soldiers in the province
          int numTroopsLost = provinceToNumberTroopsMap.get(humanProvince)*3/5;
          provinceToNumberTroopsMap.put(humanProvince, provinceToNumberTroopsMap.get(humanProvince)-numTroopsLost);
          printMessageToTerminal("Lost battle!");
        }
        resetSelections();  // reset selections in UI
        addAllPointGraphics(); // reset graphics
      }
      else{
        printMessageToTerminal("Provinces not adjacent, cannot invade!");
      }

    }
  }
  */

  /**
   * run this initially to update province owner, change feature in each
   * FeatureLayer to be visible/invisible depending on owner. Can also update
   * graphics initially
   */
  private void initializeProvinceLayers() throws JsonParseException, JsonMappingException, IOException {

    Basemap myBasemap = Basemap.createImagery();
    // myBasemap.getReferenceLayers().remove(0);
    map = new ArcGISMap(myBasemap);
    mapView.setMap(map);

    // note - tried having different FeatureLayers for AI and human provinces to
    // allow different selection colors, but deprecated setSelectionColor method
    // does nothing
    // so forced to only have 1 selection color (unless construct graphics overlays
    // to give color highlighting)
    GeoPackage gpkg_provinces = new GeoPackage("src/unsw/gloriaromanus/provinces_right_hand_fixed.gpkg");
    gpkg_provinces.loadAsync();
    gpkg_provinces.addDoneLoadingListener(() -> {
      if (gpkg_provinces.getLoadStatus() == LoadStatus.LOADED) {
        // create province border feature
        featureLayer_provinces = createFeatureLayer(gpkg_provinces);
        map.getOperationalLayers().add(featureLayer_provinces);
      } else {
        System.out.println("load failure");
      }
    });

    addAllPointGraphics();
  }

  private void addAllPointGraphics() throws JsonParseException, JsonMappingException, IOException {
    mapView.getGraphicsOverlays().clear();

    InputStream inputStream = new FileInputStream(new File("src/unsw/gloriaromanus/provinces_label.geojson"));
    FeatureCollection fc = new ObjectMapper().readValue(inputStream, FeatureCollection.class);

    GraphicsOverlay graphicsOverlay = new GraphicsOverlay();

    for (org.geojson.Feature f : fc.getFeatures()) {
      if (f.getGeometry() instanceof org.geojson.Point) {
        org.geojson.Point p = (org.geojson.Point) f.getGeometry();
        LngLatAlt coor = p.getCoordinates();
        Point curPoint = new Point(coor.getLongitude(), coor.getLatitude(), SpatialReferences.getWgs84());
        PictureMarkerSymbol s = null;
        // this get's the name of the province from the total List of Provinces
        String provinceName = (String) f.getProperty("name");
        Town provinceTown;
        for (Town town : provinceToOwningFactionMap.keySet()) {
          if(town.getTownName().equals(provinceName)){
            provinceTown = town;
            Faction factionObject = provinceToOwningFactionMap.get(provinceTown);
            String faction = factionObject.getFactionName();
            TextSymbol t = new TextSymbol(10,
            faction + "\n" + provinceTown.getTownName() + "\n", 0xFFFF0000,
            HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM);

            switch (faction) {
              case "Gaul":
                // note can instantiate a PictureMarkerSymbol using the JavaFX Image class - so could
                // construct it with custom-produced BufferedImages stored in Ram
                // http://jens-na.github.io/2013/11/06/java-how-to-concat-buffered-images/
                // then you could convert it to JavaFX image https://stackoverflow.com/a/30970114
    
                // you can pass in a filename to create a PictureMarkerSymbol...
                s = new PictureMarkerSymbol("images/barbarian.png");
                break;
              case "Rome":
                // you can also pass in a javafx Image to create a PictureMarkerSymbol (different to BufferedImage)
                s = new PictureMarkerSymbol("images/legionary.png");
                break;
              case "Celtic Briton":
                s = new PictureMarkerSymbol("images/knight.png");
                break;
              case "Greek":
                s = new PictureMarkerSymbol("images/greek.png");
                break;
              case "Egyptian":
                s = new PictureMarkerSymbol("images/egyptian.png");
                break;
              case "Thracian":
                s = new PictureMarkerSymbol("images/thracian.png");
                break;
            }
              t.setHaloColor(0xFFFFFFFF);
              t.setHaloWidth(2);
              Graphic gPic = new Graphic(curPoint, s);
              Graphic gText = new Graphic(curPoint, t);
              graphicsOverlay.getGraphics().add(gPic);
              graphicsOverlay.getGraphics().add(gText);
            }
          }
        } else {
          System.out.println("Non-point geo json object in file");
        }

    }

    inputStream.close();
    mapView.getGraphicsOverlays().add(graphicsOverlay);
  }

  private void closeWindows() {
    invadeMode = false;
    moveMode = false;
    closeProvinceWindow();
    secondWindow.setVisible(false);
  }

  private FeatureLayer createFeatureLayer(GeoPackage gpkg_provinces) {
    FeatureTable geoPackageTable_provinces = gpkg_provinces.getGeoPackageFeatureTables().get(0);

    // Make sure a feature table was found in the package
    if (geoPackageTable_provinces == null) {
      System.out.println("no geoPackageTable found");
      return null;
    }

    // Create a layer to show the feature table
    FeatureLayer flp = new FeatureLayer(geoPackageTable_provinces);

    // https://developers.arcgis.com/java/latest/guide/identify-features.htm
    // listen to the mouse clicked event on the map view
    mapView.setOnMouseClicked(e -> {
      
      if (e.getButton() == MouseButton.SECONDARY) {
        // TODO = Turn this into observer.
        closeWindows();
      }

      // was the main button pressed?
      if (e.getButton() == MouseButton.PRIMARY) {
        // get the screen point where the user clicked or tapped
        Point2D screenPoint = new Point2D(e.getX(), e.getY());

        // specifying the layer to identify, where to identify, tolerance around point,
        // to return pop-ups only, and
        // maximum results
        // note - if select right on border, even with 0 tolerance, can select multiple
        // features - so have to check length of result when handling it
        final ListenableFuture<IdentifyLayerResult> identifyFuture = mapView.identifyLayerAsync(flp,
            screenPoint, 0, false, 25);

        // add a listener to the future
        identifyFuture.addDoneListener(() -> {
          try {
            // get the identify results from the future - returns when the operation is
            // complete
            IdentifyLayerResult identifyLayerResult = identifyFuture.get();
            // a reference to the feature layer can be used, for example, to select
            // identified features
            if (identifyLayerResult.getLayerContent() instanceof FeatureLayer) {
              FeatureLayer featureLayer = (FeatureLayer) identifyLayerResult.getLayerContent();
              // select all features that were identified
              List<Feature> features = identifyLayerResult.getElements().stream().map(f -> (Feature) f).collect(Collectors.toList());

              if (features.size() > 1){
                printMessageToTerminal("Have more than 1 element - you might have clicked on boundary!");
              }
              else if (features.size() == 1){
                // note maybe best to track whether selected...
                Feature f = features.get(0);
                String province = (String)f.getAttributes().get("name");

                if (invadeMode) {
                 if ((getFaction(province).getFactionName().equals(humanFaction))) {
                    Alert alert = new Alert(AlertType.WARNING, "Please select an enemy faction", ButtonType.OK);
                    alert.showAndWait(); 
                  } else {
                    targetProvince = province;
                    openInvadeWindow();
                  }
                } else if (moveMode) {
                  if ((getFaction(province).getFactionName().equals(humanFaction))) {
                    targetProvince = province;
                    openMoveWindow();
                  } else {
                    Alert alert = new Alert(AlertType.WARNING, "Please select own faction", ButtonType.OK);
                    alert.showAndWait(); 
                  }
                } else {

                  if (currentlySelectedProvince != null){
                    featureLayer.unselectFeature(currentlySelectedProvince);
                  }

                  if (getFaction(province).getFactionName().equals(humanFaction)){
                    // province owned by human
                    currentlySelectedProvince = f;
                    loadProvinceWindow(province);
                  }
                  else{
                    currentlySelectedProvince = f;
                  }
  
                  featureLayer.selectFeature(f);     
                }
              }

              
            }
          } catch (InterruptedException | ExecutionException ex) {
            // ... must deal with checked exceptions thrown from the async identify
            // operation
            System.out.println("InterruptedException occurred");
          }
        });
      }
    });
    return flp;
  }

  private ArrayList<String> getHumanProvincesList() throws IOException {
    // https://developers.arcgis.com/labs/java/query-a-feature-layer/

    String content = Files.readString(Paths.get("src/unsw/gloriaromanus/initial_province_ownership.json"));
    JSONObject ownership = new JSONObject(content);
    return ArrayUtil.convert(ownership.getJSONArray(humanFaction));
  }

  /**
   * returns query for arcgis to get features representing human provinces can
   * apply this to FeatureTable.queryFeaturesAsync() pass string to
   * QueryParameters.setWhereClause() as the query string
   */
  private String getHumanProvincesQuery() throws IOException {
    LinkedList<String> l = new LinkedList<String>();
    for (String hp : getHumanProvincesList()) {
      l.add("name='" + hp + "'");
    }
    return "(" + String.join(" OR ", l) + ")";
  }

  private boolean confirmIfProvincesConnected(String province1, String province2) throws IOException {
    String content = Files.readString(Paths.get("src/unsw/gloriaromanus/province_adjacency_matrix_fully_connected.json"));
    JSONObject provinceAdjacencyMatrix = new JSONObject(content);
    return provinceAdjacencyMatrix.getJSONObject(province1).getBoolean(province2);
  }

  private void resetSelections(){
    featureLayer_provinces.unselectFeatures(Arrays.asList(currentlySelectedEnemyProvince, currentlySelectedHumanProvince));
    currentlySelectedEnemyProvince = null;
    currentlySelectedHumanProvince = null;
    invading_province.setText("");
    opponent_province.setText("");
  }

  private void printMessageToTerminal(String message){
    output_terminal.appendText(message+"\n");
  }

  // takes a string list of faction names, creates the factions and randomly allocates them towns
  private Map<Town, Faction> getProvincesOwningToEachFaction(List<String> factionNames) throws IOException {
    currGameFactionList = allocateTowns(factionNames);
    
    Map<Town, Faction> m = new HashMap<Town, Faction>();
    for (Faction f : currGameFactionList) {
      List<Town> towns = f.getTowns();
      // sets the Town as key and it's corresponding faction
      for (Town t : towns) {
        m.put(t, f);
      }
    }
    return m;
  }
  // from save file, loads the faction list and creates the map of a previous game
  private Map<Town, Faction> loadProvincesOwningToEachFaction(List<Faction> factionList) throws IOException {
    currGameFactionList = factionList;
    Map<Town, Faction> m = new HashMap<Town, Faction>();
    for (Faction f : factionList) {
      List<Town> towns = f.getTowns();
      // sets the Town as key and it's corresponding faction
      for (Town t : towns) {
        m.put(t, f);
      }
    }
    return m;
  }

  // added by jayden - to get a simple list of all the provinces from a json file
  private static List<String> getProvinceList() throws IOException {
    String content = Files.readString(Paths.get("src/unsw/gloriaromanus/Json/list_provinces.json"));
    List<String> list = new ArrayList<String>();
    JSONObject province = new JSONObject(content);
    JSONArray jA = province.getJSONArray("Provinces");
    
    for (int i = 0; i < jA.length(); i++) {
      list.add( jA.getString(i) );
    }
    return list;
  }

  // gets a list of factions and randomly allocates them provinces and creates towns
  // the returned list is a list of Factions, that have a list of province towns attached
  // to each faction
  public static List<Faction> allocateTowns(List<String> factions) throws IOException{
    Random rand = new Random();
    // remove content for milestone 3 and jsut read file as normal
    List<String> list = getProvinceList();
    List<Faction> facList = new ArrayList<Faction>();
    for(String f : factions){
      Faction newFac = new Faction(f);
      facList.add(newFac);
    }
    // uses the list of provinces and randomly allocates it to the factions chosen by players
    while( !list.isEmpty() ) {
      int randomIndex = rand.nextInt(list.size());
      int randomFactionIndex = rand.nextInt(facList.size());
      
      String randomTown = list.get(randomIndex);
      Faction randomFaction = facList.get(randomFactionIndex);

      Town randTown = randomFaction.addTown(randomFaction, randomTown);
      randTown.setArmy(new Army(randTown));

      list.remove(randomIndex);
    }
    return facList;
  }

  public Faction getFaction(String provinceName) {
    for (Town t: provinceToOwningFactionMap.keySet()){
      if (t.getTownName().equals(provinceName)) {
        return provinceToOwningFactionMap.get(t);
      }
    }
    return null;
  }

  public Faction StringToFaction(String factionName) {
    for (Faction f: provinceToOwningFactionMap.values()){
      if (f.getFactionName().equals(factionName)) {
        return f;
      }
    }
    return null;
  }

  public Town StringToTown(String province) {
    for (Town f: provinceToOwningFactionMap.keySet()){
      if (f.getTownName().equals(province)) {
        return f;
      }
    }
    return null;
  }

  public void setFactionList(List<String> listOfFactionNames){
    this.factionNames = listOfFactionNames;
  }
  public void setLoadGameFactionList(List<Faction> facList){
    this.loadGameFactionList = facList;
  }
  public List<Faction> getGameFactionList(){
    return currGameFactionList;
  }


  public void loadProvinceWindow(String province) {
    clearTownUnitList(pWUnitList);
    pWProvinceName.setText(province);
    pWProvinceWealth.setText(Integer.toString(StringToTown(province).getWealth()));
    fillTownUnitList(province, pWUnitList);
    pWTaxRate.getItems().clear();
    pWTaxRate.getItems().add("Low Tax Rate");
    pWTaxRate.getItems().add("Normal Tax Rate");
    pWTaxRate.getItems().add("High Tax Rate");
    pWTaxRate.getItems().add("Very High Tax Rate");
    Town town = StringToTown(province);
    switch(town.getTaxStatus()) {
      case "Low":
        pWTaxRate.getSelectionModel().select(0);
        break;
      case "Normal":
        pWTaxRate.getSelectionModel().select(1);
        break;
      case "High":
        pWTaxRate.getSelectionModel().select(2);
        break;
      case "Very high":
        pWTaxRate.getSelectionModel().select(3);
        break;
    }
    provinceWindow.setVisible(true);
  }

  public void fillTownUnitList(String province, ListView<String> list) {
    Town town = StringToTown(province);
    Army a = town.getArmy();
    if (a.getAllUnits().size() == 0) {
      list.getItems().add("No soldiers in this town");
    } else {
      for (Unit u: a.getAllUnits()) {
        list.getItems().add(u.toString());
      }
    }
  }

  public void clearTownUnitList(ListView<String> list) {
    list.getItems().clear();
  }

  @FXML
  public void closeProvinceWindow() {
    provinceWindow.setVisible(false);
  }

  public void initialiseProvinceWindow() {
    // TODO import from json list of units.
    String[] units = {"Select unit", "Melee Infantry", "Legionary"};
    pWRecruitList.getItems().addAll(units);
    pWRecruitList.getSelectionModel().selectFirst();
    pWUnitList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
  }

  @FXML
  public void handleRecruitButton() {
    String unit = pWRecruitList.getValue();
    Faction faction = StringToFaction(humanFaction);
    if (unitFactory.getUnitCost(unit) > faction.getTotalGold()) {
      Alert alert = new Alert(AlertType.WARNING, "Not enough gold.", ButtonType.OK);
      alert.showAndWait();
    } else {
      if (unit.equals("Select unit")) {
        Alert alert = new Alert(AlertType.WARNING, "Please select a valid unit", ButtonType.OK);
        alert.showAndWait();    
      } else {
        clearTownUnitList(pWUnitList);
        Town town = StringToTown(pWProvinceName.getText());
        Unit unitObject = unitFactory.createUnit(unit, faction, town);
        faction.setGold(faction.getTotalGold() - unitObject.getCost());
        if (unitObject.getTurnsToMake() > 0) {
          town.trainUnit(unitObject);
        } else {
          town.addUnit(unitObject);
        }
        fillTownUnitList(pWProvinceName.getText(), pWUnitList);
      }
    }
  }



  public void openInvadeWindow() {
    sWOwnershipLabel.setText("ENEMY PROVINCE");
    sWProvinceName.setText(targetProvince);
    sWProvinceWealth.setText(Integer.toString(StringToTown(targetProvince).getWealth()));
    sWFactionName.setText(getFaction(targetProvince).getFactionName());
    sWConfirmButton.setText("Confirm Invade");
    clearTownUnitList(sWUnitList);
    fillTownUnitList(targetProvince, sWUnitList);
    secondWindow.setVisible(true);
  }

  public void openMoveWindow() {
    sWOwnershipLabel.setText("YOUR PROVINCE");
    sWProvinceName.setText(targetProvince);
    sWProvinceWealth.setText(Integer.toString(StringToTown(targetProvince).getWealth()));
    sWFactionName.setText(getFaction(targetProvince).getFactionName());
    sWConfirmButton.setText("Confirm Move");
    clearTownUnitList(sWUnitList);
    fillTownUnitList(targetProvince, sWUnitList);
    secondWindow.setVisible(true);
  }

  @FXML
  public void handlesWConfirmButton() {
    String humanProvince = pWProvinceName.getText();
    Army yourArmy = StringToTown(humanProvince).getArmy();
    Army enemyArmy = StringToTown(targetProvince).getArmy();
    //INVADE CODE FOR JIBI
    Infantry infantry1 = new Infantry("Archers");
    yourArmy.addUnit(infantry1);
  
    Infantry infantry2 = new Infantry("Archers Again");
    yourArmy.addUnit(infantry2);
    
    Infantry infantry3 = new Infantry("Archers three");
    yourArmy.addUnit(infantry3);
    
    Artillery artillery1 = new Artillery("Catapults");
    enemyArmy.addUnit(artillery1);
    
    Artillery artillery2 = new Artillery("Catapults lol");
    enemyArmy.addUnit(artillery2);

    Artillery artillery3 = new Artillery("Catapults again");
    enemyArmy.addUnit(artillery3);

    Faction current = provinceToOwningFactionMap.get(StringToTown(humanProvince));
    Faction enemy = provinceToOwningFactionMap.get(StringToTown(targetProvince));

    battleScreen.start(
      yourArmy, enemyArmy,
      humanProvince, targetProvince,
      current, enemy
    );

  }
  @FXML
  public void handleInvadeButton() {
    invadeMode = true;
  }

  @FXML
  public void handleMoveButton() {
    moveMode = true;
  }

  @FXML
  public void handlePWTaxRate() {
    switch(pWTaxRate.getValue().toString()) {
      case "Low Tax Rate":
        StringToTown(pWProvinceName.getText()).updateTaxStatus("Low");
        break;
      case "Normal Tax Rate":
        StringToTown(pWProvinceName.getText()).updateTaxStatus("Normal");
        break;
      case "High Tax Rate":
        StringToTown(pWProvinceName.getText()).updateTaxStatus("High");
        break;
      case "Very High Tax Rate":
        StringToTown(pWProvinceName.getText()).updateTaxStatus("Very high");
        break;
    }
  }

  @FXML
  public void handleEndTurnButton() {
    closeWindows();
    Faction f = StringToFaction(humanFaction);
    f.endTurnUpdate();
    if (goal.checkWin(f)) {
      Alert alert = new Alert(AlertType.WARNING, "Congrats you have won!", ButtonType.OK);
      alert.showAndWait(); 
      terminate();
    } else {
      if (turnCount == numPlayers - 1) {
        year++;
        turnCount = 0;
      } else {
        turnCount++;
      }
      setNextFaction();
    }
  }

  public void setNextFaction() {
    int index = 0;
    for (String f : factionNames) {
      if (f.equals(humanFaction)) {
        index = factionNames.indexOf(f);
        break;
      } 
    }
    try {
      index++;
      humanFaction = factionNames.get(index);
    } catch (Exception indexOutOfBoundsException) {
      humanFaction = factionNames.get(0);
    }
    initialiseTopBar();
  }

  public void initialiseTopBar() {
    topBarGold.textProperty().bind(StringToFaction(humanFaction).getGoldProperty().asString());
    topBarWealth.textProperty().bind(StringToFaction(humanFaction).getWealthProperty().asString());
    topBarFaction.setText(humanFaction);
    topBarYear.setText(Integer.toString(year));
  }
  @FXML
  public void handleGoalsButton() {
    if(goalsWindow.isVisible()) {
      goalsWindow.setVisible(false);
    } else {
      goalsWindow.setVisible(true);
    }
  }

  // after Options is clicked and Save Game selected show text field for input
  @FXML
  private void handleSaveGame(ActionEvent event){
    saveFileName.setVisible(true);
    saveGame.setVisible(true);
    saveGameBackButton.setVisible(true);
  }
  // if Main menu button is clicked 
  @FXML
  private void handleMainMenuButton(ActionEvent event){
    Alert alert = new Alert(AlertType.WARNING, "All Unsaved Progress Will Be Lost!", ButtonType.OK);
    alert.showAndWait();
    goToMenuButton.setVisible(true);
  }
  
  // gets the user input file name for the new save file
  // saves creates the save file and prints out a confirmation
  @FXML
  private void handleSaveGameButton(ActionEvent event){
    String fileName = saveFileName.getText();
    try {
      new SaveFile(getGameFactionList(), fileName, year);
      saveFileName.setVisible(false);
      saveGame.setVisible(false);
      Alert alert = new Alert(AlertType.CONFIRMATION, "Current Game is Saved as "+fileName+".json", ButtonType.OK);
      alert.showAndWait();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  @FXML
  private void handleSaveGameBack(ActionEvent event){
    saveFileName.setVisible(false);
    saveGame.setVisible(false);
    saveGameBackButton.setVisible(false);
  }
  @FXML
  private void handleGoToMenuButton(ActionEvent event){
    terminate();
    startScreen.start();
  }
  @FXML
  private void handleExitGame(ActionEvent event){
    Alert alert = new Alert(AlertType.CONFIRMATION, 
    "You are about to exit the game all unsaved progress will be lost", 
    ButtonType.OK, ButtonType.CANCEL);
    Optional<ButtonType> result = alert.showAndWait();
    if( result.get() == ButtonType.CANCEL ){ return; }
    else{
      terminate();
      System.exit(0);
    }
  }
  public void setStartScreen(StartScreen startScreen){
    this.startScreen = startScreen;
  }
  public void setYear(int gameYear){
    this.year = gameYear;
  }
  /**
   * Stops and releases all resources used in application.
   */
  public void terminate() {

    if (mapView != null) {
      mapView.dispose();
    }
  }

}