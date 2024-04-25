package edu.rit.croatia.swen383.g4.dmview;

import edu.rit.croatia.swen383.g4.dmcontroller.DietManagerController;
import edu.rit.croatia.swen383.g4.dmmodel.DietManagerModel;
import edu.rit.croatia.swen383.g4.food.BasicFood;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import edu.rit.croatia.swen383.g4.logs.DailyLog;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import edu.rit.croatia.swen383.g4.util.CsvHandler;

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
private ComboBox<String>[] ingredientComboBoxes;
  private TextField[] ingredientServingFields;
  private Button addRecipeButton;

  private TextArea foodListTextArea;
  private TextArea dailyLogTextArea;
  private TextArea dailyExerciseTextArea;

  private GridPane basicFoodInputGrid;
  private GridPane recipeInputGrid;

  private ComboBox<String> foodSelectionComboBox;
  private TextField servingAmountField;
  private DatePicker datePicker;
  private Button addLogEntryButton;
  private Button addExerciseEntryButton;
  public static List exercises=new ArrayList<>();
  private Label[] ingredientLabels;
  private Label[] amountLabels;


  private ComboBox<String> exerciseSelectionComboBox;
  private TextField weightField;
  private TextField durationField;
  private static final String LOG_FILE_PATH = "log.csv";
  private static final String EXERCISE_FILE_PATH = "exercise.csv";
  DietManagerModel model  = new DietManagerModel("foods.csv", "log.csv","exercise.csv");
  @Override
  public void init() {
    controller =
      new DietManagerController(
        this,
              model
      );
  }

  @Override
  public void start(Stage primaryStage) {
    // Create the user interface
    basicFoodInputGrid = createBasicFoodInputGrid();
    recipeInputGrid = createRecipeInputGrid();
    HBox logInputArea = createLogInputArea();

    HBox exerciseInputArea = createExerciseInputArea();

    foodListTextArea = new TextArea();
    foodListTextArea.setEditable(false);
    foodListTextArea.setMinHeight(300);
    dailyLogTextArea = new TextArea();
    dailyLogTextArea.setEditable(false);
    dailyExerciseTextArea = new TextArea();
    dailyExerciseTextArea.setEditable(false);
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
                            new BorderWidths(0.2)
                    )
            )
    );

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
                            new BorderWidths(0.2)
                    )
            )
    );
    logArea.setPadding(new Insets(10));
    logArea.setPrefWidth(700);
    Label logLabel = new Label("Daily Log Addition and Display");
    logLabel.setFont(new Font(new Text().getFont().getName(), 16));

    VBox mainLayout = new VBox(10, foodLabel, foodArea, logLabel, logArea);
    mainLayout.setPadding(new Insets(10));
    mainLayout.setPrefWidth(900);


    VBox exerciseArea = new VBox(10,exerciseInputArea);
    exerciseArea.setBorder(
            new Border(
                    new BorderStroke(
                            Color.BLACK,
                            BorderStrokeStyle.SOLID,
                            new CornerRadii(3),
                            new BorderWidths(0.2)
                    )
            )
    );
    logArea.setPadding(new Insets(10));
    logArea.setPrefWidth(500);
    Label exerciseLabel = new Label("Daily Exercise Addition and Display");
    logLabel.setFont(new Font(new Text().getFont().getName(), 16));

    //
    VBox exercise = new VBox(10, exerciseLabel, exerciseArea,dailyExerciseTextArea);
    exercise.setPadding(new Insets(10));
    exercise.setPrefWidth(800);

    HBox root = new HBox(10, mainLayout, exercise);
    root.setPadding(new Insets(10));
    root.setBackground(
            new Background(
                    new BackgroundFill(
                            Paint.valueOf("#ffffff"),
                            CornerRadii.EMPTY,
                            new Insets(10)
                    )
            )
    );

    // Add event handlers


    addFoodButton.setOnAction(event -> controller.addBasicFood());
    setIngredientsButton.setOnAction(event -> updateRecipeIngredients());
    addRecipeButton.setOnAction(event -> {
      String recipeName = recipeNameField.getText();
      Map<String, Double> ingredients = new HashMap<>();
      for (int i = 0; i < getNumberOfIngredients(); i++) {
        String ingredientName = ingredientComboBoxes[i].getValue();
        Double servingSize = Double.valueOf(ingredientServingFields[i].getText());
        ingredients.put(ingredientName, servingSize);
      }
      controller.createAndAddRecipe(recipeName, ingredients);
    });
    addLogEntryButton.setOnAction(event -> controller.addLogEntry());


    // Inside the addExerciseEntryButton.setOnAction() method
    addExerciseEntryButton.setOnAction(event -> {
      String exerciseName = exerciseSelectionComboBox.getValue();
      String weight = weightField.getText();
      String duration = durationField.getText();
      LocalDate selectedDate = datePicker.getValue();

      if (exerciseName == null || exerciseName.isEmpty() || duration.isEmpty() || weight.isEmpty() || selectedDate == null) {
        showAlert("Missing Information", "Please fill in all fields.", Alert.AlertType.WARNING);
        return;
      }

      double burnCalories = Double.parseDouble(weight) * getCaloriesForExercise(exerciseName) * (Double.parseDouble(duration) / 60);

      // Write exercise data to log.csv
      writeExerciseDataToLog(String.valueOf(selectedDate), exerciseName, Double.parseDouble(duration));

      // Add exercise to CsvHandler.exercises
      CsvHandler.exercises.add(String.join(",", String.valueOf(selectedDate), "e", exerciseName, String.valueOf(burnCalories)));
      // Populate exercise data for the selected date
      populateExerciseDataFromCSV(String.valueOf(selectedDate), weight);
    });


    // Set initial data
    controller.updateFoodList();
    controller.updateDailyLog();

    primaryStage.setWidth(1300);
    primaryStage.setHeight(600);


    //populateExerciseDataFromCSV();

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Diet Manager");
    primaryStage.show();

  }

    public void populateExerciseDataFromCSV(String selectedDate, String w) {
      double weight = Double.parseDouble(w);
        dailyExerciseTextArea.clear();
        dailyExerciseTextArea.appendText("I am daily exercises text area\n");
    double totalburntcal=calculateTotalBurntCaloriesOnDate(selectedDate, weight);
        // Replace "-" with ","
        String modifiedDate = selectedDate.replace("-", ",");

        // Find the index of the second comma after which the day starts
        int secondCommaIndex = modifiedDate.indexOf(",", modifiedDate.indexOf(",") + 1);
        int thirdCommaIndex = modifiedDate.indexOf(",", secondCommaIndex + 1);

        // Extract the complete date from modifiedDate
        String modifiedDateComplete = modifiedDate.substring(0, thirdCommaIndex != -1 ? thirdCommaIndex : modifiedDate.length());

        // Iterate through the exercises list and compare the starting three elements
        for (String exercise : CsvHandler.exercises) {
            // Replace "-" with "," in the exercise string
            exercise = exercise.replace("-", ",");

            // Find the index of the second comma after which the day starts in each exercise
            secondCommaIndex = exercise.indexOf(",", exercise.indexOf(",") + 1);
            thirdCommaIndex = exercise.indexOf(",", secondCommaIndex + 1);

            // Extract the complete date from the current exercise
            String exerciseComplete = exercise.substring(0, thirdCommaIndex != -1 ? thirdCommaIndex : exercise.length());

            if (exerciseComplete.equals(modifiedDateComplete)) {
                dailyExerciseTextArea.appendText(exercise + "\n");

            }
        }
        DailyLog log = model.getDailyLogByDate(LocalDate.parse(selectedDate));
        double consumed = controller.calculateCalories(log);
        dailyExerciseTextArea.appendText("Calories Consumed: : "+consumed+"\n");
        dailyExerciseTextArea.appendText("Calories burnt: "+totalburntcal+"\n");
        dailyExerciseTextArea.appendText("Net Calories: : "+(consumed-totalburntcal)+"\n");
    }

    public double calculateTotalBurntCaloriesOnDate(String selectedDate, double w) {
        double totalBurntCalories = 0.0;
        // Read exercise calorie data from exercise.csv and store in a map
        Map<String, Double> exerciseCaloriesMap = readExerciseCaloriesFromCSV();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    LocalDate date = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    if (date.toString().equals(selectedDate)) {
                        if (parts[3].equals("e") && exerciseCaloriesMap.containsKey(parts[4])) {
                            double caloriesPerHour = exerciseCaloriesMap.get(parts[4]);
                            double fractionalHours = Double.parseDouble(parts[5]) / 60.0;
                            double burntCalories = caloriesPerHour * w * fractionalHours;
                            totalBurntCalories += burntCalories;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalBurntCalories;
    }

    private Map<String, Double> readExerciseCaloriesFromCSV() {
        Map<String, Double> exerciseCaloriesMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(EXERCISE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals("e")) {
                    String exerciseName = parts[1];
                    double calories = Double.parseDouble(parts[2]);

                    exerciseCaloriesMap.put(exerciseName, calories);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return exerciseCaloriesMap;
    }












    private void writeExerciseDataToLog(String selectedDate, String exerciseName, double burnCalories) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("log.csv", true))) {

        LocalDate date = LocalDate.parse(selectedDate);
        String formattedDate = String.format("%d,%02d,%02d", date.getYear(), date.getMonthValue(), date.getDayOfMonth());

// Write the formatted data to the file
        writer.write(formattedDate + ",e," + exerciseName + "," + burnCalories + "\n");

// Add the formatted data to the exercises list
        String exerciseEntry = formattedDate + ",e," + exerciseName + "," + burnCalories;
        exercises.add(exerciseEntry);


    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private static final Map<String, Double> exerciseCaloriesMap = new HashMap<>();

  // Static block to initialize the exercise calories map
  static {
    try (BufferedReader br = new BufferedReader(new FileReader("exercise.csv"))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 3) {
          String exerciseName = parts[1].trim();
          double calories = Double.parseDouble(parts[2].trim());
          exerciseCaloriesMap.put(exerciseName, calories);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Function to get calories for a given exercise name
  private double getCaloriesForExercise(String exerciseName) {
    return exerciseCaloriesMap.getOrDefault(exerciseName, 0.0);
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
    ingredientLabels = new Label[5];
    amountLabels = new Label[5];
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
      setIngredientsButton
    );

    return grid;
  }


  private HBox createExerciseInputArea() {
    HBox hbox = new HBox(30);
    hbox.setAlignment(Pos.CENTER);

    VBox firstLine = new VBox(10);
    firstLine.setAlignment(Pos.CENTER);

    exerciseSelectionComboBox = new ComboBox<>();
    setExerciseSelectionOptions();

    weightField = new TextField();
    durationField = new TextField();

    firstLine.getChildren().addAll(
            new Label("Select Exercise:"),
            exerciseSelectionComboBox,
            new Label("Weight:"),
            weightField    );

    VBox secondLine = new VBox(10);
    secondLine.setAlignment(Pos.CENTER);

    datePicker = new DatePicker();
    datePicker.setValue(LocalDate.now());

    addExerciseEntryButton = new Button("Add Exercise");

    secondLine.getChildren().addAll(
            new Label("Select Date:"),
            datePicker,
            addExerciseEntryButton,new Label("Duration:"),
            durationField

    );

    hbox.getChildren().addAll(firstLine, secondLine);



    return hbox;
  }



  private void setExerciseSelectionOptions() {
    // Assuming exercise data is stored in a CSV file named "exercises.csv"
    String exerciseFilePath = "exercise.csv"; // Path to your exercise CSV file

    try (BufferedReader br = new BufferedReader(new FileReader(exerciseFilePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        // Split the line by comma
        String[] parts = line.split(",");

        // Assuming the exercise name is the second part (index 1)
        String exerciseName = parts[1].trim();

        // Add the exercise name to the ComboBox
        exerciseSelectionComboBox.getItems().add(exerciseName);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
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
        addLogEntryButton
      );

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
      showAlert(
        "You have entered an invalid number of ingredients",
        "Please enter a number between 1 and 5",
        Alert.AlertType.WARNING
      );
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
      ingredientLabels[i] = new Label("Ingredient " + (i + 1) + ":");
      amountLabels[i] = new Label("Amount:");

      recipeInputGrid.addRow(
        2 + i,
        ingredientLabels[i],
        ingredientComboBoxes[i],
        amountLabels[i],
        ingredientServingFields[i]
      );
    }
    recipeInputGrid.add(addRecipeButton, 1, 2 + numberOfIngredients);
    addRecipeButton.setOnAction(event -> {
    String recipeName = recipeNameField.getText();
    Map<String, Double> ingredients = new HashMap<>();
    for (int i = 0; i < getNumberOfIngredients(); i++) {
        String ingredientName = ingredientComboBoxes[i].getValue();
        Double servingSize = Double.parseDouble(ingredientServingFields[i].getText());
        ingredients.put(ingredientName, servingSize);
    }
    controller.createAndAddRecipe(recipeName, ingredients);
});
  }

  /**
   * Gets input recipe.
   *
   * @return the input recipe
   */


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
    String modifiedText = "Today's Log:\n----------------\n" + "Calories burned  TOday"+"\n"+ text;

    dailyLogTextArea.setText(modifiedText);
//    dailyLogTextArea.setText("Total Calories consumed TOday");
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

  public void clearFoodInputs() {
    foodNameField.clear();
    caloriesField.clear();
    fatField.clear();
    carbohydratesField.clear();
    proteinField.clear();
  }
  /**
   * Show alert.
   *
   * @param title   the title
   * @param content the content
   */
  public void showAlert(
    String title,
    String content,
    Alert.AlertType alertType
  ) {
    Alert alert = new Alert(alertType);
    alert.setHeaderText(title);
    alert.setContentText(content);
    alert.showAndWait();
  }
}
