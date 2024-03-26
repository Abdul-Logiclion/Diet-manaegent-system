package edu.rit.croatia.swen383.g4.dmview;

import edu.rit.croatia.swen383.g4.dmcontroller.DietManagerController;
import edu.rit.croatia.swen383.g4.dmmodel.DietManagerModel;
import edu.rit.croatia.swen383.g4.food.BasicFood;
import edu.rit.croatia.swen383.g4.food.Food;
import edu.rit.croatia.swen383.g4.food.Recipe;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * The type Diet manager view.
 */
public class DietManagerView extends Application {

  private DietManagerController controller;

  // UI components
  private TextField foodNameField;
  private TextField caloriesField;
  private TextField fatField;
  private TextField carbohydratesField;
  private TextField proteinField;
  private Button addFoodButton;

  private TextField recipeNameField;
  private TextField numOfIngredientsField;
  private Button setIngredientsButton;
  private ComboBox[] ingredientComboBoxes;
  private TextField[] ingredientServingFields;
  private Button addRecipeButton;

  private TextArea foodListTextArea;
  private TextArea dailyLogTextArea;

  private GridPane basicFoodInputGrid;
  private GridPane recipeInputGrid;
  private GridPane statisticsGrid;
  private PieChart nutritionChart;

  private ComboBox<String> foodSelectionComboBox;
  private TextField servingAmountField;
  private DatePicker datePicker;
  private Button addLogEntryButton;

  @Override
  public void init() {
    controller = new DietManagerController(
        this,
        new DietManagerModel("foods.csv", "log.csv"));
  }

  @Override
  public void start(Stage primaryStage) {
    // Create the user interface
    basicFoodInputGrid = createBasicFoodInputGrid();
    recipeInputGrid = createRecipeInputGrid();
    statisticsGrid = createStatisticsGrid();
    HBox logInputArea = createLogInputArea();

    foodListTextArea = new TextArea();
    foodListTextArea.setEditable(false);
    dailyLogTextArea = new TextArea();
    dailyLogTextArea.setEditable(false);

    GridPane topInputGrid = new GridPane();
    topInputGrid.setHgap(40);
    topInputGrid.setAlignment(Pos.CENTER);
    topInputGrid.addColumn(0, basicFoodInputGrid);
    topInputGrid.addColumn(1, recipeInputGrid);

    VBox mainLayout = new VBox(
        10,
        topInputGrid,
        foodListTextArea,
        logInputArea,
        dailyLogTextArea);
    mainLayout.setPadding(new Insets(10));
    mainLayout.setPrefWidth(1000);

    VBox statistics = new VBox(10, statisticsGrid);
    statistics.setPadding(new Insets(10));
    statistics.setPrefWidth(400);

    HBox root = new HBox(10, mainLayout, statistics);
    root.setPadding(new Insets(10));

    // Add event handlers
    addFoodButton.setOnAction(event -> controller.addBasicFood());
    setIngredientsButton.setOnAction(event -> updateRecipeIngredients());
    addRecipeButton.setOnAction(event -> controller.addRecipe());
    addLogEntryButton.setOnAction(event -> controller.addLogEntry());
    datePicker.setOnAction(event -> controller.displayLogForSelectedDate());

    // Set initial data
    controller.updateFoodList();
    controller.updateDailyLog();

    primaryStage.setScene(new Scene(root));
    primaryStage.setTitle("Diet Manager");
    primaryStage.show();
  }

  private GridPane createBasicFoodInputGrid() {
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setAlignment(Pos.CENTER);

    foodNameField = new TextField();
    caloriesField = new TextField();
    fatField = new TextField();
    carbohydratesField = new TextField();
    proteinField = new TextField();
    addFoodButton = new Button("Add Food");

    grid.addRow(0, new Label("Food Name:"), foodNameField);
    grid.addRow(1, new Label("Calories:"), caloriesField);
    grid.addRow(2, new Label("Fat:"), fatField);
    grid.addRow(3, new Label("Carbohydrates:"), carbohydratesField);
    grid.addRow(4, new Label("Protein:"), proteinField);
    grid.add(addFoodButton, 1, 6);

    return grid;
  }

  private GridPane createRecipeInputGrid() {
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setAlignment(Pos.CENTER);

    recipeNameField = new TextField();
    numOfIngredientsField = new TextField();
    ingredientComboBoxes = new ComboBox[5];
    ingredientServingFields = new TextField[5];
    setIngredientsButton = new Button("Add Ingredients");
    addRecipeButton = new Button("Add Recipe");

    for (int i = 0; i < 5; i++) {
      ingredientComboBoxes[i] = new ComboBox<>();
      ingredientServingFields[i] = new TextField();
      ingredientComboBoxes[i].setDisable(true);
      ingredientServingFields[i].setDisable(true);
    }

    grid.addRow(0, new Label("Recipe Name:"), recipeNameField);
    grid.addRow(
        1,
        new Label("Number of Ingredients (max 5):"),
        numOfIngredientsField,
        setIngredientsButton);

    return grid;
  }

  private GridPane createStatisticsGrid() {
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setAlignment(Pos.CENTER);

    updatePieChart();

    grid.addRow(0, nutritionChart);

    return grid;
  }

  public void updatePieChart() {
    Map<String, Double> nutrients = this.controller.getNutrients();

    System.out.println("View nutrients: " + nutrients);

    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        new PieChart.Data(
            (String) nutrients.keySet().toArray()[0],
            nutrients.get("Calories")),
        new PieChart.Data(
            (String) nutrients.keySet().toArray()[1],
            nutrients.get("Fat")),
        new PieChart.Data(
            (String) nutrients.keySet().toArray()[2],
            nutrients.get("Carbohydrates")),
        new PieChart.Data(
            (String) nutrients.keySet().toArray()[3],
            nutrients.get("Protein")));
    nutritionChart = new PieChart(pieChartData);
    nutritionChart.setTitle("Daily Nutrient Intake");
  }

  private HBox createLogInputArea() {
    HBox hbox = new HBox(30);
    hbox.setAlignment(Pos.CENTER);

    foodSelectionComboBox = new ComboBox<>();
    setFoodSelectionOptions(controller.getFood());

    servingAmountField = new TextField();
    datePicker = new DatePicker();
    datePicker.setValue(LocalDate.now());
    addLogEntryButton = new Button("Add Log Entry");

    hbox
        .getChildren()
        .addAll(
            new Label("Select Food:"),
            foodSelectionComboBox,
            new Label("Serving Amount:"),
            servingAmountField,
            new Label("Select Date:"),
            datePicker,
            addLogEntryButton);

    return hbox;
  }

  /**
   * Sets food selection options.
   *
   * @param foodNames the food names
   */
  public void setFoodSelectionOptions(List<String> foodNames) {
    foodSelectionComboBox.getItems().setAll(foodNames);
  }

  private void updateRecipeIngredients() {
    if (getNumberOfIngredients() > 5 || getNumberOfIngredients() < 1) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("RECONFIGURE INGREDIENT AMOUNT");
      alert.setHeaderText("You have entered an invalid number of ingredients");
      alert.setContentText("Please enter a number between 1 and 5");
      alert.showAndWait();

      return;
    }

    int numberOfIngredients = getNumberOfIngredients();
    List<String> foodNames = controller.getFood();

    for (int i = 0; i < numberOfIngredients; i++) {
      ComboBox<String> comboBox = new ComboBox<>();
      comboBox.getItems().addAll(foodNames);
      TextField textField = new TextField();
      ingredientComboBoxes[i] = comboBox;
      ingredientServingFields[i] = textField;
      recipeInputGrid.addRow(
          2 + i,
          new Label("Ingredient " + (i + 1) + ":"),
          ingredientComboBoxes[i],
          new Label("Amount:"),
          ingredientServingFields[i]);
    }
    recipeInputGrid.add(addRecipeButton, 1, 2 + numberOfIngredients);
  }

  /**
   * Gets input recipe.
   *
   * @return the input recipe
   */
  public Recipe getInputRecipe() {
    String recipeName = recipeNameField.getText();
    Map<Food, Double> foodsMap = new HashMap<>();

    for (int i = 0; i < ingredientComboBoxes.length; i++) {
      Object selectedItem = ingredientComboBoxes[i].getSelectionModel().getSelectedItem();
      if (selectedItem == null) {
        continue;
      }
      String foodName = selectedItem.toString();
      Food food = controller.getFoodByName(foodName);
      double servings = Double.parseDouble(
          ingredientServingFields[i].getText());
      foodsMap.put(food, servings);
    }

    return new Recipe(recipeName, foodsMap);
  }

  /**
   * Sets food list text.
   *
   * @param text the text
   */
  public void setFoodListText(String text) {
    String modifiedText = "Basic Foods:\n----------------\n" + text;
    foodListTextArea.setText(modifiedText);
  }

  /**
   * Sets daily log text.
   *
   * @param text the text
   */
  public void setDailyLogText(String text) {
    String modifiedText = "Today's Log:\n----------------\n" + text;
    dailyLogTextArea.setText(modifiedText);
  }

  /**
   * Gets input basic food.
   *
   * @return the input basic food
   */
  public BasicFood getInputBasicFood() {
    String foodName = foodNameField.getText();
    double calories = Double.parseDouble(caloriesField.getText());
    double fat = Double.parseDouble(fatField.getText());
    double carbohydrates = Double.parseDouble(carbohydratesField.getText());
    double protein = Double.parseDouble(proteinField.getText());

    return new BasicFood(foodName, calories, fat, carbohydrates, protein);
  }

  /**
   * Gets number of ingredients.
   *
   * @return the number of ingredients
   */
  public int getNumberOfIngredients() {
    return Integer.parseInt(numOfIngredientsField.getText());
  }

  /**
   * Gets selected food.
   *
   * @return the selected food
   */
  public String getSelectedFood() {
    return foodSelectionComboBox.getSelectionModel().getSelectedItem();
  }

  /**
   * Gets serving amount.
   *
   * @return the serving amount
   * @throws NumberFormatException the number format exception
   */
  public double getServingAmount() throws NumberFormatException {
    return Double.parseDouble(servingAmountField.getText());
  }

  /**
   * Gets selected date.
   *
   * @return the selected date
   */
  public LocalDate getSelectedDate() {
    return datePicker.getValue();
  }

  /**
   * Show alert.
   *
   * @param title   the title
   * @param content the content
   */
  public void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setContentText(content);
    alert.showAndWait();
  }
}