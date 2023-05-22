package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Created by Max LaBelle
//Future plans of Implementation: DeleteButtonHandler, Handling for the other field, storing and loading from files
//Implementation Goals: Prevent modifying of logs without proof of user and what was changed, this can provide a change log so managers can spot any suspicious activity and can also be used to track work done.
//Result Satisfaction: Works as planned, makes sure logs have proof of what was changed and logs user credentials and changes made.

public class ChangeLogController implements Initializable {
	private String ChangeLog;
	private String ProjectType;
	private String LogName;
	private String LifeCycle;
	private String effortCategory;
	private String Effort;
	private LocalDateTime startTime;
	private LocalDateTime stopTime;
	@FXML
	private Label CategoryLabel;
	@FXML
    private TextField Other;
    @FXML
    private TextField ChangeField;

    @FXML
    private Button ClearBtn;

    @FXML
    private ComboBox<String> ComboEffort;

    @FXML
    private ComboBox<String> ComboLog;

    @FXML
    private ComboBox<String> ComboPlan;

    @FXML
    private ComboBox<String> ComboProject;
    
    @FXML
    private ComboBox<String> ComboStep;

    //@FXML
    //private TextField DateField;

    @FXML
    private Button DeleteBtn;

    @FXML
    private TextField InitialField;

    @FXML
    private TextField StartField;

    @FXML
    private TextField StopField;

    @FXML
    private Button UpdateBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    Other.setVisible(false);
    ObservableList<String> list1=FXCollections.observableArrayList("Business Project", "Development Project");
    ComboProject.setItems(list1);
    ObservableList<String> list2=FXCollections.observableArrayList("Planning Plans: Project Plan","Example Log");
    ComboLog.setItems(list2);
    //Moved to be specific for business or development project
    ObservableList<String> list3=FXCollections.observableArrayList("Plans", "Deliverables","Interruptions","Defects","Others");
    ComboEffort.setItems(list3);
    ObservableList<String> list4=FXCollections.observableArrayList("Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting", "Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting");
    ComboStep.setItems(list4);
    
    // Event Handlers for the Prototype
    ComboProject.setOnAction(this::ComboProjectHandler);
  
    ComboLog.setOnAction(this::ComboLogHandler);

    ComboEffort.setOnAction(this::ComboEffortHandler);

    ComboStep.setOnAction(this::ComboStepHandler);
    
    UpdateBtn.setOnAction(this::UpdateHandler);
    
    ClearBtn.setOnAction(this::ClearHandler);
    }
    private void loadLog() {
    	ComboLog.setValue(LogName);
    	ComboProject.setValue(ProjectType);
    	ComboEffort.setValue(effortCategory);
        ComboStep.setValue(LifeCycle);
        StartField.setText((startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        StopField.setText((stopTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        if(ComboEffort.getValue().equals("Others")) {
        Other.setVisible(true);
        Other.setText(Effort);
        }else {
        ComboPlan.setValue(Effort);
        }
    }
    
    
    
    // Event handler for ComboProject
    private void ComboProjectHandler(ActionEvent event) {
        String selectedValue = ComboProject.getValue();
        if(selectedValue.equals("Business Project")) {
        	ObservableList<String> list4=FXCollections.observableArrayList("Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting", "Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting");
            ComboStep.setItems(list4);
        }else if(selectedValue.equals("Development Project")) {
        	ObservableList<String> list5=FXCollections.observableArrayList("Problem Understanding", "Conceptual Design Plan", "Requirements", "Conceptual Design", "Conceptual Design Review", "Detailed Design Plan", "Detailed Design/Prototype", "Detailed Design Review", "Implementation Plan", "Test Case Generation", "Solution Specification", "Solution Specification", "Solution Review", "Solution Implementation", "Unit/System Test", "Reflection", "Repository Update");
            ComboStep.setItems(list5);
        }
        //System.out.println("Selected value from ComboProject: " + selectedValue);
    }

    // Event listener for ComboLog
    private void ComboLogHandler(ActionEvent event) {
    	String selectedValue = ComboLog.getValue();
        //System.out.println("Selected value from ComboLog: " + selectedValue);
        if(selectedValue.equals("Example Log") && ComboProject.getValue().equals("Business Project")) {
        System.out.println("WARNING NOT FULLY IMPLEMENTED: EXAMPLE LOG LOADING");
        ComboEffort.setValue("Plans");
        ComboPlan.setValue("Project Plan");
        ComboStep.setValue("Planning");
        StartField.setText("2023-07-21 13:22:03");
        StopField.setText("2023-07-21 14:37:12");
        System.out.println("===LOG LOADED===");
    	System.out.println(ComboLog.getValue()+" within the "+ComboProject.getValue() +" log details:");
    	System.out.println("Start Time:"+StartField.getText()+ "  Stop Time:"+StopField.getText());
    	System.out.println("On the "+ComboStep.getValue()+ " step, work completed on recording of this log is "+ComboEffort.getValue()+ " ,and more specifically "+ComboPlan.getValue());
        
        }
    }
    // Event handler for ComboStep
    private void ComboStepHandler(ActionEvent event) {
        String selectedValue = ComboStep.getValue();
        //System.out.println("Selected value from ComboStep: " + selectedValue);
    }
    private void ComboEffortHandler(ActionEvent event) {
    	//Will need to implement something like this for whats on the ComboLog in the future but no recorded logs yet
    	ObservableList<String> plans=FXCollections.observableArrayList("Project Plan","Risk Management Plan", "Conceptual Design Plan","Detailed Design Plan","Implementation Plan");
        ObservableList<String> deliverables=FXCollections.observableArrayList("Conceptual Design","Detailed Design","Test Cases","Solution","Reflection","Outline","Reflection","Draft","Report","User Defined","Other");
        ObservableList<String> interruptions=FXCollections.observableArrayList("Break","Phone","Teammate","Visitor","Other");
        ObservableList<String> defects=FXCollections.observableArrayList("- no defect selected -");
    	String selected = ComboEffort.getValue();
    	//System.out.println("Selected value from ComboPlan: " + selected);
    	if(selected.equals("Plans")) {
    		ComboPlan.setItems(plans);
    		Other.setVisible(false);
    		Other.setText("");
    	}else if(selected.equals("Deliverables")) {
    		ComboPlan.setItems(deliverables);
    		Other.setVisible(false);
    		Other.setText("");
    	}else if(selected.equals("Interruptions")) {
    		ComboPlan.setItems(interruptions);
    		Other.setVisible(false);
    		Other.setText("");
    	}else if(selected.equals("Defects")) {
    		ComboPlan.setItems(defects);
    		Other.setVisible(false);
    		Other.setText("");
    	}else if(selected.equals("Others")) {
    		Other.setVisible(true);
    	}
    }
    
    private void UpdateHandler(ActionEvent event) {
    	if(StartField.getText().equals("") || StopField.getText().equals("")) {
    	System.out.println("Update the TimeStamp!");
    	}else if(ChangeField.getText().equals("")) {
    	System.out.println("You must include a changelog!");
    	}else if(InitialField.getText().equals("")) {
    	System.out.println("You must include intials for any changes");
    	}else if(ComboProject.getValue()==null){
    	System.out.println("You must select the project");
    	}else if(ComboLog.getValue()==null) {
    	System.out.println("You must select a log");
    	}else if(ComboStep.getValue()==null) {
    	System.out.println("You must select a cycle step");
    	}else if(ComboEffort.getValue()==null) {
    	System.out.println("You must select an Effort category");
    	}else if(ComboPlan.getValue()==null && Other.getText().equals("")) {
    	System.out.println("You must select an effort subcategory");
    	}else {
    	System.out.println("===LOG UPDATED===");
    	System.out.println(ComboLog.getValue()+" within the "+ComboProject.getValue() +" log details:");
    	ProjectType=ComboProject.getValue();
    	System.out.println("Start Time:"+StartField.getText()+ "  Stop Time:"+StopField.getText());
    	if(!Other.getText().equals("")) {
    	System.out.println("On the "+ComboStep.getValue()+ " step, work completed on recording of this log is "+ComboEffort.getValue()+ ", and more specifically "+Other.getText());
    	LifeCycle=ComboStep.getValue();
    	effortCategory=ComboEffort.getValue();
    	Effort=Other.getText();
    	}else {
    	System.out.println("On the "+ComboStep.getValue()+ " step, work completed on recording of this log is "+ComboEffort.getValue()+ ", and more specifically "+ComboPlan.getValue());
    	LifeCycle=ComboStep.getValue();
    	effortCategory=ComboEffort.getValue();
    	Effort=ComboPlan.getValue();
    	}
    	System.out.println("Change Log: "+ChangeField.getText());
    	System.out.println("Updated by: "+InitialField.getText());
    	ChangeLog="Edited by: "+InitialField.getText()+", Changelog: "+ChangeField.getText();
    	}
    }
    private void ClearHandler(ActionEvent event) {
    	ComboLog.setValue("");
    	ComboProject.setValue("");
    	ComboEffort.setValue("");
        ComboPlan.setValue("");
        ComboStep.setValue("");
        StartField.setText("");
        StopField.setText("");
        ChangeField.setText("");
        InitialField.setText("");
        Other.setText("");
    }
}


