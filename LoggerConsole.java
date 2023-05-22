package application;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerConsole implements Initializable {

	private String ProjectType;
	private String LogName;
	private String LifeCycle;
	private String effortCategory;
	private String Effort;
	private String Task;

	@FXML
	private ComboBox<String> EffortCategory;

	@FXML
	private ComboBox<String> EffortCategory2;

	@FXML
	private ComboBox<String> LifeCycleStep;

	@FXML
	private ComboBox<String> ProjectSelector;

	@FXML
	private Button StopActivity;

	@FXML
	private Button StartActivity;

	
	private AnimationTimer timer; 

	// private long stopTime;
	private LocalDateTime startTime;
	private LocalDateTime stopTime;

	@FXML
	
	private void onStartActivity(ActionEvent event) {
	    StopActivity.setDisable(false);
	
		startTime = LocalDateTime.now(); 
		LogName = (startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

		System.out.println(
				"Activity started at: " + startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		StartActivity.setDisable(true);
	}

	@FXML
	private ComboBox<String> TaskSelector;

	@FXML
	private TextField textField = new TextField();

	
	private String otherText;

	@FXML
	private void onStopActivity(ActionEvent event) {
		
		boolean check = true;

		

		if (TaskSelector.getValue() == null) {
			System.out.println("You must select a Task");
			check = false;
		} else {
			Task = TaskSelector.getValue();
		}

		
		if (ProjectSelector.getValue() == null) {
			System.out.println("You must select a Project");

			check = false;
		} else {
	
			ProjectType = ProjectSelector.getValue();
		}

		if (LifeCycleStep.getValue() == null) {
			System.out.println("You must select a Life Cycle");
			check = false;
		} else {

			LifeCycle = LifeCycleStep.getValue();
		}

		if (EffortCategory.getValue() == null) {
			System.out.println("You must select an Effort Category");
			check = false;
		} else {
		
			effortCategory = EffortCategory.getValue();
		}

		if (EffortCategory2.getValue() == null && textField.getText().isEmpty()) {
			System.out.println("You must select or enter an Effort Category Subclass");
			check = false;
		} else {
	
			Effort = EffortCategory2.getValue();
		}

		if (LogName != null && check == true) {
			stopTime = LocalDateTime.now(); 
			System.out.println(
					"Activity stopped at: " + stopTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			Duration duration = Duration.between(startTime, stopTime);
			long minutes = duration.toMinutes();
			long seconds = duration.getSeconds() % 60;
			long hours = minutes / 60;
			minutes = minutes % 60;
			System.out.println(String.format("Elapsed time: %02d:%02d:%02d", hours, minutes, seconds));
			LogName += " - " + (stopTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			System.out.println("Task: " + Task);
			System.out.println("Project Type: " + ProjectType);
			System.out.println("Log Name: " + LogName);
			System.out.println("Life Cycle: " + LifeCycle);
			System.out.println("Effort Category: " + effortCategory);

			
			if (!(EffortCategory2.getValue() == null)) {
				System.out.println("Sub Effort: " + Effort);
			}

			otherText = textField.getText();
			if (!textField.getText().isEmpty()) {
				System.out.println("Other: " + otherText);
			}
			
			textField.setText("");
		    StartActivity.setDisable(false);
		    StopActivity.setDisable(true);

			
		}

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		textField.setVisible(false);
		

		ObservableList<String> list1 = FXCollections.observableArrayList("Plans", "Deliverables", "Interruptions",
				"Defects", "Others");
		EffortCategory.setItems(list1);

	
		EffortCategory.setOnAction((event) -> {
			String selectedValue = EffortCategory.getValue();
			if (selectedValue.equals("Plans")) {
				ObservableList<String> list2 = FXCollections.observableArrayList("Project Plan", "Risk Management Plan",
						"Conceptual Design Plan", "Detailed Design Plan", "Implementation Plan");
				EffortCategory2.setItems(list2);
				EffortCategory2.setVisible(true);
				textField.setVisible(false);
			} else if (selectedValue.equals("Deliverables")) {
				ObservableList<String> list2 = FXCollections.observableArrayList("Conceptual Design", "Detailed Design",
						"Test Cases", "Solution", "Reflection", "Outline", "Reflection", "Draft", "Report",
						"User Defined", "Other");
				EffortCategory2.setItems(list2);
				EffortCategory2.setVisible(true);
				textField.setVisible(false);
			} else if (selectedValue.equals("Interruptions")) {
				ObservableList<String> list2 = FXCollections.observableArrayList("Break", "Phone", "Teammate",
						"Visitor", "Other");
				EffortCategory2.setItems(list2);
				EffortCategory2.setVisible(true);
				textField.setVisible(false);
			} else if (selectedValue.equals("Defects")) {
				ObservableList<String> list2 = FXCollections.observableArrayList("Defect");
				EffortCategory2.setItems(list2);
				EffortCategory2.setVisible(true);
				textField.setVisible(false);
			} else if (selectedValue.equals("Others")) {
			
				textField.setVisible(true);
				EffortCategory2.setVisible(false);
				EffortCategory2.setValue(null);

			}

		});

		ObservableList<String> list6 = FXCollections.observableArrayList("A", "B", "C");
		TaskSelector.setItems(list6);

		ObservableList<String> list4 = FXCollections.observableArrayList("Business Project", "Development Project");
		ProjectSelector.setItems(list4);

		// Add change listener for EffortCategory2
		EffortCategory2.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> onEffortCategory2(newValue));

		
		LifeCycleStep.setOnAction(this::onLifeCycleStep);

		// Add event handler for ProjectSelector
		ProjectSelector.setOnAction(this::onProjectSelector);

		ProjectSelector.setOnAction((event) -> {
			String selectedValue = ProjectSelector.getValue();
			if (selectedValue.equals("Business Project")) {
				ObservableList<String> list5 = FXCollections.observableArrayList("Planning", "Information Gathering",
						"Information Understanding", "Verifying", "Outlining", "Drafting", "Finalizing", "Team Meeting",
						"Coach Meeting", "Stakeholder Meeting");
				LifeCycleStep.setItems(list5);
			} else if (selectedValue.equals("Development Project")) {
				ObservableList<String> list2 = FXCollections.observableArrayList("Problem Understanding",
						"Conceptual Design Plan", "Requirements", "Conceptual Design", "Conceptual Design Review",
						"Detailed Design Plan", "Detailed Design Prototype", "Detailed Design Review",
						"Implementation Plan", "Test Case Generation", "Solution Specification", "Solution Review",
						"Solution Implementation", "Unit/System Test", "Reflection", "Repository Update");
				LifeCycleStep.setItems(list2);
			}
		});

		StartActivity.setOnAction(this::onStartActivity);
		StopActivity.setOnAction(this::onStopActivity);
	}

	// Event handler for EffortCategory
	private void onEffortCategorySelected(ActionEvent event) {
		String selectedValue = EffortCategory.getValue();
		// Do something with the selected value
		// System.out.println("Selected value from EffortCategory: " + selectedValue);
	}

	// Change listener for EffortCategory2
	private void onEffortCategory2(String selectedValue) {
		// Do something with the selected value
		// System.out.println("Selected value from EffortCategory2: " + selectedValue);
	}

	// Event handler for LifeCycleStep
	private void onLifeCycleStep(ActionEvent event) {
		String selectedValue = LifeCycleStep.getValue();
		// Do something with the selected value
		// System.out.println("Selected value from LifeCycleStep: " + selectedValue);
	}

	// Event handler for ProjectSelector
	private void onProjectSelector(ActionEvent event) {
		String selectedValue = ProjectSelector.getValue();
		// Do something with the selected value
		// System.out.println("Selected value from ProjectSelector: " + selectedValue);
	}
}