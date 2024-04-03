package edu.rit.croatia.swen383.g4.dmview;

import edu.rit.croatia.swen383.g4.dmcontroller.DietManagerController;
import edu.rit.croatia.swen383.g4.dmmodel.DietManagerModel;
import edu.rit.croatia.swen383.g4.food.BasicFood;
import edu.rit.croatia.swen383.g4.food.Food;
import edu.rit.croatia.swen383.g4.food.Recipe;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    HBox logInputArea = createLogInputArea();
    statisticsGrid = createStatisticsGrid();

    foodListTextArea = new TextArea();
    foodListTextArea.setEditable(false);
    foodListTextArea.setMinHeight(300);
    dailyLogTextArea = new TextArea();
    dailyLogTextArea.setEditable(false);
    foodListTextArea.setMinHeight(200);

    GridPane topInputGrid = new GridPane();
    topInputGrid.setHgap(40);
    topInputGrid.setAlignment(Pos.CENTER);
    topInputGrid.addColumn(0, basicFoodInputGrid);
    topInputGrid.addColumn(1, recipeInputGrid);

    VBox foodArea = new VBox(10, topInputGrid, foodListTextArea);
    foodArea.setBorder(
        new Border(
            new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                new CornerRadii(3),
                new BorderWidths(0.2))));
    foodArea.setPadding(new Insets(10));

    Label foodLabel = new Label("Food Addition and Display");
    foodLabel.setFont(new Font(new Text().getFont().getName(), 16));

    VBox logArea = new VBox(10, logInputArea, dailyLogTextArea);
    logArea.setBorder(
        new Border(
            new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                new CornerRadii(3),
                new BorderWidths(0.2))));
    logArea.setPadding(new Insets(10));

    Label logLabel = new Label("Daily Log Addition and Display");
    logLabel.setFont(new Font(new Text().getFont().getName(), 16));

    VBox mainLayout = new VBox(10, foodLabel, foodArea, logLabel, logArea);
    mainLayout.setPadding(new Insets(10));
    mainLayout.setPrefWidth(1200);

    VBox statistics = new VBox(10, statisticsGrid);
    statistics.setPadding(new Insets(10));
    statistics.setPrefWidth(400);

    HBox root = new HBox(10, mainLayout, statistics);
    root.setPadding(new Insets(10));
    root.setBackground(
        new Background(
            new BackgroundFill(
                Paint.valueOf("#ffffff"),
                CornerRadii.EMPTY,
                new Insets(10))));

    // Add event handlers
    addFoodButton.setOnAction(event -> controller.addBasicFood());
    setIngredientsButton.setOnAction(event -> updateRecipeIngredients());
    addRecipeButton.setOnAction(event -> controller.addRecipe());
    addLogEntryButton.setOnAction(event -> controller.addLogEntry());
    datePicker.setOnAction(event -> controller.displayLogForSelectedDate());

    // Set initial data
    controller.updateFoodList();
    controller.updateDailyLog();

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
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
    grid.setMinWidth(600);
    grid.setMinHeight(400);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setAlignment(Pos.CENTER);

    Map<String, Double> nutrients = this.controller.getNutrients();

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
    pieChartData.forEach(data -> {
      if (data.getName().equals("Calories")) {
        data
            .nameProperty()
            .bind(
                Bindings.concat(
                    data.getName(),
                    " ",
                    String.format("%.2f", data.pieValueProperty().get()),
                    "kcal"));
      } else {
        data
            .nameProperty()
            .bind(
                Bindings.concat(
                    data.getName(),
                    " ",
                    String.format("%.2f", data.pieValueProperty().get()),
                    "g"));
      }
    });

    nutritionChart.setTitle("Daily Nutrient Intake");

    grid.addRow(0, nutritionChart);

    return grid;
  }

  public void updatePieChart() {
    PieChart pieChart = (PieChart) statisticsGrid.getChildren().get(0);

    Map<String, Double> nutrients = this.controller.getNutrients();
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
    pieChart.setData(pieChartData);

    pieChartData.forEach(data -> {
      if (data.getName().equals("Calories")) {
        data
            .nameProperty()
            .bind(
                Bindings.concat(
                    data.getName(),
                    " ",
                    String.format("%.2f", data.pieValueProperty().get()),
                    " kcal"));
      } else {
        data
            .nameProperty()
            .bind(
                Bindings.concat(
                    data.getName(),
                    " ",
                    String.format("%.2f", data.pieValueProperty().get()),
                    " g"));
      }
    });
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

    List<Food> foods = new ArrayList<>();
    for (Map.Entry<Food, Double> entry : foodsMap.entrySet()) {
      foods.add(entry.getKey());
    }

    return new Recipe(recipeName, foodsMap, foods);
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